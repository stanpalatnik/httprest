import java.net.Socket
import scala.io.BufferedSource

sealed trait HTTPMessage

case class HTTPRequest(connection: Socket) extends HTTPMessage

case class HTTPResponse(response: String) extends HTTPMessage

object HTTPResponse {
  def apply(str: Stream[String]): HTTPResponse = {
    HTTPResponse(str)
  }
}

