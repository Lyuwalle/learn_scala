package adder

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
//Matchers is a trait that provides assertion methods to check the results.
import org.scalatest.matchers.must.Matchers

class testMultiplexer extends AnyFreeSpec with Matchers {
    "should calculate correct result" in {
        simulate(new Multiplexer(4)) { dut =>

            dut.io.select.poke(1.B)
            dut.io.input0.poke(8.U)
            dut.io.input1.poke(7.U)
            // select是1，选择input1信号
            dut.io.out.expect(7.U)
        }
    }
}