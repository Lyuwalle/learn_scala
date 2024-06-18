package sequential_circuits

import chisel3._
import chisel3.util._

class RegLand extends Module {
    val io = IO(new Bundle {
        val in  = Input(Bool())
        val en  = Input(Bool())
        val out = Output(Bool())
    })
    val r = Reg(Bool())
//     val r = RegInit(0.B)
    r := io.in
    io.out := r
//     io.out := RegNext(io.in, 0.B)
//     io.out := RegEnable(io.in, 0.B, io.en)
}