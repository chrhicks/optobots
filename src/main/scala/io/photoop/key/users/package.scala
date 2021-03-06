/**
 * Generated by apidoc - http://www.apidoc.me
 * Service version: 0.0.1-dev
 * apidoc:0.9.27 http://www.apidoc.me/photoop/key-users/0.0.1-dev/ning_1_9_client
 */
package io.photoop.key.users.v0.models {

case class Error(
                  code: String,
                  message: String
                  )

case class ForgotPassword(
                           email: String,
                           token: String,
                           createdAt: _root_.org.joda.time.DateTime
                           )

case class ForgotPasswordForm(
                               email: String
                               )

case class Healthcheck(
                        status: String
                        )

case class User(
                 guid: _root_.java.util.UUID,
                 createdAt: _root_.org.joda.time.DateTime,
                 updatedAt: _root_.org.joda.time.DateTime,
                 email: String,
                 firstName: _root_.scala.Option[String] = None,
                 lastName: _root_.scala.Option[String] = None,
                 vanityKey: _root_.scala.Option[String] = None
                 )

case class UserAuthenticateForm(
                                 email: String,
                                 password: String
                                 )

case class UserForm(
                     email: String,
                     firstName: _root_.scala.Option[String] = None,
                     lastName: _root_.scala.Option[String] = None,
                     vanityKey: _root_.scala.Option[String] = None,
                     password: String
                     )

case class UserUpdateForm(
                           email: _root_.scala.Option[String] = None,
                           firstName: _root_.scala.Option[String] = None,
                           lastName: _root_.scala.Option[String] = None,
                           vanityKey: _root_.scala.Option[String] = None,
                           password: _root_.scala.Option[String] = None
                           )

}

