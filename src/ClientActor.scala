/**
 * Created by stan on 11/20/14.
 */
class ClientActor extends Actor {


  def receive = {
    case Work(start, nrOfElements) ⇒
      sender ! Result(calculatePiFor(start, nrOfElements)) // perform the work
  }
}
