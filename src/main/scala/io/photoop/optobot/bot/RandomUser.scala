package io.photoop.optobot.bot

import akka.actor.{Actor, ActorLogging, Props}
import io.photoop.key.users.v0.models.User
import io.photoop.optobot.bot.ImageUploader.{ImageUploaded, ImageToUpload}
import io.photoop.optobot.bot.ProfileManager.Tick

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.{FiniteDuration, _}
import scala.util.Random

class RandomUser extends Actor with ActorLogging {
  import OptobotMessage._

  private[RandomUser] var user: Option[User] = None

  private val timeHash = self.path.name.split('+').last

  private val imageUploader = context.system.actorOf(ImageUploader.props, s"imageUploader+$timeHash")

  private val profileManager = context.system.actorOf(ProfileManager.props, s"profileManager+$timeHash")

  private var imageUploadIntervalMax = 1200


  def receive = {
    case c:InitializeWithConfig => {
      imageUploadIntervalMax = c.imageUploadIntervalMax
      val user = c.user
      randomImageUploader(user)
      profileManager ! InitializeWithUser(user)
      context.system.scheduler.schedule(FiniteDuration(30, SECONDS), FiniteDuration(30, SECONDS), profileManager, Tick)
    }

    case uploaded:ImageUploaded =>
      context.parent ! uploaded
  }

  def randomImageUploader(user: User): Unit = {
    val delay = Math.max(Random.nextInt(imageUploadIntervalMax), 60)
    context.system.scheduler.scheduleOnce(FiniteDuration(delay, SECONDS)) {
      imageUploader ! ImageToUpload(user)
      randomImageUploader(user)
    }
  }
}

object RandomUser {
  val props = Props[RandomUser]
}

object OptobotMessage {
  case object Initialize

  case class InitializeWithUser(user: User)

  case class InitializeWithConfig(imageUploadIntervalMax: Int, user: User)
}

