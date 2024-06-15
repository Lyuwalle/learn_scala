package intro

// define a class with a parameter
class WrapCounter(counterBits: Int) {
    //'-' has a prior priority to '<<'
  val max: Long = 1 << (counterBits - 1)
  // not an immutable value
  var counter = 0L
  def inc(): Long = {
    counter = counter + 1
    if (counter > max){
        counter = 0
    }
    //return value
    counter
  }
  println(s"coutner created with max value $max")
}

object WrapCounter {
  def main(args: Array[String]): Unit = {
    //max = 1 << 8 - 1
    //8 bit counter. 10000000 = 2 ^ 7 = 128
    val counter = new WrapCounter(8)
    // Todo 128会报错
    for (i <- 0 until 127) {
      println(s"counter value is ${counter.inc()}")
    }
  }
}
