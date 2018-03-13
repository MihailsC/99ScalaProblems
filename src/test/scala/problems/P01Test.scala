package problems

import org.scalameter.api._
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop._
import org.scalatest.{FunSuite, Matchers}
import problems.P01._
import problems.P01Implementations._

import scala.reflect.ClassTag
import scala.util.Random

object P01Implementations {

  def implementations[T](implicit ct: ClassTag[T]): TableFor2[String, List[T] => T] = Table(("description", "function"),
    ("lastBuiltIn", lastBuiltIn[T] _),
    ("lastRecursive", lastRecursive[T] _),
    ("lastReverse", lastReverse[T] _),
    ("splitAtLast", splitAtLast[T] _),
    ("lastAsArray", lastAsArray[T] _)
  )
}

class P01Test extends FunSuite with Matchers {
  forAll(implementations[Int]) { (description, function) =>
    test(s"$description: Find the last element of a list.") {
      function(List(1, 1, 2, 3, 5, 8)) shouldBe 8
    }
  }
}

object P01Benchmark extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.range("size")(from = 1000, upto = 100000, hop = 100000 - 1000)
  val lists: Gen[List[Int]] = for (size <- sizes) yield List.fill(size)(Random.nextInt)

  performance of "P01" in {
    forAll(implementations[Int]) { (description, function) =>
      measure method description in {
        using(lists) in function
      }
    }
  }
}
