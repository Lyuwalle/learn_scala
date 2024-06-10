package counter

import chisel3._
import chisel3.util._

class Counter(val bitWidth: Int) extends Module {
    val io = IO(new Bundle{
        val c = Input(Bool())       // clear
        val l = Input(Bool())       // load
        val x = Input(UInt(bitWidth.W))     // load value
        val y = Output(UInt(bitWidth.W))    // output
    })
    // initialize a register, has a default value 0
    val register = RegInit(0.U(bitWidth.W))
    // wire, define an unsigned int wire with the same width as register
    val incout = Wire(UInt(bitWidth.W))
        
    when (!io.l) {
        register := io.x        //低电平有效，如果l=0，那么register存入io.x的值
    } .elsewhen (!io.c) {
        register := 0.U         //低电平有效，如果c=0，那么register的值清零
    } .otherwise {
        register := incout      //register的值加1
    }
    
    io.y := register
    incout := register + 1.U    //incrementer
}