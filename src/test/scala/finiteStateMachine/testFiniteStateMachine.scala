package finiteStateMachine


import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers


class testFiniteStateMachine extends AnyFreeSpec with Matchers {

    "FiniteStateMachine should work correctly on PatternMoore" in {
        simulate(new PatternMoore()) {dut =>
            val inputs = Seq(0, 0, 1, 0, 1, 0, 1)
            val expectedOutputs = Seq(0, 0, 0, 0, 1, 0, 1)

            //This loop iterates over pairs of input and expected output values.
            for ((input, expectedOutput) <- inputs.zip(expectedOutputs)) {
                dut.io.i.poke(input.B)
                dut.clock.step()
                dut.io.o.expect(expectedOutput.B)
            }
            println("Success")
        }
    }


    "FiniteStateMachine should work correctly on PatternMealy" in {
        simulate(new PatternMealy()) {dut =>
            val inputs = Seq(0, 0, 1, 0, 1, 0, 1)
            val expectedOutputs = Seq(0, 0, 0, 0, 1, 0, 1)

            for ((input, expectedOutput) <- inputs.zip(expectedOutputs)) {
                dut.io.i.poke(input.B)
                dut.io.o.expect(expectedOutput.B)
                dut.clock.step()
            }
            println("Success")
        }
    }
}