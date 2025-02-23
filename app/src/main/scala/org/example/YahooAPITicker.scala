package org.example

import org.apache.hadoop.fs.FileUtil
import org.example.Ticker
import org.example.Range
import org.example.DataGranularity
import sttp.client4.Response
import ujson.*
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{StringType, StructField, StructType, LongType, DoubleType}

import java.sql.Struct

class YahooAPITicker(symbol: String, frequency: String) extends Ticker(symbol, frequency) {

  private val spark = SparkSession.builder()
    .appName("YahooFinanceAPI")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._

  def yahoo_api_price(range: Range, dataGranularity: DataGranularity) = {
    // convert range to string
    val uri_input = s"${BASE_URL}/${symbol}?range=${range.toString}&interval=${dataGranularity.toString}"
    val response = send_get_request(uri_input)
    extract_json(response)
  }

   private def extract_json(response: Response[String]) = {
     val json = ujson.read(response.body)
     val timestamp = json("chart")("result")(0)("timestamp").arr.map(_.num.toLong).toList
     val close = json("chart")("result")(0)("indicators")("quote")(0)("close").arr.map(_.num).toList
     val open = json("chart")("result")(0)("indicators")("quote")(0)("open").arr.map(_.num).toList
     val high = json("chart")("result")(0)("indicators")("quote")(0)("high").arr.map(_.num).toList
     val low = json("chart")("result")(0)("indicators")("quote")(0)("low").arr.map(_.num).toList
     val volume = json("chart")("result")(0)("indicators")("quote")(0)("volume").arr.map(_.num).toList

     println("------")
     println(timestamp)
      println(timestamp.length)
      println(timestamp.getClass)

     val schema = StructType(
       List(
         StructField("timestamp", LongType, nullable = true),
          StructField("close", DoubleType, nullable = true),
          StructField("open", DoubleType, nullable = true),
          StructField("high", DoubleType, nullable = true),
          StructField("low", DoubleType, nullable = true),
          StructField("volume", DoubleType, nullable = true)
       )
     )

     val sparkContext = spark.sparkContext

     val rdd = sparkContext.parallelize(
       timestamp.indices.map(i => (
         timestamp(i),
         close(i),
         open(i),
         high(i),
         low(i),
         volume(i)
       ))
     )

     val rowRDD = rdd.map { case (ts, cl, op, hi, lo, vol) =>
       Row(ts, cl, op, hi, lo, vol)
     }

     val df: DataFrame = spark.createDataFrame(rowRDD, schema)
     println(df.show())

   }
 }
