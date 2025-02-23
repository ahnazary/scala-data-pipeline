package org.example
import sttp.client4.quick._
import sttp.client4.Response
import sttp.client4.httpclient.HttpClientSyncBackend
import com.typesafe.config.ConfigFactory

class Ticker(val symbol: String, val frequency: String) {
  private val config = ConfigFactory.load()
  val BASE_URL: String = config.getString("api.BASE_URL")
  private val httpClient = HttpClientSyncBackend()

  def getSymbol(): String = {
    return symbol
  }

  def getFrequency(): String = {
    return frequency
  }

  /*
  * Method that sends a GET request to the Yahoo Finance API

  * @param uri_input: String - The URI to send the GET request to
  * */
  def send_get_request(uri_input: String): Response[String] = {
    val uri = uri"$uri_input"
    val request = basicRequest.get(uri).header("User-Agent", get_user_agent())
    val response = quickRequest.get(uri).header("User-Agent", get_user_agent()).send(httpClient)
    return response
  }

  def close_http_client(): Unit =
    httpClient.close()

  /*
  * Method that returns the headers of the request based on the operating system
  * */
  def get_user_agent(): String = {
    val os = System.getProperty("os.name").toLowerCase()
    if (os.contains("win")) {
      return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
    } else if (os.contains("nix") || os.contains("nux")) {
      return "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
    } else {
      return "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
    }
  }
}
