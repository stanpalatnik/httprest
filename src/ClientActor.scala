import java.io.PrintStream

import akka.actor.Actor

import scala.io.BufferedSource

class ClientActor extends Actor {


  def receive = {
    case HTTPRequest(socket) => {
      val in = new BufferedSource(socket.getInputStream).getLines()
      val out = new PrintStream(socket.getOutputStream)
      out.println("received: " + in.toStream)
      socket.close()
    }
  }
}
