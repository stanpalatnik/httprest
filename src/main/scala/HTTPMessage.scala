import java.net.Socket
import scala.io.BufferedSource

sealed trait HTTPMessage

case class HTTPRequest(connection: Socket) extends HTTPMessage

case class HTTPResponse(response: String) extends HTTPMessage

object HTTPResponse {
  def apply(str: Stream[String]): HTTPResponse = {
    val words = str.mkString.split(" ")
    val wordCount = words.length
    val wordMap = words.foldLeft( Map[String, Int]() ) {
      (accumulator, currentWord) =>
        accumulator + (currentWord ->
          (accumulator.getOrElse(currentWord, 0) + 1)) }
    val responseBuilder = new StringBuilder
    responseBuilder.append("Word Count: ").append(wordCount).append(" and the word breakdown is: ")
    for((word, count) <- wordMap) {
      responseBuilder.append("Word: ").append(word).append(" appeared: ")
        .append(count).append("times.").append("\n")
    }
    HTTPResponse(responseBuilder.toString())
  }
}

