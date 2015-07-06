package io.photoop.optobot.bot

import java.util.UUID

import akka.actor.{Actor, ActorLogging, Props}
import io.photoop.images.v0.models.Image
import io.photoop.key.users.v0.models.User
import io.photoop.optobot.bot.OptobotMessage.InitializeWithUser
import io.photoop.optobot.util.PortfolioUtil
import io.photoop.portfolios.v0.errors.UnitResponse
import io.photoop.portfolios.v0.models.{NewPortfolioGroupImageForm, NewPortfolioForm, Portfolio}

import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random


class ProfileManager extends Actor with ActorLogging {
  import ProfileManager._

  private[ProfileManager] var user: Option[User] = None

  val imageFetchLimit = 25

  def receive = {
    case iwu:InitializeWithUser => user = Some(iwu.user)

    case Tick => {
      val imgs = getImages()
      val portfolioOpt: Option[Portfolio] = getPortfolio
      val unusedImages: Seq[Image] = portfolioOpt.map(p => PortfolioUtil.imagesNotInPortfolio(imgs, p)).getOrElse(Seq.empty)

      portfolioOpt match {
        case Some(portfolio) =>
          if (portfolio.portfolioGroups.isEmpty) {
            addNewPortfolioGroups(portfolio)
          } else {
            if (unusedImages.nonEmpty) {
              addImagesToGroups(unusedImages, portfolio)
            }
          }

        case None => log.error("No portfolio to operate on!")
      }
    }
  }

  def addImagesToGroups(imgs: Seq[Image], portfolio: Portfolio) = {
    val randomGroup = Random.shuffle(portfolio.portfolioGroups).head
    val randomImages = Random.shuffle(imgs).take(Random.nextInt(Math.min(10, imgs.size)))

    randomImages.foreach { img =>
      Await.result(
        portfolios.PortfolioGroups.postByUserGuidAndPortfolioGuidAndPortfolioGroupGuid(
          userGuid = user.get.guid,
          portfolioGuid = portfolio.guid,
          portfolioGroupGuid = randomGroup.guid,
          NewPortfolioGroupImageForm(img.guid)
        ),
        Duration("5000 ms")
      )
    }
  }

  def addNewPortfolioGroups(portfolio: Portfolio) = {
    val newGroups = PortfolioUtil.generateGroups
    newGroups.foreach { newGroup =>
      Await.result(
        portfolios.PortfolioGroups.postByUserGuidAndPortfolioGuid(user.get.guid, portfolio.guid, newGroup),
        Duration("5000 ms")
      )
    }
  }


  def getImages(offset: Int = 0, limit: Int = imageFetchLimit): Seq[Image] = {
    val imgsFuture = images.Images.get(userGuid = user.map(_.guid), offset = offset, limit = imageFetchLimit).flatMap { imgs =>
      if (imgs.size == imageFetchLimit) {
        Future.successful(imgs ++ getImages(offset + imageFetchLimit, imageFetchLimit))
      } else {
        Future.successful(imgs)
      }
    }

    Await.result(imgsFuture, Duration("5000 ms"))
  }

  def getPortfolio: Option[Portfolio] = {
    user.flatMap(u => getPortfolioByUserGuid(u.guid))
  }

  def getPortfolioByUserGuid(userGuid: UUID): Option[Portfolio] = {
    try {
      Some(Await.result(portfolios.Portfolios.getByUserGuid(userGuid), Duration("5000 ms")))
    } catch {
      case r:UnitResponse => if (r.status == 404) Some(createPortfolio(userGuid)) else None
      case e:Throwable => None
    }
  }

  def createPortfolio(userGuid: UUID): Portfolio = {
    Await.result(portfolios.Portfolios.post(NewPortfolioForm(userGuid)), Duration("5000 ms"))
  }
}

object ProfileManager {
  val props = Props[ProfileManager]

  case object Tick

  private val images = new io.photoop.images.v0.Client("http://localhost:7000")

  private val portfolios = new io.photoop.portfolios.v0.Client("http://localhost:7000")
}
