package sequential_circuits

import chisel3._
import scala.reflect.ClassTag
import chisel3.util._
import chisel3.experimental.hierarchy.{Definition, Instance, instantiable, public}

abstract class ComputationalUnit(width: Int) extends Module {
    val io = IO(new Bundle {
        val a = Input(UInt(width.W))
        val b = Input(UInt(width.W))
        val c = Output(UInt(width.W))
    })  
}

class ParallelUnit(vectorSize: Int, arraySize: Int, unitWidth: Int, comp: (Int) => ComputationalUnit) extends Module {
  require(vectorSize % arraySize == 0)
  
  val io = IO(new Bundle {
    val a = Input(Vec(vectorSize, UInt(unitWidth.W)))
    val b = Input(Vec(vectorSize, UInt(unitWidth.W)))
    val start = Input(Bool())
    val done = Output(Bool())
    val c = Output(Vec(vectorSize, UInt((unitWidth).W)))
  })

  val units = Seq.fill(arraySize)(Module(comp(unitWidth)))

//   io.done := false.B

//   // 使用寄存器保存计算结果
//   val results = Reg(Vec(vectorSize, UInt(unitWidth.W)))
  

//     // 初始化results
//     when(io.start){
//         for (i <- 0 until vectorSize) {
//             results(i) := 0.U
//         }
//     }
    
//     // Create state machine to control the start and done signals
//     val processing = RegInit(false.B)
//     val segment = vectorSize / arraySize
//     val cycleCounter = RegInit(0.U(log2Ceil(segment).W))

//     // Connect the units
//     for (i <- 0 until arraySize) {
//         printf(p"cycleCounter: ${cycleCounter}, i: ${i}\n")
//         units(i).io.a := io.a(cycleCounter * arraySize.U + i.U)
//         units(i).io.b := io.b(cycleCounter * arraySize.U + i.U)

//         results(cycleCounter * arraySize.U + i.U) := units(i).io.c

//         // 打印units(i).io.a的值 (10进制)
//         printf(p"units($i).io.a: ${units(i).io.a} ")
//         printf(p"units($i).io.b: ${units(i).io.b} ")
//         printf(p"units($i).io.c: ${units(i).io.c}\n")

//         //io.c(cycleCounter * arraySize.U + i.U) := units(i).io.c
//     }
//     io.c := results
//     cycleCounter := cycleCounter + 1.U

//     when(io.start && !processing) {
//         processing := true.B
//     }

//     when(processing) {
//         when(cycleCounter < (segment.U - 1.U)) {
//             cycleCounter := cycleCounter + 1.U
//         }.otherwise {
//             processing := false.B
//             io.done := true.B
//         }
//     }

  io.done := false.B
  val processing = RegInit(false.B)
  val cycleCounter = RegInit(0.U(log2Ceil(vectorSize / arraySize).W))

  // 使用寄存器保存计算结果
  val results = Reg(Vec(vectorSize, UInt(unitWidth.W)))
    when(io.start) {
        processing := true.B
        for (i <- 0 until vectorSize) {
             results(i) := 0.U
         }
    } .elsewhen(processing && cycleCounter === (vectorSize / arraySize - 1).U) {
        processing := false.B
        cycleCounter := 0.U
    } .otherwise {
        cycleCounter := cycleCounter + 1.U
    }

    // Connect inputs to units
    for (i <- 0 until arraySize) {
        printf(p"cycleCounter: ${cycleCounter}, i: ${i}\n")
        units(i).io.a := io.a(cycleCounter * arraySize.U + i.U)
        units(i).io.b := io.b(cycleCounter * arraySize.U + i.U)

        results(cycleCounter * arraySize.U + i.U) := units(i).io.c

        // 打印units(i).io.a的值 (10进制)
        printf(p"units($i).io.a: ${units(i).io.a} ")
        printf(p"units($i).io.b: ${units(i).io.b} ")
        printf(p"units($i).io.c: ${units(i).io.c}\n")

        //io.c(cycleCounter * arraySize.U + i.U) := units(i).io.c
    }
    io.c := results

    // Generate done signal
    io.done := !processing && (cycleCounter === ((vectorSize / arraySize) - 1).U)
}