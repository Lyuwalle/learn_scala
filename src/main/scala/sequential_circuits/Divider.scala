package sequential_circuits

import chisel3._
import chisel3.util._

class Divider(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val start = Input(Bool())
    val done = Output(Bool())
    val dividend = Input(UInt(bitWidth.W))
    val divisor = Input(UInt(bitWidth.W))
    val quotient = Output(UInt(bitWidth.W))
    val remainder = Output(UInt(bitWidth.W))
  })

  val remainder = RegInit(0.U(bitWidth.W)) // current remainder
  // 初始化一个寄存器，里面是一个Vec，Vec里面是bitWidth个值为0的值
  // = {dividend[i:0], quotient[N−1:i+1]}, where dividend is the input dividend and quotient is the final output quotient, and i is the current cycle
  val quotient = RegInit(VecInit(Seq.fill(bitWidth)(0.U(1.W))))
  val divisor = RegInit(0.U(bitWidth.W)) // divisor

  //??? // TODO: implement Problem 1.1 here

  val counter = RegInit(0.U(log2Ceil(bitWidth + 1).W))
  val running = RegInit(false.B)

  // 连接输出
  io.done := !running
  io.quotient := quotient.asUInt
  io.remainder := remainder

  when(io.start) {
    remainder := 0.U
    quotient := VecInit(io.dividend.asBools)
    divisor := io.divisor
    counter := (bitWidth - 1).U
    running := true.B
  }.elsewhen(running) {
    //val nextRemainder = Cat(remainder(bitWidth - 2, 0), quotient(counter))
    val nextRemainder = (remainder << 1) + quotient(counter)
    when(nextRemainder < divisor) {
      remainder := nextRemainder
      quotient(counter) := 0.U
    }.otherwise {
      remainder := nextRemainder - divisor
      quotient(counter) := 1.U
    }
    // 更新计数器
    when(counter === 0.U) {
      running := false.B
    }.otherwise {
      counter := counter - 1.U
    }
  }

}