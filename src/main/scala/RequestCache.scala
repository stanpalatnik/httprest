import scala.concurrent.stm._
import scala.collection.mutable

object RequestCache {
  private val requests: Ref[mutable.Map[String, HTTPResponse]] = Ref(mutable.Map[String, HTTPResponse]())

  def put(key: String, request: HTTPResponse): Unit = {
    atomic {
      implicit txn =>
        requests.get += (key -> request)
    }
  }

  def get(key: String) : Option[HTTPResponse] = {
    atomic {
      implicit txn =>
        requests.get.get(key)
    }
  }
}