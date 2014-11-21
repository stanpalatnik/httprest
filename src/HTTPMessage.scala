import java.net.Socket

sealed trait HTTPMessage

case class HTTPRequest(connection: Socket) extends HTTPMessage

case class HTTPResponse(response: String) extends HTTPMessage

object HTTPResponse {
  def apply(requestLines: Iterator[String]): HTTPResponse = {
    //generate html response
    HTTPResponse("test")
  }
}

