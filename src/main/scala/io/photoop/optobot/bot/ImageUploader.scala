package io.photoop.optobot.bot

import java.io.File

import akka.actor.{Actor, ActorLogging, Props}
import io.photoop.images.v0.models.{Image, ImageForm}
import io.photoop.key.users.v0.models.User
import io.photoop.optobot.util.ImageUtil

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import scala.util.Random

class ImageUploader extends Actor with ActorLogging {
  import ImageUploader._

  private val imageFiles = new File("src/main/resources/images").listFiles()

  def receive = {
    case img:ImageToUpload =>
      val imageIndex = Random.nextInt(imageFiles.length)
      val filePath = imageFiles(imageIndex)
      val uploadedImage = uploadImage(filePath.getAbsolutePath, img.user)
      sender() ! ImageUploaded(img.user, uploadedImage)
  }

  def uploadImage(imagePath: String, user: User): Image = {
    val base64EncodedString = ImageUtil.getBase64EncodedImageDataString(imagePath)

    val imageForm = ImageForm(user.guid, base64EncodedString)

    Await.result(images.Images.post(imageForm), Duration("10000 ms"))
  }
}

object ImageUploader {
  val props = Props[ImageUploader]
  private val images = new io.photoop.images.v0.Client("http://localhost:7000")

  case class ImageToUpload(user: User)

  case class ImageUploaded(user: User, image: Image)
}
