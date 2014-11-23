import java.io.PrintStream
import akka.actor.Actor
import scala.io.BufferedSource

class ClientActor extends Actor {

  def receive = {
    case HTTPRequest(socket) => {
      val in = new BufferedSource(socket.getInputStream)
      val requestStr = Stream.continually(in.bufferedReader().readLine).takeWhile(validLine).mkString
      val out = new PrintStream(socket.getOutputStream)
      val response = getResponse(requestStr)
      out.println(response.response)
      socket.close()
    }
  }

  def validLine(line: String): Boolean = {
    line != null && line.length > 0
  }

  def getResponse(requestStr: String): HTTPResponse = {
      val key = SecurityUtil.md5(requestStr)
      RequestCache.get(key) match {
        case Some(reply) => reply
        case None        =>  {
          val response = HTTPResponse(requestStr)
          RequestCache.put(key, response)
          response
        }
      }
  }

  def readRequest(in: BufferedSource): String = {
    val request = new StringBuilder
    val reader = in.bufferedReader()
    var continue = true
    while(continue) {
      val line = reader.readLine()
      if(line != null && line.length > 0)
        request.append(reader.readLine())
      else continue = false
    }
    request.toString()
  }
}
