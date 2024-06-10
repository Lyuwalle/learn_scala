package finiteStateMachine

import chisel3._
import chisel3.util._

// input and output
class MoorePatternDetector() extends Module {
    val io = IO(new Bundle {
        val i = Input(Bool())
        val o = Output(Bool())
    })
}