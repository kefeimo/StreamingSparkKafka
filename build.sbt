name := "StreamingSparkKafka"

version := "0.1"

scalaVersion := "2.11.12"

val sparkVersion = "2.4.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion,
  "org.apache.spark" %% "spark-hive" % sparkVersion,
  "org.apache.spark" %% "spark-streaming" % sparkVersion,

//  "mysql" % "mysql-connector-java" % "5.1.46",

  //  "org.apache.kafka" % "kafka_2.12" % "2.0.0",
  "org.apache.kafka" % "kafka-clients" % "2.3.1",

  "org.apache.spark" % "spark-streaming-kafka-0-8_2.11" % sparkVersion
  //  "org.apache.spark" % "spark-streaming-kafka-0-10-assembly_2.12" % sparkVersion,


  //  "org.apache.kafka" % "kafka_2.12" % "2.0.0"

  //  "org.scalactic" %% "scalactic" % "3.2.7",
  //  "org.scalatest" %% "scalatest" % "3.2.7" % Test
)