package io.photoop.key.users.v0.models {

package object json {
  import play.api.libs.json.__
  import play.api.libs.json.JsString
  import play.api.libs.json.Writes
  import play.api.libs.functional.syntax._
  import io.photoop.key.users.v0.models.json._

  private[v0] implicit val jsonReadsUUID = __.read[String].map(java.util.UUID.fromString)

  private[v0] implicit val jsonWritesUUID = new Writes[java.util.UUID] {
    def writes(x: java.util.UUID) = JsString(x.toString)
  }

  private[v0] implicit val jsonReadsJodaDateTime = __.read[String].map { str =>
    import org.joda.time.format.ISODateTimeFormat.dateTimeParser
    dateTimeParser.parseDateTime(str)
  }

  private[v0] implicit val jsonWritesJodaDateTime = new Writes[org.joda.time.DateTime] {
    def writes(x: org.joda.time.DateTime) = {
      import org.joda.time.format.ISODateTimeFormat.dateTime
      val str = dateTime.print(x)
      JsString(str)
    }
  }

  implicit def jsonReadsUsersError: play.api.libs.json.Reads[Error] = {
    (
      (__ \ "code").read[String] and
        (__ \ "message").read[String]
      )(Error.apply _)
  }

  implicit def jsonWritesUsersError: play.api.libs.json.Writes[Error] = {
    (
      (__ \ "code").write[String] and
        (__ \ "message").write[String]
      )(unlift(Error.unapply _))
  }

  implicit def jsonReadsUsersForgotPassword: play.api.libs.json.Reads[ForgotPassword] = {
    (
      (__ \ "email").read[String] and
        (__ \ "token").read[String] and
        (__ \ "created_at").read[_root_.org.joda.time.DateTime]
      )(ForgotPassword.apply _)
  }

  implicit def jsonWritesUsersForgotPassword: play.api.libs.json.Writes[ForgotPassword] = {
    (
      (__ \ "email").write[String] and
        (__ \ "token").write[String] and
        (__ \ "created_at").write[_root_.org.joda.time.DateTime]
      )(unlift(ForgotPassword.unapply _))
  }

  implicit def jsonReadsUsersForgotPasswordForm: play.api.libs.json.Reads[ForgotPasswordForm] = {
    (__ \ "email").read[String].map { x => new ForgotPasswordForm(email = x) }
  }

  implicit def jsonWritesUsersForgotPasswordForm: play.api.libs.json.Writes[ForgotPasswordForm] = new play.api.libs.json.Writes[ForgotPasswordForm] {
    def writes(x: ForgotPasswordForm) = play.api.libs.json.Json.obj(
      "email" -> play.api.libs.json.Json.toJson(x.email)
    )
  }

  implicit def jsonReadsUsersHealthcheck: play.api.libs.json.Reads[Healthcheck] = {
    (__ \ "status").read[String].map { x => new Healthcheck(status = x) }
  }

  implicit def jsonWritesUsersHealthcheck: play.api.libs.json.Writes[Healthcheck] = new play.api.libs.json.Writes[Healthcheck] {
    def writes(x: Healthcheck) = play.api.libs.json.Json.obj(
      "status" -> play.api.libs.json.Json.toJson(x.status)
    )
  }

  implicit def jsonReadsUsersUser: play.api.libs.json.Reads[User] = {
    (
      (__ \ "guid").read[_root_.java.util.UUID] and
        (__ \ "created_at").read[_root_.org.joda.time.DateTime] and
        (__ \ "updated_at").read[_root_.org.joda.time.DateTime] and
        (__ \ "email").read[String] and
        (__ \ "first_name").readNullable[String] and
        (__ \ "last_name").readNullable[String] and
        (__ \ "vanity_key").readNullable[String]
      )(User.apply _)
  }

  implicit def jsonWritesUsersUser: play.api.libs.json.Writes[User] = {
    (
      (__ \ "guid").write[_root_.java.util.UUID] and
        (__ \ "created_at").write[_root_.org.joda.time.DateTime] and
        (__ \ "updated_at").write[_root_.org.joda.time.DateTime] and
        (__ \ "email").write[String] and
        (__ \ "first_name").writeNullable[String] and
        (__ \ "last_name").writeNullable[String] and
        (__ \ "vanity_key").writeNullable[String]
      )(unlift(User.unapply _))
  }

  implicit def jsonReadsUsersUserAuthenticateForm: play.api.libs.json.Reads[UserAuthenticateForm] = {
    (
      (__ \ "email").read[String] and
        (__ \ "password").read[String]
      )(UserAuthenticateForm.apply _)
  }

  implicit def jsonWritesUsersUserAuthenticateForm: play.api.libs.json.Writes[UserAuthenticateForm] = {
    (
      (__ \ "email").write[String] and
        (__ \ "password").write[String]
      )(unlift(UserAuthenticateForm.unapply _))
  }

  implicit def jsonReadsUsersUserForm: play.api.libs.json.Reads[UserForm] = {
    (
      (__ \ "email").read[String] and
        (__ \ "first_name").readNullable[String] and
        (__ \ "last_name").readNullable[String] and
        (__ \ "vanity_key").readNullable[String] and
        (__ \ "password").read[String]
      )(UserForm.apply _)
  }

  implicit def jsonWritesUsersUserForm: play.api.libs.json.Writes[UserForm] = {
    (
      (__ \ "email").write[String] and
        (__ \ "first_name").writeNullable[String] and
        (__ \ "last_name").writeNullable[String] and
        (__ \ "vanity_key").writeNullable[String] and
        (__ \ "password").write[String]
      )(unlift(UserForm.unapply _))
  }

  implicit def jsonReadsUsersUserUpdateForm: play.api.libs.json.Reads[UserUpdateForm] = {
    (
      (__ \ "email").readNullable[String] and
        (__ \ "first_name").readNullable[String] and
        (__ \ "last_name").readNullable[String] and
        (__ \ "vanity_key").readNullable[String] and
        (__ \ "password").readNullable[String]
      )(UserUpdateForm.apply _)
  }

  implicit def jsonWritesUsersUserUpdateForm: play.api.libs.json.Writes[UserUpdateForm] = {
    (
      (__ \ "email").writeNullable[String] and
        (__ \ "first_name").writeNullable[String] and
        (__ \ "last_name").writeNullable[String] and
        (__ \ "vanity_key").writeNullable[String] and
        (__ \ "password").writeNullable[String]
      )(unlift(UserUpdateForm.unapply _))
  }
}
}



