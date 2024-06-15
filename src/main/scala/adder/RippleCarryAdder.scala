package adder

import chisel3._
/**
  * 实现一个RippleCarryAdder，计算n位的a+b, 需要n个FullAdder
  *
  * @param bitWidth
  */
class RippleCarryAdder(bitWidth: Int) extends Adder(bitWidth) {
    // 创建一个FullAdder数组，长度是bitWidth
    val adders = Seq.fill(bitWidth)(Module(new FullAdder))  //instantiate bitWidth many full adders

    adders(0).io.a := io.a(0)   //connect the first full adder to the inputs
    adders(0).io.b := io.b(0)
    adders(0).io.cin := io.cin  //第一个adder的cin是输入的cin

    for (i <- 1 until bitWidth) { //connect the rest of the full adders to ...
        adders(i).io.a := io.a(i)   //... the inputs
        adders(i).io.b := io.b(i)   //... the inputs
        // 这个的fulladder的cin是前一个adder的carry
        adders(i).io.cin := adders(i - 1).io.cout //... and the carry out of the previous full adder
    }

    // 将fulladder的sum连接起来
    // VecInit是 Chisel 中的一个函数，用于创建一个 Vec 类型的硬件向量
    // adders.map(_.io.sum)，这是一个映射操作 (map)，它对 adders 序列中的每个元素（即 FullAdder 实例）执行 _.io.sum 操作
    //seq.map(fun) applies function fun to every element of the sequence 
    //seq.Seq(1,2,3).map(_ + 42) yields Seq(43,44,45).
    // .asUInt表示把Vec类型转成UInt类型
    //compose the outputs of all full adders and connect them to the output of the module
    io.sum := VecInit(adders.map(_.io.sum) :+ adders.last.io.cout).asUInt 
    // io.sum := Cat(adders.last.io.cout, VecInit(adders.map(_.io.sum)).asUInt)   //equivalent，这句话更好理解
    //   io.sum := Cat(adders.last.io.cout, adders.map(_.io.sum).reverse.reduce(Cat(_, _)))   /equivalent
}