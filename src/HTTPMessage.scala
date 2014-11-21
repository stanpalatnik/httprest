import java.net.Socket

sealed trait HTTPMessage

case class HTTPRequest(connection: Socket) extends HTTPMessage

case class HTTPResponse(response: String) extends HTTPMessage

object HTTPResponse {
  def apply(requestLines: Iterator[String]): HTTPResponse = {

    //Iterator.continually(requestLines).takeWhile(_.length > 2).foreach(line => println("read " + line))
    HTTPResponse("test")
  }

  def validLine(line: String): Unit = {
    val valid = line.length > 0
    valid
  }
}

