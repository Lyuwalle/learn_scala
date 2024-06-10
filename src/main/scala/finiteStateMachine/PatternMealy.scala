package finiteStateMachine

import chisel3._
import chisel3.util._

class PatternMealy() extends MealyPatternDetector {
    val state = RegInit(0.U(2.W)) 
    // 00 0.U - last 0 or none
    // 01 1.U - last 1
    // 10 2.U - last 10
    
    io.o := 0.B
    switch(state) {
        is(0.U) {
            when (io.i) {
                state := 1.U
            }
        }
        is(1.U) {
            when (!io.i) {
                state := 2.U
            }
        }
        is(2.U) {
            when (io.i) {
                state := 1.U        //101
                io.o := 1.B
            } .otherwise {
                state := 0.U        //go to 0 again
                io.o := 0.B
            }
        }
    }
}

// Here is an alternative solution. This implementation is the one from the sample solution,
// though, it does not look very readable.
 
// class PatternMealyAlt() extends MealyPatternDetector { 
//     val reg1 = RegInit(0.B)
//     val reg2 = RegInit(0.B)
//     val new_reg1 = Wire(Bool())
//     val new_reg2 = Wire(Bool())
    
//     new_reg1 := io.i
//     new_reg2 := reg1
//     io.o := !reg1 & reg2 & io.i
//     reg1 := new_reg1
//     reg2 := new_reg2
// }