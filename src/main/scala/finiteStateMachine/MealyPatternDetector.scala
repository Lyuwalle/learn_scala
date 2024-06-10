package finiteStateMachine

import chisel3._
import chisel3.util._

// assignment 6.5
// input and output
class MealyPatternDetector() extends Module {
    val io = IO(new Bundle {
        val i = Input(Bool())
        val o = Output(Bool())
    })
}