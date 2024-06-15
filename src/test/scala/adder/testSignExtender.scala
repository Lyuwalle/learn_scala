package adder

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
//Matchers is a trait that provides assertion methods to check the results.
import org.scalatest.matchers.must.Matchers

class testSignExtender extends AnyFreeSpec with Matchers {
    "SignExtender should extend correctly" in {
        simulate(new SignExtender(4, 5)) { dut =>
            // 7:0111, 8:1000
            dut.io.in.poke(8.U)
            // 7:00111, 8: 11000 = 24(unsigned int)
            dut.io.out.expect(24.U)
            println("Success")
        }
    }
}