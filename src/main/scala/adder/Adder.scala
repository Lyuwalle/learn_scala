package adder

import chisel3._
import chisel3.util._

abstract class Adder(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    // .W表示位宽，bitWidth.W表示把bitWidth转换成位宽，然后传递给Input（需要位宽）
    val a = Input(UInt(bitWidth.W))
    val b = Input(UInt(bitWidth.W))
    // cin是一个Bool类型的输入，0或1
    val cin = Input(Bool())
    val sum = Output(UInt((bitWidth+1).W))
  })
}
