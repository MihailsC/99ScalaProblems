package problems

import scala.reflect.ClassTag

object P02 {

  def reverseTailHead[T](is: List[T]): T = is.reverse.tail.head

  def initLast[T](is: List[T]): T = is.init.last

  def preLastAsArray[T](is: List[T])(implicit ct: ClassTag[T]): T = {
    val arr = is.toArray[T]
    arr(is.length - 2)
  }

  @scala.annotation.tailrec
  def recursive[T](is: List[T]): T = is match {
    case preLast :: _ :: Nil => preLast
    case _ :: tail => recursive(tail)
    case Nil => throw new NoSuchElementException
  }

  def splitAtPreLast[T](is: List[T]): T = {
    val (_, preLast) = is.splitAt(is.size - 2)
    preLast.head
  }

  def dropHead[T](is: List[T]): T = is.drop(is.length-2).head

  def takeRightHead[T](is: List[T]): T = is.takeRight(2).head

}
