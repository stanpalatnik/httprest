/**
 * Created by stan on 11/20/14.
 */
sealed trait HTTPMessage

case class HTTPRequest(body: String) extends HTTPRequest

case class HTTPResponse(wordCount:Int, dictionary: String) extends HTTPRequest

