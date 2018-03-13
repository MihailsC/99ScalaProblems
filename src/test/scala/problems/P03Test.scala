package problems

import org.scalameter.api._
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop._
import org.scalatest.{FunSuite, Matchers}
import problems.P03._
import problems.P03Implementations._

import scala.reflect.ClassTag
import scala.util.Random

object P03Implementations {

  def implementations[T](implicit ct: ClassTag[T]): TableFor2[String, (Int, List[T]) => T] = Table(("description", "function"),
    ("byIndex", byIndex[T] _)
    ,("dropKHead", dropKHead[T] _)
    ,("recursive", recursive[T] _)
    ,("byIndexAsArray", byIndexAsArray[T] _)
  )
}

class P03Test extends FunSuite with Matchers {
  val list = List(1, 1, 2, 3, 5, 8)

  forAll(implementations[Int]) { (description, function) =>
    test(s"$description: Find the last but one element of a list.") {
      function(0, list) shouldBe 1
      function(1, list) shouldBe 1
      function(2, list) shouldBe 2
      function(3, list) shouldBe 3
      function(4, list) shouldBe 5
      function(5, list) shouldBe 8
    }
  }
}

object P03Benchmark extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.range("size")(from = 1000, upto = 100000, hop = 100000 - 1000)
  val lastIndexedLists: Gen[(Int, List[Int])] = for (size <- sizes) yield size - 1 -> List.fill(size)(Random.nextInt)

  performance of "P03" in {
    forAll(implementations[Int]) { (description, function) =>
      measure method description in {
        using(lastIndexedLists) in {
          case (lastIndex, list) => function(lastIndex, list)
        }
      }
    }
  }
}
