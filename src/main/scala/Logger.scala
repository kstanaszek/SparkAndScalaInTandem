import org.apache.log4j.{Level, Logger}

trait Logger {
  Logger.getLogger("org").setLevel(Level.ERROR)
}
