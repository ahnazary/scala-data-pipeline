package org.example
import sttp.client4.quick.*
import sttp.client4.Response
import sttp.client4.httpclient.HttpClientSyncBackend
import com.typesafe.config.ConfigFactory

class Ticker(val symbol: String, val frequency: String) {
  val config = ConfigFactory.load()
  val BASE_URL: String = config.getString("api.BASE_URL")
  private val httpClient = HttpClientSyncBackend()

  def getSymbol(): String = {
    return symbol
  }

  def getFrequency(): String = {
    return frequency
  }

  def send_get_request(): Response[String] = {
    val headers = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36"
    val uri = uri"$BASE_URL/$symbol"

    val request = basicRequest.get(uri).header("User-Agent", headers)

    val response = quickRequest.get(uri).header("User-Agent", headers).send(httpClient)
    return response
  }

  httpClient.close()
}
