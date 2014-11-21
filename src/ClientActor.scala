import java.io.PrintStream

import akka.actor.Actor

import scala.io.BufferedSource

class ClientActor extends Actor {


  def receive = {
    case HTTPRequest(socket) => {
      val in = new BufferedSource(socket.getInputStream).getLines()
      val out = new PrintStream(socket.getOutputStream)
      val response = HTTPResponse(in)
      out.println(response.response)
      out.println(in.toStream)
      socket.close()
    }
  }
}
