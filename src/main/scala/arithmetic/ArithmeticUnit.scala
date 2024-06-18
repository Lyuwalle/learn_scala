package arithmetic

import chisel3._
import chisel3.util._

/**
  * the arithmetic operation of a and b
  *
  * @param width
  */
abstract class ArithmeticUnit(width: Int) extends Module {
    val io = IO(new Bundle {
        val a = Input(SInt(width.W))
        val b = Input(SInt(width.W))
        val result = Output(SInt((width+1).W))
    })
}