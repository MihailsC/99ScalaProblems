package problems

import scala.reflect.ClassTag

object P03 {

  def byIndex[T](k: Int, ts: List[T]): T = ts(k)

  def byIndexAsArray[T](k: Int, ts: List[T])(implicit ct: ClassTag[T]): T = {
    val arr = ts.toArray[T]
    arr(k)
  }

  def dropKHead[T](k: Int, ts: List[T]): T = ts.drop(k).head

  @scala.annotation.tailrec
  def recursive[T](k: Int, ts: List[T]): T = {
    k match {
      case 0 => ts.head
      case _ if k > 0 => recursive(k - 1, ts.tail)
      case _ => throw new NoSuchElementException
    }
  }
}
