import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object zJavaDemo extends Logger with App {

  val spark: SparkSession = SparkSession.builder
    .master("local[*]")
    .appName("zJava: Spark with Scala Analytics")
    .getOrCreate()

  case class Character(Id: String, PrimaryName: String, AlternativeName: String) {
  }

  import spark.implicits._

  val characters = spark.read
    .option("header", true)
    .option("inferSchema", true)
    .csv(getClass.getResource("/Tolkien/characters.csv").toString)
    .as[Character]

  val charactersKeywords = characters.map(row => row.Id).collect()

  val fileContents: RDD[(String, String)] = spark.sparkContext.wholeTextFiles(getClass.getResource("/Tolkien/scripts").toString)

  fileContents.mapValues(_.split("""\W+"""))
    .mapValues(_.size)
    .map{case (_, count) => count}.reduce(_+_)

  val wordFileNameOnes: RDD[((String, String), Int)] = fileContents.flatMap { case (filePath, fileContent) =>
    val fileName = filePath.split("/").last

    fileContent.split("""\W+""")
      .map(word => word.toLowerCase)
      .filter(charactersKeywords.contains(_))
      .map(word => ((word, fileName), 1))

  }.reduceByKey((count1, count2) => count1 + count2)

  val wordGroups: RDD[(String, Iterable[(String, Int)])] = wordFileNameOnes.map{
    case ((word, fileName), count) => (word, (fileName, count))
  }.groupByKey

  val wordTotalCountAndReferences = wordGroups.map { case (word, iterable) =>
    val vect = iterable.toVector.sortBy {
      case (fileName, count) => (-count, fileName)
    }

    val (_, counts) = vect.unzip
    val totalCount = counts.sum
    (word, totalCount, vect.map(v => s"${v._1}:${v._2}"))
  }

  import org.apache.spark.sql.functions._

  val resultsBook = wordTotalCountAndReferences
    .toDF("Character", "TotalCount", "References")


  import org.apache.spark.sql.expressions.Window

  val results = resultsBook
    .join(characters, $"Character" === $"Id")
    .select($"PrimaryName", $"Character", $"TotalCount", $"References")
    .groupBy($"PrimaryName".as("Character"))
    .agg(sum($"TotalCount").as("TotalCount"), collect_list($"Character").as("Keywords"), collect_list($"References").as("References"))
    .withColumn("Ranking", row_number().over(Window.orderBy($"TotalCount".desc)))
    .select($"Ranking", $"Character", $"Keywords", $"TotalCount", $"References")
    .show(100, false)



  //results.createOrReplaceTempView("results")
  //spark.sqlContext.sql("select * from results order by Ranking desc").show(100, false)
}
