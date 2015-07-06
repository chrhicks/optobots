package io.photoop.optobot.bot

import akka.actor.{Actor, ActorLogging, Props}
import io.photoop.images.v0.models.{Image, ImageLikeForm}
import io.photoop.key.users.v0.models.User

import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

class LikeRandom extends Actor with ActorLogging {
  import LikeRandom._

  import scala.concurrent.duration._

  def receive = {
    case init:Initialize =>
      context.system.scheduler.schedule(FiniteDuration(Random.nextInt(300), SECONDS), init.frequency) {
        likeImage(init.user)
      }
  }

  private def likeImage(user: User): Unit = {
    val randomImageOpt = InMemoryImageStore.getRandomImage

    randomImageOpt.foreach { randomImage =>
      likeImage(randomImage, user)
      InMemoryImageLikeStore.like(randomImage.guid, user.guid)
    }
  }

  private def likeImage(image: Image, user: User): Boolean = {
    Await.result(images.Images.postLike(ImageLikeForm(image.guid, user.guid)), Duration("5000 ms"))
  }

}

object LikeRandom {
  val props = Props[LikeRandom]

  private val images = new io.photoop.images.v0.Client("http://localhost:7000")

  case class Initialize(user: User, frequency: FiniteDuration)
}
