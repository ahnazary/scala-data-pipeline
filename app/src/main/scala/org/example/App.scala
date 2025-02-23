package org.example

import org.example.YahooAPITicker
import org.example.Range
import org.example.DataGranularity

object App {
  def main(args: Array[String]): Unit = {

  val ticker = new YahooAPITicker("MSFT", "annual")
    //print(ticker.send_get_request("https://query2.finance.yahoo.com/v8/finance/chart/MSFT").getClass())
    print(ticker.yahoo_api_price(Range.`1d`, DataGranularity.`1m`))
  }
}