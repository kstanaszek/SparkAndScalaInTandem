package functions

object FunctionsAsValues extends App {
  val add = (a: Int, b: Int) =>  a + b

  def substract = (a: Int, b: Int) => a -b

  println(add(1,2))
}
