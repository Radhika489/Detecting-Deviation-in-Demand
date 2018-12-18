name := "spark-playground"

version := "1.0"

scalaVersion := "2.11.8"


libraryDependencies += "org.apache.spark" %% "spark-core" % "2.2.1"

libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.2.1"

libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.2.1"
libraryDependencies += "org.apache.spark" %% "spark-sql-kafka-0-10" % "2.2.1"
