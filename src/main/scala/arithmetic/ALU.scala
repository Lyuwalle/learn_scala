package arithmetic

import chisel3._
import chisel3.util._
import chisel3.experimental.hierarchy.{
  Definition,
  Instance,
  instantiable,
  public
}

class ALU(width: Int, adderDef: Definition[Adder]) extends Module {
    val io = IO(new Bundle {
        val a = Input(Bits(width.W))
        val b = Input(Bits(width.W))
        val c = Input(Bool())
        val s = Input(Bits(3.W))
        val result = Output(Bits((width+1).W))
    })
    val adder = Instance(adderDef)

    //todo 下面四行adder.io报错
    // adder.io.a := io.a.asUInt ^ Fill(width, !io.s(1))
    // adder.io.b := io.b.asUInt ^ Fill(width, !io.s(0))
    // adder.io.cin := (io.s(1) ^ io.s(0)) || io.c

    // val arithmeticOut = Mux(io.s(0) || io.s(1), adder.io.sum, Fill(width+1, 0.U))
    val logicOut = Mux(io.s(0), Mux(io.s(1), Fill(width+1, 1.B), Cat(0.B, io.a | io.b)), Cat(0.B, Mux(io.s(1), io.a & io.b, io.a ^ io.b)))

    //io.result := Mux(io.s(2), logicOut, arithmeticOut)
}