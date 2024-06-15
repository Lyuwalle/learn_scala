package intro

import chisel3._
import chisel3.util._ 

class MyXOR extends Module {
    val io = IO(new Bundle {
        val a = Input(Bool())
        val b = Input(Bool())
        val c = Output(Bool())
    })
    // := 是chisel中硬件信号和寄存器赋值的操作符， 而=是scala中的赋值操作符，用于初始化scala中的变量
    io.c := io.a ^ io.b
}

// object MyXOR {
//     def main(args: Array[String]): Unit = {
//         printVerilog(new MyXOR)
//         visualize(() => new MyXOR)
//     }
// }
