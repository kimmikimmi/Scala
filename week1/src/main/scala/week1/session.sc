object  session {
  1+2
  def abs(x: Double) = if (x < 0) -x else x
  def sqrt(x : Double) = {


    def sqrtIter(guess: Double): Double =
      if (isGoodEnough(guess)) guess
      else sqrtIter(improve(guess))

    def isGoodEnough(guess: Double) =
      abs(guess * guess - x) / x < 0.001

    def improve(guess: Double) =
      (guess + x / guess) / 2

    sqrtIter(1.0)
  }
  //1.6 - Bolcos and lexical Scope
/*
  arithmetic and boolean expressions
  conditional expression if-else
  functions with recursion
  nesting and lexical scope
  CBV - CBN
 */


  //Blocks in Scala
  /*
  A block is delimited by braces { ...}
    { val x = f(3)
      x * x
    }

  The last element of a block is an expression that defines its value

  This return expression can be preceded by auxiliary definitions

  Blocks are themselves expressions; a block may appear everywhere an
    expression can




   */


  //Semicolons
  /*
  In Scala semicolons at the end of lines are in most cases optional
  You could write
  var x = 1;
  but most people would omit the semicolon.

  On the other hand, if there are more than one statements on a line,
  they need to be separated by semicolons
   */



  sqrt(2)
  sqrt(4)
  sqrt(1e-6)
  sqrt(1e60)

}