package io.photoop

import java.util.UUID

import akka.actor.{ActorRef, ActorSystem}
import io.photoop.optobot.bot.{OptobotMessage, RandomUser}

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

object OptobotMain extends App {
  val system = ActorSystem("OptobotSystem")

  val numRandomUsers = 50

  val users:List[ActorRef] = List.fill(numRandomUsers) {
    val timeHash = UUID.randomUUID().toString.split('-').head

    system.actorOf(RandomUser.props, s"randomUser+$timeHash")
  }

  users.foreach { user =>
    system.scheduler.scheduleOnce(FiniteDuration(Random.nextInt(60) + 1, SECONDS)) {
      user ! OptobotMessage.InitializeWithConfig(imageUploadIntervalMax = 300)
    }
  }

  system.awaitTermination()
}
