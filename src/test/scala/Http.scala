import java.net.URL
import org.scalatest.FunSuite

class Http extends FunSuite {

  test("test fail") {
    val connection = new URL("localhost:8083").openConnection()
    connection.setDoOutput(true); // Triggers POST.
    connection.setRequestProperty("Accept-Charset", "ASCII")
    connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=ASCII")
    val output = connection.getOutputStream
    output.write("lol".getBytes)
    val response = connection.getInputStream()
  }
}
