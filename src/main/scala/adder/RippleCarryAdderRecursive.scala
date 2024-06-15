package adder

import chisel3._
import chisel3.util._

/**
  * RippleCarryAdder的递归版本
  *
  * @param bitWidth
  */
class RippleCarryAdderRecursive(bitWidth: Int) extends Adder(bitWidth) {
  val fullAdder = Module(new FullAdder)
  // io.a(0)表示io.a的第0个元素
  fullAdder.io.a := io.a(0)
  fullAdder.io.b := io.b(0)
  fullAdder.io.cin := io.cin

  if (bitWidth == 1) {
    io.sum := Cat(fullAdder.io.cout, fullAdder.io.sum)
  } else if (bitWidth > 1) {
    val upperAdder = Module(new RippleCarryAdderRecursive(bitWidth-1))
    
    // upperAdder.io.a(bitWidth-1, 1)表示upperAdder.io.a的第1个到第bitWidth-1个元素
    upperAdder.io.a := io.a(bitWidth-1, 1)
    upperAdder.io.b := io.b(bitWidth-1, 1)
    upperAdder.io.cin := fullAdder.io.cout

    io.sum := Cat(upperAdder.io.sum, fullAdder.io.sum)
  }
}