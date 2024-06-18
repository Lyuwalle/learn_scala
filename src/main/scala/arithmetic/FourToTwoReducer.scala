package arithmetic

import chisel3._
import chisel3.util._

class FourToTwoReducer(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    val inputs = Input(Vec(4, UInt(bitWidth.W)))
    val outputs = Output(Vec(2, UInt(bitWidth.W)))
  })
  val adder1 = Module(new CarrySaveAdder(bitWidth))
  val adder2 = Module(new CarrySaveAdder(bitWidth))
  adder1.io.inputs := io.inputs.slice(0, 3) //slice(0, 3) extracts elements 0, 1, 2, but *not* 3
  adder2.io.inputs := VecInit(io.inputs(3), adder1.io.sum, adder1.io.carry)
  io.outputs := VecInit(adder2.io.sum, adder2.io.carry)
}