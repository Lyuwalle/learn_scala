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
            dut.io.c.poke(0.B)          // c=0, clear, register = 0
            dut.io.l.poke(1.B)          // l=1
            dut.clock.step()            // 使时钟前进一个周期
            dut.io.c.poke(1.B)          // c=1,l=1, 此时正常计数
            for (i <- 0 until (1 << bitWidth)) {    //0-2^4-1
                dut.io.y.expect(i.U)

                println(dut.io.y)

                dut.clock.step()        //every step move, the counter will add 1
            }
            println("Success")
        }
    }

    "Counter should increment correctly2" in {
        simulate(new Counter(4)) { dut => 
            val bitWidth = dut.bitWidth
            val maxVal = 1 << bitWidth // 2^4
            val rand = new Random
            dut.io.c.poke(1.B)
            //numTests: 20
            for (_ <- 0 until 20) {
                val x = rand.nextInt(maxVal)
                println(s"maxVal = $x")
                dut.io.l.poke(0.B)
                dut.io.x.poke(x.U)
                dut.clock.step()        //每次都load random number 到register
                dut.io.l.poke(1.B)      //取消load, 然后继续计数
                for (i <- x until maxVal) {
                    println(s"i = $i")
                    dut.io.y.expect(i.U)
                    dut.clock.step()
                }
            }
            println("Success")
        }
    }

}

