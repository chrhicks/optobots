package io.photoop.optobot.bot

import akka.actor._
import io.photoop.OptoBotMaster.Initialize
import io.photoop.key.users.v0.Client
import io.photoop.key.users.v0.errors.ErrorsResponse
import io.photoop.key.users.v0.models.{User, UserForm}
import io.photoop.optobot.model.{Persona, PersonaJson}
import play.api.Logger
import play.api.libs.json.{JsError, JsSuccess, JsValue}

import scala.collection.mutable.ArrayBuffer
import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{Duration, _}
import scala.util.Random

object RandomUserGenerator {

  var users: ArrayBuffer[User] = ArrayBuffer.empty[User]
  var junkUsers: ArrayBuffer[User] = new ArrayBuffer[User](5000)

  val builder = new com.ning.http.client.AsyncHttpClientConfig.Builder()
  val client = new play.api.libs.ws.ning.NingWSClient(builder.build())

  val usersClient = new Client("http://localhost:7000")

  def init(system: ActorSystem, master: ActorRef, desiredCount: Int) =  system.scheduler.scheduleOnce(1.seconds) {
    getUsers(system, master, desiredCount)
  }

  def getUsers(system: ActorSystem, master: ActorRef, desiredCount: Int): Unit = {
    get.flatten.map(u => users += u)
    if (users.size < desiredCount) {
      system.scheduler.scheduleOnce(15.seconds) {
        getUsers(system, master, desiredCount)
      }
    } else {
      println("Initializing master...")
      master ! Initialize(desiredCount)
    }
  }

  def getUser(index: Int): User = users(index)

  def getJunkUser = {
    val user = generateInternalUser
    junkUsers += user
    user
  }

  private def generateInternalUser: User = {
    val firstName = Random.nextString(8)
    val lastName = Random.nextString(15)
    val email = s"$firstName.$lastName@photooptest.io"
    val password = Random.nextString(25)
    val vanityKey = s"$firstName$lastName"

    val userForm = UserForm(
      email = email,
      password = password,
      firstName = Some(firstName),
      lastName = Some(lastName),
      vanityKey = Some(vanityKey)
    )

    register(userForm)
  }

  private def get: Seq[Option[User]] = {
    println("getting 100 users...")
    val personasFuture = client.url("http://api.randomuser.me/?results=100").get().map { response =>
      response.json.\("results").\\("user").map(parsePersona)
    }

    val usersFuture = personasFuture.map(_.map(_.map(register)))

    Await.result(usersFuture, Duration("30000 ms"))
  }

  private def parsePersona(value: JsValue): Option[Persona] = {
    import PersonaJson._

    value.validate[Persona] match {
      case p:JsSuccess[Persona] =>
        p.asOpt
      case e:JsError =>
        Logger.error("Errors: " + JsError.toFlatJson(e).toString())
        None
    }
  }

  private def register(persona: Persona): User = {
    val userForm = UserForm(
      email = persona.email,
      password = persona.password,
      firstName = Some(persona.name.first),
      lastName = Some(persona.name.last),
      vanityKey = Some(persona.username + s"${System.currentTimeMillis()}}"))

    register(userForm)
  }

  private def register(userForm: UserForm): User = {
    val userFuture = usersClient.Users.post(userForm).recover {
      case e:ErrorsResponse => Await.result(usersClient.Users.get(email = Some(userForm.email)), Duration("5000 ms"))
    }

    Await.result(userFuture, Duration("5000 ms"))
  }
}
