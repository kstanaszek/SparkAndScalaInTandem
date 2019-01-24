object Implicits extends App {

  implicit class IntUtils(val x: Int) {
    def addOne= x +1
  }

  val myval: Int = 1

  println(myval addOne)

}

