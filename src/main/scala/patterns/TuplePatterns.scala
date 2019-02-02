package patterns


object TuplePatterns extends App {
  //Tuple example
  val myTuple = (1, "hello, zJava!", 20L, MyCharacter("Sam", false))
  myTuple match {
    case (_, _, _, h) => println(s"Matched character is ${h.Name}")
    case _ =>
  }

  //Patterns everywhere - case class deconstruction
  val (_, _, _, sam) = myTuple

}
