package sequential_circuits

import chisel3._
import chisel3.util._

class RecursiveDecoder(noInputs: Int) extends Decoder(noInputs) {
  if (noInputs == 1) {
    io.out := Cat(io.in, ~io.in)
  } else {
    val internalDecoder = Module(new RecursiveDecoder(noInputs - 1))
    internalDecoder.io.in := io.in(noInputs - 2, 0)
    
    val lowerOut = internalDecoder.io.out & Fill(noOutputs/2, ~io.in(noInputs - 1))
    val upperOut = internalDecoder.io.out & Fill(noOutputs/2, io.in(noInputs - 1))
    io.out := Cat(upperOut, lowerOut) 
  }
}