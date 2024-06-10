package counter

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
import org.scalatest.matchers.must.Matchers

import scala.util.Random

class testCounter extends AnyFreeSpec with Matchers {

    "Counter should increment correctly" in {
        simulate(new Counter(4)) { dut => 
            val bitWidth = dut.bitWidth
            dut.io.c.poke(0.B)
            dut.io.l.poke(1.B)
            dut.clock.step()
            dut.io.c.poke(1.B)
            for (i <- 0 until (1 << bitWidth)) {
                dut.io.y.expect(i.U)
                dut.clock.step()
            }
            println("Success")
        }
    }

    "Counter should increment correctly2" in {
        simulate(new Counter(4)) { dut => 
            val bitWidth = dut.bitWidth
            val maxVal = 1 << bitWidth
            val rand = new Random
            dut.io.c.poke(1.B)
            //numTests: 20
            for (_ <- 0 until 20) {
                val x = rand.nextInt(maxVal)
                dut.io.l.poke(0.B)
                dut.io.x.poke(x.U)
                dut.clock.step()
                dut.io.l.poke(1.B)
                for (i <- x until maxVal) {
                    dut.io.y.expect(i.U)
                    dut.clock.step()
                }
            }
            println("Success")
        }
    }

}

