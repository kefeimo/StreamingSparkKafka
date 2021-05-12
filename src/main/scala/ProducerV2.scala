

import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming._


object ProducerV2 extends App {
  val spark = SparkSession.builder()
    .appName("")
    .master("local[*]")
    .getOrCreate()

  val sc = spark.sparkContext
  sc.setLogLevel("ERROR")

  val ssc = new StreamingContext(sc, Seconds(5))

  //  create an ssd for processing
  val rddData = sc.parallelize(Seq((1,"one"), (2,"two"), (3,"three")))



  val props:Properties = new Properties()
  props.put("bootstrap.servers","localhost:9092")
  props.put("key.serializer",
    "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer",
    "org.apache.kafka.common.serialization.StringSerializer")
    props.put("acks","all")
  val producer = new KafkaProducer[String, String](props)
  val topic = "Hello-Kafka-1"

  rddData.foreach(x => {
    val record = new ProducerRecord[String, String](topic, x._1.toString, x._2)
    try {
      val metadata = producer.send(record)
      printf(s" record(key=%s value=%s) " +
        "meta(partition=%d, offset=%d)\n",
        record.key(), record.value(),
        metadata.get().partition(),
        metadata.get().offset()
      )
    }
    catch {
      case e: Exception => e.printStackTrace()
    }
  })

  producer.close()


}


