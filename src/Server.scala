import java.net.ServerSocket

import akka.actor.{Props, ActorSystem}
import akka.routing.RoundRobinRouter

object Server extends App {
  final val NUM_OF_WORKERS = 10
  final val SERVER_PORT = 8082
  //keep an md5 hash of the request, to keep a cache
  //final val requestCache[] = mutable.Map

  val system = ActorSystem("restserver")

  val workerRouter = system.actorOf(
    Props[ClientActor].withRouter(RoundRobinRouter(NUM_OF_WORKERS)), name = "clientActor")


  while(true) {
    val socket = new ServerSocket(SERVER_PORT)
    val s = socket.accept()
    workerRouter ! HTTPRequest(s)
  }
}
