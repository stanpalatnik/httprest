import java.io.PrintStream
import akka.actor.Actor
import scala.io.BufferedSource

class ClientActor extends Actor {

  def receive = {
    case HTTPRequest(socket) => {
      val in = new BufferedSource(socket.getInputStream)
      val strStream = Stream.continually(in.bufferedReader().readLine).takeWhile(validLine)
      val out = new PrintStream(socket.getOutputStream)
      val response = getResponse(strStream)
      out.println(response.response)
      socket.close()
    }
  }

  def validLine(line: String): Boolean = {
    val valid = line.length > 0
    valid
  }

  def getResponse(requestStrStream: Stream[String]): HTTPResponse = {
      val key = SecurityUtil.md5(requestStrStream.mkString)
      RequestCache.get(key) match {
        case Some(reply) => reply
        case None        =>  {
          val response = HTTPResponse(requestStrStream)
          RequestCache.put(key, response)
          response
        }
      }
  }
}
