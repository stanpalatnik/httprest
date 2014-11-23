import java.net.ServerSocket
import akka.actor.{Props, ActorSystem}
import akka.routing.RoundRobinRouter
import java.security.MessageDigest

object Server extends App {
  final val NUM_OF_WORKERS = 10
  final val SERVER_PORT = 8083

  val system = ActorSystem("httprest")

  val workerRouter = system.actorOf(
    Props[ClientActor].withRouter(RoundRobinRouter(NUM_OF_WORKERS)), name = "clientActor")

  val socket = new ServerSocket(SERVER_PORT)

  while (true) {
    val s = socket.accept()
    workerRouter ! HTTPRequest(s)
  }
}
