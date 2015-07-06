package io.photoop.optobot.bot

import akka.actor.{ActorLogging, Actor, Props}
import io.photoop.optobot.bot.SystemReporter.{Initialize, Report}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class SystemReporter extends Actor with ActorLogging {
  def receive = {
    case Initialize =>
      context.system.scheduler.schedule(5.seconds, 5.seconds) {
        InMemoryImageLikeStore.calculateLikesForLast5Min
      }

    case Report => {
      val randomUserDotMeUsers = RandomUserGenerator.users.size
      val junkUsers = RandomUserGenerator.junkUsers.size
      val totalImages = InMemoryImageStore.store.size
      val totalLikes = InMemoryImageLikeStore.totalLikes
      val totalLikes5Min = InMemoryImageLikeStore.likesForTimePeriod(5).size

      log.info(
        s"""
           |System Summary
           |-------------------------------------------
           |Total Users           : ${randomUserDotMeUsers + junkUsers}
           |Total randomuser.me   : $randomUserDotMeUsers
           |Total junk users      : $junkUsers
           |
           |Total Images          : $totalImages
           |Total Likes           : $totalLikes
           |Total Likes (5 min)   : $totalLikes5Min
           |
         """.stripMargin)
    }

  }
}

object SystemReporter {
  val props = Props[SystemReporter]

  case object Report

  case object Initialize
}
