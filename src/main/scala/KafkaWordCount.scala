import java.util.HashMap

import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.spark.SparkConf
import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.sql.SparkSession

object KafkaWordCount {
  def main(args: Array[String]) {
    val args = Array("localhost:2181", "test1", "Hello-Kafka", "1")
    if (args.length < 4) {
      System.err.println("Usage: KafkaWordCount <zkQuorum><group> <topics> <numThreads>")
      System.exit(1)
    }

    val Array(zkQuorum, group, topics, numThreads) = args

    val spark = SparkSession.builder()
      .appName("KafkaWordCount")
      .master("local[*]")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("ERROR")

    val ssc = new StreamingContext(sc, Seconds(5))


//    val sparkConf = new SparkConf()
//      .setAppName("KafkaWordCount")
//      .setMaster("local[*]")//
//
//    val ssc = new StreamingContext(sparkConf, Seconds(2))

    ssc.checkpoint("checkpoint")

    val topicMap = topics.split(",").map((_, numThreads.toInt)).toMap
    val lines = KafkaUtils.createStream(ssc, zkQuorum, group, topicMap).map(_._2)
//    val words = lines.flatMap(_.split(" "))
//    val wordCounts = words.map(x => (x, 1L))
//      .reduceByKeyAndWindow(_ + _, _ - _, Minutes(10), Seconds(2), 2)
//    wordCounts.print()
    lines.print()

    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }
}