package myPackage

import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object KafkaStream {
  def main(args: Array[String])
  {
 val conf = new SparkConf().setMaster("local[*]").setAppName("KafkaStream")

  val kafkaParams = Map[String, Object](
    "bootstrap.servers" -> "localhost:9092",
    "key.deserializer" -> classOf[StringDeserializer],
    "value.deserializer" -> classOf[StringDeserializer],
    "group.id" -> "spark-playground-group"
    //"auto.offset.reset" -> "latest",
    //"enable.auto.commit" -> (false: java.lang.Boolean)
  )
  val ssc = new StreamingContext(conf, Seconds(10))


  val inputStream = KafkaUtils.createDirectStream(ssc, PreferConsistent, Subscribe[String, String](Array("test"), kafkaParams))
  val processedStream = inputStream
    .flatMap(record => record.value.split(" "))
    .map(x => (x, 1))
    .reduceByKey((x, y) => x + y)
print("Running")
  processedStream.print()
  ssc.start()
  ssc.awaitTermination()

}

}