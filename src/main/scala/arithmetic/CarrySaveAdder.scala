package arithmetic

import chisel3._
import chisel3.util._
import adder.FullAdder

/**
 * 并不直接计算两个数的相加
  * 三个输入，不明白第三个输入的作用是什么以及怎么对第三个输入赋值
  * n个全加器
  *
  * @param bitWidth
  */
class CarrySaveAdder(bitWidth: Int) extends Module {
  val io = IO(new Bundle {
    //inputs 是一个输入向量（Vec），包含 3 个 UInt 类型的信号，每个信号的宽度为 bitWidth 位
    // 第三个向量是啥？
    val inputs = Input(Vec(3, UInt(bitWidth.W)))  
    val sum = Output(UInt(bitWidth.W))
    val carry = Output(UInt(bitWidth.W))
  })
  // bitWidth个FullAdder组成的sequence
  val adders = Seq.fill(bitWidth)(Module(new FullAdder))

  //zipWithIndex 是 Scala 集合操作方法，它将每个元素与其索引配对
  adders.zipWithIndex.foreach { case (adder, i) =>
    adder.io.a := io.inputs(0)(i)
    adder.io.b := io.inputs(1)(i)
    //
    adder.io.cin := io.inputs(2)(i)
  }

  io.sum := VecInit(adders.map(_.io.sum)).asUInt
  // ## 0.U 是位连接操作，将 cout 连接上一个零位，目的是确保进位信号的总宽度比 bitWidth 宽一位
  // pad(bitWidth) 将信号扩展到 bitWidth 位，必要时在高位填充零
  // 如果carry是1110，那么把carry变成1100，为什么这么做？
  io.carry := (VecInit(adders.map(_.io.cout)).asUInt ## 0.U).pad(bitWidth)
  //io.carry := VecInit(adders.map(_.io.cout)).asUInt
}