package io.photoop.key.users.v0 {
import com.ning.http.client.{AsyncCompletionHandler, AsyncHttpClient, AsyncHttpClientConfig, Realm, Request, RequestBuilder, Response}

object Constants {

  val UserAgent = "apidoc:0.9.27 http://www.apidoc.me/photoop/key-users/0.0.1-dev/ning_1_9_client"
  val Version = "0.0.1-dev"
  val VersionMajor = 0

}

class Client(
              apiUrl: String,
              auth: scala.Option[io.photoop.key.users.v0.Authorization] = None,
              defaultHeaders: Seq[(String, String)] = Nil,
              asyncHttpClient: AsyncHttpClient = Client.defaultAsyncHttpClient
              ) {
  import org.slf4j.Logger
  import org.slf4j.LoggerFactory
  import io.photoop.key.users.v0.models.json._

  val logger = LoggerFactory.getLogger(getClass)

  def forgotPasswords: ForgotPasswords = ForgotPasswords

  def healthchecks: Healthchecks = Healthchecks

  def users: Users = Users

  object ForgotPasswords extends ForgotPasswords {
    override def post(
                       forgotPasswordForm: io.photoop.key.users.v0.models.ForgotPasswordForm
                       )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.ForgotPassword] = {
      val payload = play.api.libs.json.Json.toJson(forgotPasswordForm)

      _executeRequest("POST", s"/forgot_passwords", body = Some(payload)).map {
        case r if r.getStatusCode == 200 => _root_.io.photoop.key.users.v0.Client.parseJson("io.photoop.key.users.v0.models.ForgotPassword", r, _.validate[io.photoop.key.users.v0.models.ForgotPassword])
        case r if r.getStatusCode == 409 => throw new io.photoop.key.users.v0.errors.ErrorsResponse(r)
        case r => throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Unsupported response code[${r.getStatusCode}]. Expected: 200, 409", requestUri = Some(r.getUri))
      }
    }

    override def putByToken(
                             token: String
                             )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User] = {
      _executeRequest("PUT", s"/forgot_passwords/${_root_.io.photoop.key.users.v0.PathSegment.encode(token, "UTF-8")}").map {
        case r if r.getStatusCode == 200 => _root_.io.photoop.key.users.v0.Client.parseJson("io.photoop.key.users.v0.models.User", r, _.validate[io.photoop.key.users.v0.models.User])
        case r if r.getStatusCode == 409 => throw new io.photoop.key.users.v0.errors.ErrorsResponse(r)
        case r => throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Unsupported response code[${r.getStatusCode}]. Expected: 200, 409", requestUri = Some(r.getUri))
      }
    }
  }

  object Healthchecks extends Healthchecks {
    override def get()(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.Healthcheck] = {
      _executeRequest("GET", s"/healthchecks").map {
        case r if r.getStatusCode == 200 => _root_.io.photoop.key.users.v0.Client.parseJson("io.photoop.key.users.v0.models.Healthcheck", r, _.validate[io.photoop.key.users.v0.models.Healthcheck])
        case r => throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Unsupported response code[${r.getStatusCode}]. Expected: 200", requestUri = Some(r.getUri))
      }
    }
  }

  object Users extends Users {
    override def get(
                      guid: _root_.scala.Option[_root_.java.util.UUID] = None,
                      email: _root_.scala.Option[String] = None
                      )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User] = {
      val queryParameters = Seq(
        guid.map("guid" -> _.toString),
        email.map("email" -> _)
      ).flatten

      _executeRequest("GET", s"/users", queryParameters = queryParameters).map {
        case r if r.getStatusCode == 200 => _root_.io.photoop.key.users.v0.Client.parseJson("io.photoop.key.users.v0.models.User", r, _.validate[io.photoop.key.users.v0.models.User])
        case r => throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Unsupported response code[${r.getStatusCode}]. Expected: 200", requestUri = Some(r.getUri))
      }
    }

    override def post(
                       userForm: io.photoop.key.users.v0.models.UserForm
                       )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User] = {
      val payload = play.api.libs.json.Json.toJson(userForm)

      _executeRequest("POST", s"/users", body = Some(payload)).map {
        case r if r.getStatusCode == 200 => _root_.io.photoop.key.users.v0.Client.parseJson("io.photoop.key.users.v0.models.User", r, _.validate[io.photoop.key.users.v0.models.User])
        case r if r.getStatusCode == 409 => throw new io.photoop.key.users.v0.errors.ErrorsResponse(r)
        case r => throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Unsupported response code[${r.getStatusCode}]. Expected: 200, 409", requestUri = Some(r.getUri))
      }
    }

    override def postAuthenticate(
                                   userAuthenticateForm: io.photoop.key.users.v0.models.UserAuthenticateForm
                                   )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User] = {
      val payload = play.api.libs.json.Json.toJson(userAuthenticateForm)

      _executeRequest("POST", s"/users/authenticate", body = Some(payload)).map {
        case r if r.getStatusCode == 200 => _root_.io.photoop.key.users.v0.Client.parseJson("io.photoop.key.users.v0.models.User", r, _.validate[io.photoop.key.users.v0.models.User])
        case r if r.getStatusCode == 409 => throw new io.photoop.key.users.v0.errors.ErrorsResponse(r)
        case r => throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Unsupported response code[${r.getStatusCode}]. Expected: 200, 409", requestUri = Some(r.getUri))
      }
    }

    override def putByGuid(
                            guid: _root_.java.util.UUID,
                            userUpdateForm: io.photoop.key.users.v0.models.UserUpdateForm
                            )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User] = {
      val payload = play.api.libs.json.Json.toJson(userUpdateForm)

      _executeRequest("PUT", s"/users/${guid}", body = Some(payload)).map {
        case r if r.getStatusCode == 200 => _root_.io.photoop.key.users.v0.Client.parseJson("io.photoop.key.users.v0.models.User", r, _.validate[io.photoop.key.users.v0.models.User])
        case r if r.getStatusCode == 409 => throw new io.photoop.key.users.v0.errors.ErrorsResponse(r)
        case r => throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Unsupported response code[${r.getStatusCode}]. Expected: 200, 409", requestUri = Some(r.getUri))
      }
    }
  }

  def _logRequest(request: Request) {
    logger.info("_logRequest: " + request)
  }

  def _requestBuilder(method: String, path: String): RequestBuilder = {
    val builder = new RequestBuilder(method)
      .setUrl(apiUrl + path)
      .addHeader("User-Agent", Constants.UserAgent)
      .addHeader("X-Apidoc-Version", Constants.Version)
      .addHeader("X-Apidoc-Version-Major", Constants.VersionMajor.toString)

    defaultHeaders.foreach { h => builder.addHeader(h._1, h._2) }

    auth.fold(builder) { a =>
      a match {
        case Authorization.Basic(username, password) => {
          builder.setRealm(
            new Realm.RealmBuilder()
              .setPrincipal(username)
              .setUsePreemptiveAuth(true)
              .setScheme(Realm.AuthScheme.BASIC)
              .build()
          )
        }
        case _ => sys.error("Invalid authorization scheme[" + a.getClass + "]")
      }
    }
  }

  def _executeRequest(
                       method: String,
                       path: String,
                       queryParameters: Seq[(String, String)] = Seq.empty,
                       body: Option[play.api.libs.json.JsValue] = None
                       )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[com.ning.http.client.Response] = {
    val request = _requestBuilder(method, path)

    queryParameters.foreach { pair =>
      request.addQueryParameter(pair._1, pair._2)
    }

    val requestWithParamsAndBody = body.fold(request) { b =>
      val serialized = play.api.libs.json.Json.stringify(b)
      request.setBody(serialized).addHeader("Content-type", "application/json; charset=UTF-8")
    }

    val finalRequest = requestWithParamsAndBody.build()
    _logRequest(finalRequest)

    val result = scala.concurrent.Promise[com.ning.http.client.Response]()
    asyncHttpClient.executeRequest(finalRequest,
      new AsyncCompletionHandler[Unit]() {
        override def onCompleted(r: com.ning.http.client.Response) = result.success(r)
        override def onThrowable(t: Throwable) = result.failure(t)
      }
    )
    result.future
  }

}

