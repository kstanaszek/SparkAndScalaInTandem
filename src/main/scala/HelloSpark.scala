import org.apache.spark.sql.SparkSession
import zJavaDemo.getClass

object HelloSpark extends Logger with App {
  val spark: SparkSession = SparkSession.builder
    .master("local[*]")
    .appName("zJava: Spark with Scala Analytics")
    .getOrCreate()

  import spark.implicits._

  final case class Character(Id: String, PrimaryName: String, AlternativeName: String) {
  }

  val characters = spark.read
    .option("header", true)
    .option("inferSchema", true)
    .csv(getClass.getResource("/Tolkien/characters.csv").toString)
    .as[Character]
    .show(false)
}
