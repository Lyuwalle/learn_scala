package adder

import chisel3._
import chisel3.util._

/**
  * HalfAdder: 输入a,b 都是0/1，输出c,s都是0/1
  * 计算1+1，1+0，0+1，0+0
  *
  */
class HalfAdder extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(1.W))
    val b = Input(UInt(1.W))
    val sum = Output(UInt(1.W))
    val carry = Output(UInt(1.W))
  })


  //相加的和，异或，（1+0=1，1+1=0，0+0=0）
  val local_sum = io.a ^ io.b
  //carry：进位，使用&
  val local_carry = io.a & io.b

  io.sum := local_sum
  io.carry := local_carry
}