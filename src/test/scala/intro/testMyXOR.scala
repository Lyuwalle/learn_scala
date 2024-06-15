package intro

//FreeSpec provides a free-form, nested style for writing tests
import chisel3._
import chisel3.experimental.BundleLiterals._
import chisel3.simulator.EphemeralSimulator._
import org.scalatest.freespec.AnyFreeSpec
//Matchers is a trait that provides assertion methods to check the results.
import org.scalatest.matchers.must.Matchers

class testMyXOR extends AnyFreeSpec with Matchers {

  "MyXOR should work correctly" in {
    simulate(new MyXOR) { x =>
        x.io.a.poke(0.B)
        x.io.b.poke(0.B)
        x.io.c.expect(0.B)  // 0 ^ 0

        x.io.a.poke(0.B)
        x.io.b.poke(1.B)
        x.io.c.expect(1.B)  // 0 ^ 1

        x.io.a.poke(1.B)
        x.io.b.poke(0.B)
        x.io.c.expect(1.B)  // 1 ^ 0

        x.io.a.poke(1.B)
        x.io.b.poke(1.B)
        x.io.c.expect(0.B)  // 1 ^ 1
        }
    }
}