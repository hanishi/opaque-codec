package patterns.iarray

import scala.collection.mutable.ArrayBuffer

object ImmutableBuffer {
  opaque type ImmutableBuffer[+T] = ArrayBuffer[? <: T]

  def apply[T](elems: T*): ImmutableBuffer[T] = ArrayBuffer.from(elems)

  def unsafeFromArrayBuffer[T](buf: ArrayBuffer[T]): ImmutableBuffer[T] = buf

  extension [T](buf: ImmutableBuffer[T]) {
    def apply(index: Int): T = buf(index)
    def size: Int = buf.size
    def isEmpty: Boolean = buf.isEmpty
    def nonEmpty: Boolean = buf.nonEmpty
    def head: T = buf.head
    def last: T = buf.last
    def toList: List[T] = buf.toList
    def map[U](f: T => U): ImmutableBuffer[U] = ArrayBuffer.from(buf.map(f))
    def filter(p: T => Boolean): ImmutableBuffer[T] = ArrayBuffer.from(buf.filter(p))
    def foreach(f: T => Unit): Unit = buf.foreach(f)
  }
}