object Client {

  private lazy val defaultAsyncHttpClient = {
    new AsyncHttpClient(
      new AsyncHttpClientConfig.Builder()
        .setExecutorService(java.util.concurrent.Executors.newCachedThreadPool())
        .build()
    )
  }

  def parseJson[T](
                    className: String,
                    r: _root_.com.ning.http.client.Response,
                    f: (play.api.libs.json.JsValue => play.api.libs.json.JsResult[T])
                    ): T = {
    f(play.api.libs.json.Json.parse(r.getResponseBody("UTF-8"))) match {
      case play.api.libs.json.JsSuccess(x, _) => x
      case play.api.libs.json.JsError(errors) => {
        throw new io.photoop.key.users.v0.errors.FailedRequest(r.getStatusCode, s"Invalid json for class[" + className + "]: " + errors.mkString(" "), requestUri = Some(r.getUri))
      }
    }
  }

}

sealed trait Authorization
object Authorization {
  case class Basic(username: String, password: Option[String] = None) extends Authorization
}

trait ForgotPasswords {
  /**
   * Creates forgot password request for a user identified by the provided email
   * address.
   */
  def post(
            forgotPasswordForm: io.photoop.key.users.v0.models.ForgotPasswordForm
            )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.ForgotPassword]

  /**
   * Validates a forgot password token. If valid, return true.
   */
  def putByToken(
                  token: String
                  )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User]
}

