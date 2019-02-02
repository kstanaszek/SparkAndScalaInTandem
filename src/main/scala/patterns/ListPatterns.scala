package patterns

object ListPatterns extends App {
  //List example
  List(1, 2, 3) match {
    case List(1, _*) => println("found it")
    case _ =>
  }
}