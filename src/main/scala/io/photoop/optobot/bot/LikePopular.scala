package io.photoop.optobot.bot

import java.util.UUID

import akka.actor.{Actor, ActorLogging, Props}
import io.photoop.images.v0.models.{Image, ImageLikeForm}
import io.photoop.key.users.v0.models.User

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Random


class LikePopular extends Actor with ActorLogging {
  import LikePopular._

  def receive = {
    case init:Initialize =>
      context.system.scheduler.schedule(FiniteDuration(Random.nextInt(300), SECONDS), init.frequency) {
        likeImage(init.user)
      }
  }

  private def likeImage(user: User): Unit = {
    val topImagesByLikes =
      InMemoryImageLikeStore
        .likesForTimePeriod(5)
        .map(like => like.imageGuid -> like)
        .groupBy(_._1)
        .mapValues(_.size)
        .toSeq
        .sortBy(-_._2)
        .take(10)

    if(topImagesByLikes.size > 10) {
      val imgGuidToLike = topImagesByLikes(Random.nextInt(10))._1
      likeImage(imgGuidToLike, user.guid)
      InMemoryImageLikeStore.like(imgGuidToLike, user.guid)
    }
  }

  private def likeImage(imageGuid: UUID, userGuid: UUID): Boolean = {
    Await.result(images.Images.postLike(ImageLikeForm(imageGuid, userGuid)), Duration("5000 ms"))
  }

}

object LikePopular {
  val props = Props[LikePopular]

  private val images = new io.photoop.images.v0.Client("http://localhost:7000")

  case class Initialize(user: User, frequency: FiniteDuration)
}
