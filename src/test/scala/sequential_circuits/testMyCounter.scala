package sequential_circuits

import chisel3._
import chiseltest._
import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import scala.reflect.ClassTag
import chisel3.experimental.hierarchy.{
  Definition,
  Instance,
  instantiable,
  public
}

import chisel3.reflect.DataMirror
import scala.util.Random
import chisel3.experimental.BundleLiterals
import scala.language.implicitConversions

class testMyCounter extends AnyFlatSpec with ChiselScalatestTester with Matchers {

    "MyCounter" should "correctly counter" in {
        test(new MyCounter(3)) { c =>
        c.io.en.poke(1.B)
        c.io.out.expect(0.U)
        //MyCounter的clock是implicit的
        //step之后才+1
        c.clock.step()

        c.io.en.poke(1.B)
        c.io.out.expect(1.U)
        c.clock.step()

        c.io.en.poke(1.B)
        c.io.out.expect(2.U)
        c.clock.step()

        c.io.en.poke(0.B)
        c.io.out.expect(3.U)
        c.clock.step()

        c.io.en.poke(1.B)
        //count=3
        c.io.out.expect(3.U)
        c.clock.step()

        c.io.en.poke(1.B)
        c.io.out.expect(0.U)
        c.clock.step()
        println("Success!")
        }
  }
}
