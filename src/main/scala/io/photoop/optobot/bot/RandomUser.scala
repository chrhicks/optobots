package io.photoop.optobot.bot

import akka.actor.{Actor, ActorLogging, Props}
import io.photoop.key.users.v0.Client
import io.photoop.key.users.v0.models.{User, UserForm}
import io.photoop.optobot.bot.ImageUploader.ImageToUpload
import io.photoop.optobot.bot.ProfileManager.Tick
import io.photoop.optobot.model.{Persona, PersonaJson}
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess}

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{FiniteDuration, Duration}
import scala.util.Random

import scala.concurrent.duration._

class RandomUser extends Actor with ActorLogging {
  import OptobotMessage._

  private[RandomUser] var user: Option[User] = None

  private val timeHash = self.path.name.split('+').last

  private val imageUploader = context.system.actorOf(ImageUploader.props, s"imageUploader+$timeHash")

  private val profileManager = context.system.actorOf(ProfileManager.props, s"profileManager+$timeHash")

  private var imageUploadIntervalMax = 1200


  def receive = {
    case c:InitializeWithConfig => {
      log.info("Initializing Bot!")

      imageUploadIntervalMax = c.imageUploadIntervalMax

      user = RandomUser.get

      user.map { u =>
        log.info(s"Hello! My name is ${u.firstName} ${u.lastName}")

        randomImageUploader(u)

        profileManager ! InitializeWithUser(u)

        context.system.scheduler.schedule(FiniteDuration(30, SECONDS), FiniteDuration(30, SECONDS), profileManager, Tick)
      }.getOrElse {
        log.error("Could not get user information for myself!")
        context.stop(self)
      }
    }
  }

  def randomImageUploader(user: User): Unit = {

    val delay = Math.max(Random.nextInt(imageUploadIntervalMax), 60)
//    log.info(s"Uploading an image in $delay s")
    context.system.scheduler.scheduleOnce(FiniteDuration(delay, SECONDS)) {
      imageUploader ! ImageToUpload(user)
      randomImageUploader(user)
    }
  }
}

object RandomUser {
  val props = Props[RandomUser]

  val builder = new com.ning.http.client.AsyncHttpClientConfig.Builder()
  val client = new play.api.libs.ws.ning.NingWSClient(builder.build())

  val users = new Client("http://localhost:7000")

  def get: Option[User] = {
    import PersonaJson._

    val personaFuture = client.url("http://api.randomuser.me/").get().map { response =>
      val userJson = response.json.\("results").\\("user").head

//      Logger.info(s"userJson: $userJson")

      val persona: Option[Persona] = userJson.validate[Persona] match {
        case p:JsSuccess[Persona] =>
          Logger.info("got persona")
          p.asOpt
        case e:JsError =>
          Logger.error("Errors: " + JsError.toFlatJson(e).toString())
          None
      }

      persona
    }

    Await.result(personaFuture.map(_.map(register)), Duration("5000 ms"))
  }

  def register(persona: Persona): User = {
    val userForm = UserForm(
      email = persona.email,
      password = persona.password,
      firstName = Some(persona.name.first),
      lastName = Some(persona.name.last),
      vanityKey = Some(persona.username))

    Await.result(users.Users.post(userForm), Duration("5000 ms"))
  }
}

object OptobotMessage {
  case object Initialize

  case class InitializeWithUser(user: User)

  case class InitializeWithConfig(imageUploadIntervalMax: Int)
}

