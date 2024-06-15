package adder

import chisel3._
import chisel3.util._

/**
  * 并行前缀计算
  *
  * @param dataGen
  * @param width
  * @param associativeOperation
  */
class ParallelPrefixSum[T <: Data](dataGen: => T, width: Int, associativeOperation: (T, T) => T) extends Module {
  val io = IO(new Bundle {
    val input = Input(Vec(width, dataGen))
    val output = Output(Vec(width, dataGen))
  })

  if (width == 1) {
    io.output := io.input
  } else {
    val subParallelPrefixSum = Module(new ParallelPrefixSum(dataGen, width / 2, associativeOperation))
    subParallelPrefixSum.io.input := VecInit((0 until width/2).map(i => associativeOperation(io.input(i*2+1), io.input(i*2))))
    val subOutput = subParallelPrefixSum.io.output
    val innerOutput = (0 until (width/2 - 1)).flatMap(i => Seq(subOutput(i), associativeOperation(io.input((i+1)*2), subOutput(i))))
    val subOutputLast = subOutput(width/2-1)
    if (width % 2 == 0) {
      io.output := VecInit(Seq(io.input(0)) ++ innerOutput ++ Seq(subOutputLast))
    } else {  //width is odd
      val extraOutput = associativeOperation(io.input(width - 1), subOutputLast)
      io.output := VecInit(Seq(io.input(0)) ++ innerOutput ++ Seq(subOutputLast, extraOutput))
    }
  }
}