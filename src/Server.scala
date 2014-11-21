import java.net.ServerSocket


/**
 * Created by stan on 11/20/14.
 */
object Server extends App {
  final val NUM_OF_WORKERS = 10

  val system = ActorSystem("restserver")

  val workerRouter = context.actorOf(
    Props[ClientActor].withRouter(RoundRobinRouter(NUM_OF_WORKERS)), name = "clientActor")


  while(true) {
    val socket = new ServerSocket(80)
    val s = socket.accept()
    val in = new BufferedSource(s.getInputStream()).getLines()
    val out = new PrintStream(s.getOutputStream())
  }
}
