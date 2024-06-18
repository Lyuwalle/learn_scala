package sequential_circuits

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
//Matchers is a trait that provides assertion methods to check the results.
import org.scalatest.matchers.must.Matchers

class testDivider extends AnyFreeSpec with Matchers {
     "should correctly" in { 
        simulate(new Divider(8)) { dut =>
            // 测试用例 1: 10 / 2
      dut.io.start.poke(true.B)
      dut.io.dividend.poke(10.U)
      dut.io.divisor.poke(2.U)
      dut.clock.step(1)
      dut.io.start.poke(false.B)
      dut.clock.step(10)
      dut.io.done.expect(true.B)
      dut.io.quotient.expect(5.U)
      dut.io.remainder.expect(0.U)

      // 测试用例 2: 20 / 3
      dut.io.start.poke(true.B)
      dut.io.dividend.poke(20.U)
      dut.io.divisor.poke(3.U)
      dut.clock.step(1)
      dut.io.start.poke(false.B)
      dut.clock.step(10)
      dut.io.done.expect(true.B)
      dut.io.quotient.expect(6.U)
      dut.io.remainder.expect(2.U)

      // 测试用例 3: 15 / 4
      dut.io.start.poke(true.B)
      dut.io.dividend.poke(15.U)
      dut.io.divisor.poke(4.U)
      dut.clock.step(1)
      dut.io.start.poke(false.B)
      dut.clock.step(10)
      dut.io.done.expect(true.B)
      dut.io.quotient.expect(3.U)
      dut.io.remainder.expect(3.U)


        
        }
     }
    }