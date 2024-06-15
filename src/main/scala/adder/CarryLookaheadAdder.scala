package adder

import chisel3._
import chisel3.util._

/**
  * 进位预测加法器，linear cost and logarithmic depth
  *
  * @param bitWidth
  */
class CarryLookaheadAdder(bitWidth: Int) extends Adder(bitWidth) {

  val associativeOperation = (ax: Vec[Bool], bx: Vec[Bool]) => {
    val g = ax(0)
    val p = ax(1)
    val g1 = bx(0)
    val p1 = bx(1)
    VecInit(g | (p & g1), p & p1)
  }

  val aXORb = io.a ^ io.b
  val gp = VecInit((0 until bitWidth).map(i => VecInit(Seq(io.a(i) & io.b(i), aXORb(i)))))
  val parallelPrefixSum = Module(new ParallelPrefixSum(Vec(2, Bool()), bitWidth, associativeOperation))
  parallelPrefixSum.io.input := gp
  val prefixSum = parallelPrefixSum.io.output

  def cj(j: Int): Bool = {
    if (j == -1) {
      return io.cin
    }
    val gj0 = prefixSum(j)(0)
    val pj0 = prefixSum(j)(1)
    gj0 | (pj0 & io.cin)
  }

  io.sum := VecInit((0 until bitWidth).map(i => cj(i-1) ^ aXORb(i)) :+ cj(bitWidth-1)).asUInt
}