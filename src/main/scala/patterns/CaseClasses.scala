import patterns.MyCharacter

object CaseClasses extends App {

  val myCharacter = MyCharacter("Frodo Baggins", true)

  val whoHasRing = myCharacter match {
    case MyCharacter(x, true) => s"$x has a ring"
    case _ => "Don't know"
  }
  println(whoHasRing)
}

