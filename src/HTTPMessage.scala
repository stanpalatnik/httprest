import java.net.Socket

sealed trait HTTPMessage

case class HTTPRequest(connection: Socket) extends HTTPMessage

case class HTTPResponse(wordCount:Int, dictionary: String) extends HTTPMessage

