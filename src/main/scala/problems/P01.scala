package problems

import scala.reflect.ClassTag

object P01 {

  def lastBuiltIn[T](is: List[T]): T = is.last

  @scala.annotation.tailrec
  def lastRecursive[T](is: List[T]): T = is match {
    case last :: Nil => last
    case _ :: tail => lastRecursive(tail)
    case Nil => throw new NoSuchElementException
  }

  def lastReverse[T](is: List[T]): T = is.reverse.head

  def lastAsArray[T](is: List[T])(implicit ct: ClassTag[T]): T = {
    val a = is.toArray[T]
    a(a.length - 1)
  }

  def splitAtLast[T](is: List[T]): T = {
    val (_, last) = is.splitAt(is.size - 1)
    last.head
  }

}
