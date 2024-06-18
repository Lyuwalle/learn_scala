package arithmetic

import chisel3._
import chisel3.util._

/**
  * NToTwoReducer 分治
  *
  * @param numberOfInputs
  * @param bitWidth
  */
class NToTwoReducer(numberOfInputs: Int, bitWidth: Int) extends Module {
  assert (numberOfInputs >= 2)
  assert (numberOfInputs % 2 == 0)
  val io = IO(new Bundle {
    val inputs = Input(Vec(numberOfInputs, UInt(bitWidth.W)))
    val outputs = Output(Vec(2, UInt(bitWidth.W)))
  })
  if (numberOfInputs == 2) {
    io.outputs := io.inputs
  } else {
    val reduceLower = Module(new NToTwoReducer(numberOfInputs / 2, bitWidth))
    val reduceUpper = Module(new NToTwoReducer(numberOfInputs / 2, bitWidth))
    reduceLower.io.inputs := io.inputs.slice(0, numberOfInputs / 2)
    reduceUpper.io.inputs := io.inputs.slice(numberOfInputs / 2, numberOfInputs)
    val reduceFinal = Module(new FourToTwoReducer(bitWidth))
    reduceFinal.io.inputs := VecInit(reduceLower.io.outputs(0), reduceLower.io.outputs(1), reduceUpper.io.outputs(0), reduceUpper.io.outputs(1))
    io.outputs := reduceFinal.io.outputs
  }
}