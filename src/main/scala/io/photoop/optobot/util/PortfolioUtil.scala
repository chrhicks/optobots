package io.photoop.optobot.util

import java.util.UUID

import io.photoop.images.v0.models.Image
import io.photoop.portfolios.v0.models.{Portfolio, NewPortfolioGroupForm}

import scala.util.Random


object PortfolioUtil {

  val sampleGroups = Map (
    "wedding" -> "Wedding Photography",
    "fashion" -> "Fashion Photography",
    "portrait" -> "Portrait Photography",
    "beauty" -> "Beauty Photography",
    "advertising" -> "Advertising Photography",
    "event" -> "Event Photography",
    "architecture" -> "Architectural Photography",
    "landscape" -> "Landscape Photography"
  )

  def generateGroups: Seq[NewPortfolioGroupForm] = {
    val numGroups = Random.nextInt(5) + 1

    Random
      .shuffle(sampleGroups)
      .take(numGroups)
      .map {
        case (key, name) => NewPortfolioGroupForm(key, name)
      }.toSeq
  }

  def imagesNotInPortfolio(imgs: Seq[Image], portfolio: Portfolio): Seq[Image] = {
    val photosInPortfolio: Seq[UUID] = portfolio.portfolioGroups.foldLeft(Seq.empty[UUID]) { (accum, group) =>
      accum ++ group.images
    }

    imgs.filterNot(img => photosInPortfolio.contains(img.guid))
  }
}
