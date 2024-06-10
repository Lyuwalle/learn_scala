package finiteStateMachine

import chisel3._
import chisel3.util._


class PatternMoore() extends MoorePatternDetector {
    val state = RegInit(0.U(2.W))       //0 is the initial value this register will hold
                                        //This denotes that the value is an unsigned integer literal in Chisel
                                        //2.W means that the width of the register is 2 bits
    // 00 0.U - last 0 or none
    // 01 1.U - last 1
    // 10 2.U - last 10
    // 11 3.U - last 101

    io.o := 0.B                         //output is initially 0(boolean type)
    switch(state) {
        is(0.U) {
            when(io.i) {
                state := 1.U            //0->1
            }
        }
        is(1.U) {
            when (!io.i) {
                state := 2.U            //1->10
            }
        }
        is(2.U) {
            when (io.i) {
                state := 3.U            //10->101
            } .otherwise {
                state := 0.U            //10->0
            }
        }
        is(3.U) {
            io.o := 1.B                 //101, output 1
            when (io.i) {               //101->01
                state := 1.U
            } .otherwise {              //101->10
                state := 2.U
            }
        }
    }
}

// Here is an alternative solution. This implementation is the one from the sample solution,
// though, it does not look very readable.
 
// class PatternMooreAlt() extends MoorePatternDetector { 
//     val reg1 = RegInit(0.B)
//     val reg2 = RegInit(0.B)
//     val reg3 = RegInit(0.B)
//     val new_reg1 = Wire(Bool())
//     val new_reg2 = Wire(Bool())
//     val new_reg3 = Wire(Bool())
    
//     new_reg1 := io.i
//     new_reg2 := reg1
//     new_reg3 := reg2
//     io.o := reg1 & !reg2 & reg3
//     reg1 := new_reg1
//     reg2 := new_reg2
//     reg3 := new_reg3
// }