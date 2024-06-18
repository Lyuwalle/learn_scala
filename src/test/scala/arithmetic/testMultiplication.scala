package arithmetic

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
//Matchers is a trait that provides assertion methods to check the results.
import org.scalatest.matchers.must.Matchers

class testMultiplication extends AnyFreeSpec with Matchers {
     "should correctly" in { 
        simulate(new Multiplication(4)) { dut =>
            dut.io.a.poke("b1111".U)
            dut.io.b.poke("b1110".U)
            //15*14 = 210
            dut.io.product.expect(210.U)
        }
     }
    }