package adder

import chisel3._
import chisel3.util._

/**
  * SignExtender， in=11，out=11111（wout=5）
  *
  * @param win  输入信号的位宽
  * @param wout 输出信号的位宽
  */
class SignExtender(win: Int, wout: Int) extends Module {
    val io = IO(new Bundle {
        val in = Input(UInt(win.W))
        val out = Output(UInt(wout.W))
    })
    assert(win > 0)
    assert(win < wout)
    //获取输入信号的最高位（符号位）
    val signBit = io.in(win-1)
    // Fill(n, x) returns a UInt with n copies of x
    // wout-win表示需要extend的位数
    val extension = Fill(wout-win, signBit)
    //extension和in拼接，即extend
    io.out := Cat(extension, io.in)
}

// object SignExtender {
//     def main(args: Array[String]): Unit = {
//         val extender = new SignExtender(win = 3, wout = 5)
//         extender.io.in := 7.U
//         //打印7的二进制5位
//         println(extender.io.out.toString)
//     }
// }