
object CaseClassesAndPatterns extends App {

  case class Character(Name: String, hasRing: Boolean) {
  }

  val myCharacter = Character("Frodo Baggins", true)

  val whoHasRing = myCharacter match {
    case Character(x, true) => s"$x has a ring"
    case _ => "Don't know"
  }
  println(whoHasRing)

  //Tuple example
  val myTuple = (1, "hello, zJava!", 20L, Character("Sam", false))
  myTuple match {
    case (_, _, _, h) => println(s"Matched character is ${h.Name}")
    case _ =>
  }

  //List example
  List(1, 2, 3) match {
    case List(0, _*) => println("found it")
    case _ =>
  }

  //Patterns everywhere - case class deconstruction
  val (_, _, _, sam) = myTuple

  //case sequence as a function literal
  val whoHasRingFunc: Character => String = {
    case Character(x, true) => x
    case Character(y,_) => "Don't know"
  }
  println(whoHasRingFunc(Character("Bilbo", false)))

}
