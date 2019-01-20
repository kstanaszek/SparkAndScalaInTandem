# Apache Spark and Scala in Tandem

## This is a demo project to show how to use Scala and Spark together as a next-gen ETL framework.

1. Books downloaded from archive.org: https://archive.org/details/TheLordOfTheRing1TheFellowshipOfTheRing
2. Movies scripts downloaded from: https://www.imsdb.com 

For academical and non-profit purposes only.

##Demo Agenda
1. Why Spark
2. Why Scala
   * Objects - Scala has built-in support for the Singleton Design Pattern, i.e., when we only want one instance of a class. We use the object keyword. There is no static keyword in Scala. Instead of adding static methods and fields to classes as in Java, you put them in objects instead.
   * Mutable Variables vs. Immutable Values
   * Case Classes
   * Passing Functions as Arguments
   * Tuples
   * map v. flatMap - the latter instead of returning one output record for each input record, a flatMap returns a collection of new records, zero or more, for each input record. These collections are then flattened into one big collection.
   * braces instead of parentheses - we have the familiar block-like syntax {...}
   * Scala Vector - a collection with O(1) performance for most operations
   * Pattern Matching - the case keyword says I want to pattern match on the object passed to the function
     

