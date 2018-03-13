package problems

import org.scalameter.api._
import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.prop._
import org.scalatest.{FunSuite, Matchers}
import problems.P02._
import problems.P02Implementations._

import scala.reflect.ClassTag
import scala.util.Random

object P02Implementations {

  def implementations[T](implicit ct: ClassTag[T]): TableFor2[String, List[T] => T] = Table(("description", "function"),
    ("takeRightHead", takeRightHead[T] _)
    ,("recursive", recursive[T] _)
    ,("reverseTailHead", reverseTailHead[T] _)
    ,("dropHead", dropHead[T] _)
    ,("splitAtPreLast", splitAtPreLast[T] _)
    ,("preLastAsArray", preLastAsArray[T] _)
    ,("initLast", initLast[T] _)
  )
}

class P02Test extends FunSuite with Matchers {
  forAll(implementations[Int]) { (description, function) =>
    test(s"$description: Find the last but one element of a list.") {
      function(List(1, 1, 2, 3, 5, 8)) shouldBe 5
    }
  }
}

object P02Benchmark extends Bench.LocalTime {

  val sizes: Gen[Int] = Gen.range("size")(from = 1000, upto = 100000, hop = 100000 - 1000)
  val lists: Gen[List[Int]] = for (size <- sizes) yield List.fill(size)(Random.nextInt)

  performance of "P02" in {
    forAll(implementations[Int]) { (description, function) =>
      measure method description in {
        using(lists) in function
      }
    }
  }
}
