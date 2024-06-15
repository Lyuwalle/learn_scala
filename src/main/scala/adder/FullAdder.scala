package adder

import chisel3._
import chisel3.util._

/**
  * 全加器：场景，a+b+cin(进位)，三个位宽都是1，得到sum，cout
  *
  */
class FullAdder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val cin = Input(UInt(1.W))
    val sum = Output(UInt(1.W))
    val cout = Output(UInt(1.W))
  })

  //一个 full adder 由两个half adder 和 一个or gate 组成
  val halfAdder1 = Module(new HalfAdder)       //need to wrap instances in `Module'
  val halfAdder2 = Module(new HalfAdder)       //need to wrap instances in `Module'
  halfAdder1.io.a := io.a
  halfAdder1.io.b := io.b
  halfAdder2.io.a := halfAdder1.io.sum
  halfAdder2.io.b := io.cin

  io.sum := halfAdder2.io.sum
  io.cout := halfAdder1.io.carry | halfAdder2.io.carry

  
}