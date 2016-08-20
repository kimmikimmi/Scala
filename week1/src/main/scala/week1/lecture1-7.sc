// Lecture 1.7 - Tail Recursion!!


/*

This can be formalized as a rewriting of the program itself

  def f(x1, ..., xn) = B; ...f(v1,...vn)

  -> def f(x1,... xn) = B; ... [v1/x1, ... vn/xn]B

   Here [v1/x1, ... vn/xn] means :
   The expression B in which all occurrences of xi have been replaced
   by vi.
   [v1/x1, .... ,vn/xn] is called a substitution

 */


/*

example

*/

def gcd(a : Int, b : Int): Int =
  if (b == 0) a else gcd(b, a%b)

gcd(14, 21)


def factorial(n : Int) : Int =
  if(n == 0) 1 else  factorial(n-1) * n

factorial(4)
factorial(3)
factorial(5)
/*/
The difference between gcd and factorial func...
  each step we put a more statement.

  -> Tail recursive functions are iterative processes.
  Tail calls.

  Be careful of stack overflow exception!
 */


// Design Tail recursion version of the function..
def tail_factorial(n : Int) : Int = {
  def loop(acc: Int, n: Int) : Int =
    if(n==0) acc
    else loop(acc*n, n-1)
  loop(1, n)
}

tail_factorial(5)
factorial(5)
