package patterns


object CasePatterns extends App {
  //case sequence as a function literal
  val whoHasRingFunc: MyCharacter => String = {
    case MyCharacter(x, true) => x
    case MyCharacter(y,_) => "Don't know"
  }
  println(whoHasRingFunc(MyCharacter("Bilbo", false)))


}
