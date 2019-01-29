
object CaseClasses extends App {

  case class Character(Id: String, PrimaryName: String, AlternativeName: String, hasRing: Boolean) {
  }

  val myCharacter = Character("frodo", "Frodo Baggins", "Mr. Underhill", true)
  val whoHasRing = myCharacter match {
    case Character(x, y, z, true) => s"$y has a ring"
    case _ => "Don't know"
  }
  println(whoHasRing)
}
