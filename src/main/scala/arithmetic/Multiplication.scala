package arithmetic


import chisel3._
import chisel3.util._
import adder.CarryLookaheadAdder

/**
  * 乘法运算
  *
  * @param bitWidth
  */
class Multiplication(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val a = Input(UInt(bitWidth.W))
    val b = Input(UInt(bitWidth.W))
    val product = Output(UInt((bitWidth*2).W))
  })

  //部分积放到一个数组里面
  val partialProducts = VecInit(
    // 部分积，每循环一次都要乘2（左移一位）
    (0 until bitWidth).map(i => io.a(i) * (io.b << i))
  )

  //部分积数组放在reducer的inputs里面
  val reducer = Module(new NToTwoReducer(bitWidth, bitWidth * 2))
  reducer.io.inputs := partialProducts

  // CarryLookaheadAdder的输入是reducer的输出（两个加数，cin是0）
  val adder = Module(new CarryLookaheadAdder(bitWidth * 2))
  //sum
  adder.io.a := reducer.io.outputs(0)
  //carry
  adder.io.b := reducer.io.outputs(1)
  adder.io.cin := 0.U
  io.product := adder.io.sum
}