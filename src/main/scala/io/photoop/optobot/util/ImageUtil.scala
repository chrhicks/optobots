package io.photoop.optobot.util

import java.io.{FileInputStream, BufferedInputStream, File}
import java.net.URLConnection
import java.nio.file.{Files, Paths}

import org.apache.commons.codec.binary.Base64

object ImageUtil {
  def getBase64EncodedImageDataString(imagePath: String): String = {
    val f = new File(imagePath)
    val path = Paths.get(imagePath)
    val byteArray = Files.readAllBytes(path)
    val encoded = Base64.encodeBase64String(byteArray)

    val is = new BufferedInputStream(new FileInputStream(f))
    val mimeType = URLConnection.guessContentTypeFromStream(is)

    is.close()

    s"data:$mimeType;base64,$encoded"
  }
}
