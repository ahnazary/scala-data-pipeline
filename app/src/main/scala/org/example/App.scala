package org.example

import org.example.Ticker

object App {
  def main(args: Array[String]): Unit = {

  val ticker = new Ticker("MSFT", "annual")
  print(ticker.send_get_request())
  }
}