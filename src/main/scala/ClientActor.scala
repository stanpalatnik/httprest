import java.io.PrintWriter
import akka.actor.Actor
import scala.io.BufferedSource

class ClientActor extends Actor {
  final val httpSeperator = "\r\n\r\n"
  final val maxSize = 10000000

  def receive = {
    case HTTPRequest(socket) => {
      val in = new BufferedSource(socket.getInputStream)

      val requestStr = in.mkString
      val bodyContent = requestStr.substring(requestStr.indexOf(httpSeperator)+4)
      val fileContent = bodyContent.substring(bodyContent.indexOf(httpSeperator)+4, bodyContent.lastIndexOf(httpSeperator))
      if(requestStr.length > maxSize) throw new Exception("File Size is greater than 10MB")
      val filteredFileContent = filterSpecialCharacters(fileContent)
      val out = new PrintWriter(socket.getOutputStream, true)
      val response = getResponse(filteredFileContent)
      out.println(response.response)
      socket.close()
    }
  }

  def filterSpecialCharacters(str: String): String = {
    str.replaceAll("[-+.^:,]()","")
  }

  def getResponse(requestStr: String): HTTPResponse = {
      val key = SecurityUtil.md5(requestStr)
      RequestCache.get(key) match {
        case Some(reply) => reply
        case None        =>
          val response = HTTPResponse(requestStr)
          RequestCache.put(key, response)
          response
      }
  }
}
