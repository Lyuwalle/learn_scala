package counter

import chisel3._
import chisel3.util._

class Counter(val bitWidth: Int) extends Module {
    val io = IO(new Bundle{
        val c = Input(Bool())
        val l = Input(Bool())
        val x = Input(UInt(bitWidth.W))
        val y = Output(UInt(bitWidth.W))
    })
    val register = RegInit(0.U(bitWidth.W))
    val incout = Wire(UInt(bitWidth.W))
        
    when (!io.l) {
        register := io.x
    } .elsewhen (!io.c) {
        register := 0.U
    } .otherwise {
        register := incout
    }
    
    io.y := register
    incout := register + 1.U
}