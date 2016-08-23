package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {

    if (c == r || c == 0) 1 else pascal(c - 1, r - 1) + pascal(c, r - 1)
  }


  /**
   * Exercise 2
   */


  def balance(chars: List[Char]): Boolean = {
    val open = 0;

    def inner_balance(c : List[Char], cnt : Int): Boolean = {
      if (c.isEmpty) {
        if (open == 0) return true else return false;
      }
      var cnt = open;
      if (c.head == ')') {
        cnt = open - 1

      } else if (c.head == '(') {
        cnt = open + 1

      }
      inner_balance(c.tail, cnt)
    }

    inner_balance(chars, open);

  }

  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = {
    if(money == 0)
      1
    else if(money > 0 && !coins.isEmpty)
      countChange(money - coins.head, coins) + countChange(money, coins.tail)
    else
      0
  }
}
