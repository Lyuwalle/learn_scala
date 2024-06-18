package sequential_circuits

import chisel3._
import chisel3.util._

class MyCounter(maxVal: Int) extends Module {
    val io = IO(new Bundle {
        val en  = Input(Bool())
        val out = Output(UInt())
    })
    // To get the number of bits needed to represent some number n, use log2Ceil(n + 1)
    val count = Reg(UInt(log2Ceil(maxVal+1).W))
    val nextVal = Mux(count < maxVal.U, count + 1.U, 0.U)
    val applyEn = Mux(io.en, nextVal, count)
    count := Mux(reset.asBool, 0.U, applyEn)
    io.out := count

    //using when
    
    // when (io.en) {
    //     when (count < maxVal.U) {
    //         count := count + 1.U
    //     } .otherwise {
    //         count := 0.U
    //     }
    // }
    // io.out := count
}