package functions

object FunctionsAsValues extends App {
  val add = (a: Int, b: Int) =>  a + b

  def subtract = (a: Int, b: Int) => a - b

  println(add(1,2))
}
