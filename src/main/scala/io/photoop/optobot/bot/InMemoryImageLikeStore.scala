package io.photoop.optobot.bot

import java.util.UUID

import org.joda.time.DateTime

object InMemoryImageLikeStore {
  case class MemoryLike(imageGuid: UUID, userGuid: UUID, date: DateTime)

  private val commitLog = new scala.collection.mutable.ArrayBuffer[MemoryLike](100)

  private var likes5Min: Seq[MemoryLike] = Seq.empty

  def totalLikes = commitLog.size

  def like(imageGuid: UUID, userGuid: UUID) = {
    commitLog += MemoryLike(imageGuid, userGuid, DateTime.now())
  }

  def likesForImage(imageGuid: UUID): Seq[MemoryLike] = commitLog.filter(_.imageGuid == imageGuid)

  def likesForTimePeriod(lastMinutes: Int): Seq[MemoryLike] = likes5Min

  def calculateLikesForLast5Min = {
    val now = DateTime.now()
    likes5Min = commitLog.filter(_.date.isAfter(now.minusMinutes(5)))
  }
}
