object PatternMatching extends App {

  abstract class Tree

  case class Node(elem: Int, left: Tree, right: Tree) extends Tree

  case class Leaf(elem: Int) extends Tree

  def getValue(node: Tree) = {
    node match {
      case Node(e, l, r) => e
      case Leaf(e) => e
    }
  }

  //examples
  val myval = getValue(new Leaf(100))
  println(myval)

}
