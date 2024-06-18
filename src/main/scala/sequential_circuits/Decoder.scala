package sequential_circuits


import chisel3._
import chisel3.util._


abstract class Decoder(noInputs: Int) extends Module {
  val noOutputs = 1 << noInputs
  val io = IO(new Bundle {
    val in = Input(UInt(noInputs.W))
    val out = Output(UInt(noOutputs.W))
  })
}