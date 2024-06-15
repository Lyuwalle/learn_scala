package adder

import chisel3._
import chisel3.util._

/**
  * CSA adder, dicide and conquer(分治)
  * depth(CSAn) = 3logn+3
  *
  * @param bitWidth
  */
class ConditionalSumAdder(bitWidth: Int) extends Adder(bitWidth) {
  if (bitWidth == 1) {
    val fullAdder = Module(new FullAdder)
    fullAdder.io.a := io.a(0)
    fullAdder.io.b := io.b(0)
    fullAdder.io.cin := io.cin
    io.sum := Cat(fullAdder.io.cout, fullAdder.io.sum)
  } else {
    val lowerAdder = Module(new ConditionalSumAdder(bitWidth/2))
    lowerAdder.io.a := io.a(bitWidth/2-1, 0)  //accessing subrange from 0 to bitWidth/2-1
    lowerAdder.io.b := io.b(bitWidth/2-1, 0)
    lowerAdder.io.cin := io.cin

    val upperAdderTrue = Module(new ConditionalSumAdder(bitWidth/2))
    upperAdderTrue.io.a := io.a(bitWidth-1, bitWidth/2)  //accessing subrange from bitWidth/2 to bitWidth-1
    upperAdderTrue.io.b := io.b(bitWidth-1, bitWidth/2)
    upperAdderTrue.io.cin := true.B                     //从低位一半传来的进位信号是1

    val upperAdderFalse = Module(new ConditionalSumAdder(bitWidth/2))
    upperAdderFalse.io.a := io.a(bitWidth-1, bitWidth/2) //accessing subrange from bitWidth/2 to bitWidth-1
    upperAdderFalse.io.b := io.b(bitWidth-1, bitWidth/2)
    upperAdderFalse.io.cin := false.B                   //从低位一半传来的进位信号是0

    val lowerAdderCarry = lowerAdder.io.sum(bitWidth/2)
    //lowerAdderCarry是1，则选择upperAdderTrue.io.sum，否则选择upperAdderFalse.io.sum
    val upperSum = Mux(lowerAdderCarry, upperAdderTrue.io.sum, upperAdderFalse.io.sum)
    io.sum := Cat(upperSum, lowerAdder.io.sum(bitWidth/2-1, 0))
  }
}