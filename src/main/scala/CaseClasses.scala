
object CaseClasses extends App {

  case class Character(PrimaryName: String, hasARing: Boolean) {
  }

  val h = Character("Frodo Baggins", true)

  val whoHasARing = h match {
    case Character(x, true) => s"$x has a ring"
    case Character(x, false) => s"$x does not have a ring"
  }

  println(whoHasARing)
}
