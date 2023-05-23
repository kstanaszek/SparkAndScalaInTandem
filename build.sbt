name := "SparkAndScalaInTandem"

version := "0.1"

scalaVersion := "2.13.10"

val sparkVersion = "3.4.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)
