package arithmetic

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
//Matchers is a trait that provides assertion methods to check the results.
import org.scalatest.matchers.must.Matchers

class testCarrySaveAdder extends AnyFreeSpec with Matchers {
     "should extend correctly" in {
        simulate(new CarrySaveAdder(4)) { dut =>

            dut.io.inputs(0).poke("b0110".U)
            dut.io.inputs(1).poke("b1010".U)
            dut.io.inputs(2).poke("b1100".U)

            //部分和
            dut.io.sum.expect("b0000".U)
            //进位，为什么不是1110
            dut.io.carry.expect("b1110".U)
        }
     }
}