package adder

import chisel3._
import chisel3.util._

/**
  * Multiplexer用于在两个输入中信号（都是n-bit width）中选择一个
  *
  * @param bitWidth 位宽
  */
class Multiplexer(bitWidth: Int) extends Module {

    val io = IO(new Bundle {
        val select = Input(UInt(1.W))       //select信号，1或0
        val input0 = Input(UInt(bitWidth.W))
        val input1 = Input(UInt(bitWidth.W))
        val out = Output(UInt(bitWidth.W))
    })

    //创建一个长度为bitWidth的Wire数组（wire用于连接数字逻辑电路的输入和输出）
    val outputs = Seq.fill(bitWidth)(Wire(UInt(1.W)))
    val posSelect = io.select
    val negSelect = !io.select
    // io.select是1，则选择input1，否则选择input0
    for (i <- 0 until bitWidth) {
        outputs(i) := posSelect & io.input1(i) | negSelect & io.input0(i)
    }
  
    io.out := VecInit(outputs).asUInt
    //   io.out := Mux(io.select.asBool, io.input1, io.input0) //using the "built-in" multiplexer
}