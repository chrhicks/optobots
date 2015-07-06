package io.photoop.optobot.bot

import java.util.UUID

import io.photoop.images.v0.models.Image
import io.photoop.optobot.bot.ImageUploader.ImageUploaded

import scala.util.Random

object InMemoryImageStore {
  val store: scala.collection.mutable.HashMap[UUID, ImageUploaded] = scala.collection.mutable.HashMap.empty[UUID, ImageUploaded]

  def recordUpload(iu: ImageUploaded) = store.put(iu.image.guid, iu)

  def getAllImages = store.values.map(_.image)

  def getRandomImage: Option[Image] =
    if (store.nonEmpty)
      Some(getAllImages.toSeq(Random.nextInt(store.size)))
    else
      None
}