trait Healthchecks {
  /**
   * Healthcheck for this service
   */
  def get()(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.Healthcheck]
}

trait Users {
  /**
   * Find a user.
   */
  def get(
           guid: _root_.scala.Option[_root_.java.util.UUID] = None,
           email: _root_.scala.Option[String] = None
           )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User]

  /**
   * Creates a user.
   */
  def post(
            userForm: io.photoop.key.users.v0.models.UserForm
            )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User]

  /**
   * Authenticate a user.
   */
  def postAuthenticate(
                        userAuthenticateForm: io.photoop.key.users.v0.models.UserAuthenticateForm
                        )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User]

  /**
   * Update information about a user.
   */
  def putByGuid(
                 guid: _root_.java.util.UUID,
                 userUpdateForm: io.photoop.key.users.v0.models.UserUpdateForm
                 )(implicit ec: scala.concurrent.ExecutionContext): scala.concurrent.Future[io.photoop.key.users.v0.models.User]
}

package errors {

import io.photoop.key.users.v0.models.json._

case class ErrorsResponse(
                           response: _root_.com.ning.http.client.Response,
                           message: Option[String] = None
                           ) extends Exception(message.getOrElse(response.getStatusCode + ": " + response.getResponseBody("UTF-8"))){
  lazy val errors = _root_.io.photoop.key.users.v0.Client.parseJson("Seq[io.photoop.key.users.v0.models.Error]", response, _.validate[Seq[io.photoop.key.users.v0.models.Error]])
}

case class FailedRequest(responseCode: Int, message: String, requestUri: Option[_root_.java.net.URI] = None) extends Exception(s"HTTP $responseCode: $message")

}

object PathSegment {
  // See https://github.com/playframework/playframework/blob/2.3.x/framework/src/play/src/main/scala/play/utils/UriEncoding.scala
  def encode(s: String, inputCharset: String): String = {
    val in = s.getBytes(inputCharset)
    val out = new java.io.ByteArrayOutputStream()
    for (b <- in) {
      val allowed = segmentChars.get(b & 0xFF)
      if (allowed) {
        out.write(b)
      } else {
        out.write('%')
        out.write(upperHex((b >> 4) & 0xF))
        out.write(upperHex(b & 0xF))
      }
    }
    out.toString("US-ASCII")
  }

  private def upperHex(x: Int): Int = {
    // Assume 0 <= x < 16
    if (x < 10) (x + '0') else (x - 10 + 'A')
  }

  private[this] val segmentChars: java.util.BitSet = membershipTable(pchar)

  private def pchar: Seq[Char] = {
    val alphaDigit = for ((min, max) <- Seq(('a', 'z'), ('A', 'Z'), ('0', '9')); c <- min to max) yield c
    val unreserved = alphaDigit ++ Seq('-', '.', '_', '~')
    val subDelims = Seq('!', '$', '&', '\'', '(', ')', '*', '+', ',', ';', '=')
    unreserved ++ subDelims ++ Seq(':', '@')
  }

  private def membershipTable(chars: Seq[Char]): java.util.BitSet = {
    val bits = new java.util.BitSet(256)
    for (c <- chars) { bits.set(c.toInt) }
    bits
  }
}
}
