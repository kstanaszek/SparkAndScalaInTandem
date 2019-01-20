import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window

object zJavaDemo extends Logger with App {

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

  val charactersKeywords = characters.map(row => row.Id).collect()

  val fileContents = spark.sparkContext.wholeTextFiles(getClass.getResource("/Tolkien/scripts").toString)

  val wordFileNameOnes = fileContents.flatMap{ case (filePath, fileContent) =>
    val fileName = filePath.split("/").last

    fileContent.split("""\W+""")
      .map(word => word.toLowerCase)
      .filter(charactersKeywords.contains(_))
      .map(word => ((word, fileName), 1))

  }.reduceByKey((count1, count2) => count1 + count2)

  val wordGroups = wordFileNameOnes.map{
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

  val resultsBook = wordTotalCountAndReferences
    .toDF("Character", "TotalCount", "References")

  import org.apache.spark.sql.functions._

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
