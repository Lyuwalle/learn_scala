package adder

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
//Matchers is a trait that provides assertion methods to check the results.
import org.scalatest.matchers.must.Matchers

class testRippleCarryAdder extends AnyFreeSpec with Matchers {
    "RippleCarryAdder should calculate correct result" in {
        simulate(new RippleCarryAdder(4)) { dut =>
            dut.io.a.poke(8.U)
            dut.io.b.poke(8.U)
            dut.io.cin.poke(0.B)
            //8+8=16
            dut.io.sum.expect(16.U)
        }
    }
}