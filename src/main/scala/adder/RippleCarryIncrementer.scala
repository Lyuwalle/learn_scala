package adder

import chisel3._
import chisel3.util._

abstract class Incrementer(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(bitWidth.W))
    val c = Input(Bool())
    val out = Output(UInt((bitWidth+1).W))
  })
}

/**
  * Incrementer， 把RippleCarryAdder的FA换成HA
  * 用于把n位的数据加一
  *
  * @param bitWidth
  */
class RippleCarryIncrementer(bitWidth: Int) extends Incrementer(bitWidth) {
    // 创建bitWidth长度的HalfAdder
  val adders = Seq.fill(bitWidth)(Module(new HalfAdder))

  adders(0).io.a := io.a(0)
  adders(0).io.b := io.c

  for (i <- 1 until bitWidth) {
    adders(i).io.a := io.a(i)
    adders(i).io.b := adders(i - 1).io.carry
  }

  io.out := Cat(adders.last.io.carry, VecInit(adders.map(_.io.sum)).asUInt)
}