package io.photoop

import java.util.UUID

import akka.actor._
import io.photoop.OptoBotMaster.Initialize
import io.photoop.optobot.bot.ImageUploader.ImageUploaded
import io.photoop.optobot.bot.OptobotMessage.InitializeWithConfig
import io.photoop.optobot.bot.SystemReporter.Report
import io.photoop.optobot.bot._

import scala.concurrent.duration.FiniteDuration
import scala.util.Random
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object OptoBotMaster {
  val props = Props[OptoBotMaster]

  case class Initialize(desiredCount: Int)
}

class OptoBotMaster extends Actor with ActorLogging {
  def receive = {
    case i:Initialize => {
      // Kick off stats reporter
      val systemReporter = context.actorOf(SystemReporter.props, "SystemReporter")
      systemReporter ! io.photoop.optobot.bot.SystemReporter.Initialize
      context.system.scheduler.schedule(1.second, 5.seconds) {
        systemReporter ! Report
      }

      // Random User - uploads images and manages profile
      for (n <- 0 to i.desiredCount - 1) {
        val timeHash = UUID.randomUUID().toString.split('-').take(2).mkString

        val randomUser = context.actorOf(RandomUser.props, s"randomUser+$timeHash")

        context.system.scheduler.scheduleOnce(FiniteDuration(Random.nextInt(60) + 1, SECONDS)) {
          randomUser ! InitializeWithConfig(300, RandomUserGenerator.getJunkUser)
        }
      }

      // Random Normal - likes every 1 - 5 minutes
      for (n <- 0 to 1000) {
        val timeHash = UUID.randomUUID().toString.split('-').take(2).mkString
        val likeRandomBot = context.actorOf(LikeRandom.props, s"likeRandom+$timeHash")

        likeRandomBot ! io.photoop.optobot.bot.LikeRandom.Initialize(RandomUserGenerator.getJunkUser, FiniteDuration(Random.nextInt(240) + 60, SECONDS))
      }

      // Random Aggressive - likes every 0 - 60 seconds
      for (n <- 0 to 100) {
        val timeHash = UUID.randomUUID().toString.split('-').take(2).mkString
        val likeRandomBot = context.actorOf(LikeRandom.props, s"likeRandom+$timeHash")

        likeRandomBot ! io.photoop.optobot.bot.LikeRandom.Initialize(RandomUserGenerator.getJunkUser, FiniteDuration(Random.nextInt(60), SECONDS))
      }

      // Popular Normal - likes every 1 - 5 minutes
      for (n <- 0 to 1000) {
        val timeHash = UUID.randomUUID().toString.split('-').take(2).mkString
        val likeRandomBot = context.actorOf(LikePopular.props, s"likePopular+$timeHash")

        likeRandomBot ! io.photoop.optobot.bot.LikePopular.Initialize(RandomUserGenerator.getJunkUser, FiniteDuration(Random.nextInt(240) + 60, SECONDS))
      }

      // Popular aggressive - likes every 0 - 60 seconds
      for (n <- 0 to 100) {
        val timeHash = UUID.randomUUID().toString.split('-').take(2).mkString
        val likeRandomBot = context.actorOf(LikePopular.props, s"likePopular+$timeHash")

        likeRandomBot ! io.photoop.optobot.bot.LikePopular.Initialize(RandomUserGenerator.getJunkUser, FiniteDuration(Random.nextInt(60), SECONDS))
      }
    }

    case uploadedImage:ImageUploaded =>
      InMemoryImageStore.recordUpload(uploadedImage)
  }
}

object OptobotMain extends App {
  val system = ActorSystem("OptobotSystem")

  val numRandomUsers = 500

  val master = system.actorOf(OptoBotMaster.props, "OptoBotMaster")

//  RandomUserGenerator.init(system, master, numRandomUsers)
  master ! Initialize(500)

  system.awaitTermination()
}
