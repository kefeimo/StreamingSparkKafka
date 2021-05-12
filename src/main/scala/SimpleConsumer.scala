import java.util.{Collections, Properties}
import java.util.regex.Pattern
import org.apache.kafka.clients.consumer.KafkaConsumer
import scala.collection.JavaConverters._
import java.time.Duration


object SimpleConsumer extends App {

  val props:Properties = new Properties()
  props.put("group.id", "test")
  props.put("bootstrap.servers","localhost:9092")
  props.put("key.deserializer",
    "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer",
    "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("enable.auto.commit", "true")
  props.put("auto.commit.interval.ms", "1000")
  props.put("request.timeout.ms", "10000")
  val consumer = new KafkaConsumer(props)
  val topics = List("Hello-Kafka")
  try {
    consumer.subscribe(topics.asJava)
    val startTime = System.currentTimeMillis //fetch starting time
    while ((System.currentTimeMillis() - startTime) < 10000) {
      val records = consumer.poll(Duration.ofMillis(100))
      for (record <- records.asScala) {
        println("Topic: " + record.topic() +
          ",Key: " + record.key() +
          ",Value: " + record.value() +
          ", Offset: " + record.offset() +
          ", Partition: " + record.partition())
      }
    }
  }catch{
    case e:Exception => e.printStackTrace()
  }finally {
    consumer.close()
  }
}

