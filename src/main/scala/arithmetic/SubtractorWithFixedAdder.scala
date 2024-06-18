package arithmetic

import chisel3._
import chisel3.util._
//不同的包 需要import别的包下的class
import adder.RippleCarryAdder

class SubtractorWithFixedAdder(width: Int) extends ArithmeticUnit(width) {
    val adder = Module(new RippleCarryAdder(width))

    // b = -b
    val invertedB = ~io.b
    adder.io.a := io.a.asUInt
    // -b as unsigned int, 最高位是1
    adder.io.b := invertedB.asUInt
    // cin = 1, 因为 -b = ~b + 1
    adder.io.cin := true.B

    //sum结果再转成 signed int
    io.result := adder.io.sum.asSInt
}