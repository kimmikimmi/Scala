#Higher-Order Functions (2nd week)
---
##Essential building block of FP

	Functional languages treat functions as first-class values.
	
	This means that, like any other value, a function can be passed as a 
	parameter and returned as a result.
	
	This provides a flexible way to compose programs.
	
	Functions that take other functions as parameters or that return 
	functions as results are called higher order function
	

	Take the sum of the integers b/w a and b
	def sumInts(a: Int, b: Int): Int = 
		if(a > b) 0 else a + sumInts(a+1, b)
		
	Take the sum of the cubes of all integers b/w a and b
	def cube(x: Int): Int = x * x * x
	
	def sumCubes(a : Int, b: Int): Int = 
		if(a > b) 0 else cube(a) + sumCubes(a+1, b)
		
		
###We can then write
	def sumInts(a : Int, b: Int) = sum(id, a, b)
	def subCubes(a : Int, b: Int) = sum(cube, a, b)
	def sumFactorials(a : Int, b : Int) = sum(fact, a, b)
	
###where
	def id(x : Int) = x
	def cube(x : Int): Int = x * x * x
	def factorial(x : Int): Int = if(x == 0) 1 else fact(x - 1)
	
##Function Types
    The type A => is the type of a function that takes an argument of 
    type A and returns a result of type B.
    
    So, Int => Int is the type of functions that map integers to integers
    
##anonymous function
###anonymous function Syntax
	(x: Int) => x * x * x
	Here, (x: Int) is the parameter of the function, and x * x * x is it's body
	-> the type of the parameter can be omitted if it can be inferred 
	by the compiler from the context.
	
	If there are several parameters, they are separated by commas:
		(x: Int, y: Int) => x + y
		
###Summation with Anonymous Functions
    Using anonymous functions, we can write sums in a shorter way
    
    def sumInts(a: Int, b: Int) = sum(x => x, a, b)
    def sumCubes(a : Int, b: Int) = sum(x => x * x * x, a, b)
    
    
write a tail-recursive version of sum
	
	def sum(f: Int=> Int)(a: Int, b: Int): Int = {
		def loop(a: Int, acc: Int): Int = {
			if(???) ???
			else loop(???, ???)
		}
		loop(???, ???)
	}
	
	def sum(f: Int => Int)(a: Int, b: Int): Int = {
		def loop(a: Int, acc: Int): Int = {
			if(a > b) acc
			else loop(a+1, f(a) + acc)
		}
		loop(a, 0)
		sum(x => x*x, 3, 5)
	}

---	
#Currying
##**why we use it?**
>1. Basically, you can use currying if you want to plug a function together at different places

>2. Arguments passed one parameter section can be used to infer the types of a formal parameter in a subsequent parameter section

	인자가 여러개 있는 함수를 하나의 인자를 가진 함수의 체인 형태로 만들어 주는 것.
	f:(X*Y) -> Z 형식의 함수가 있을 때 이것을 Currying 하면 curry(f): X -> (Y -> Z)형태로 만듬
	예) f(x,y) = y/x의 원본 함수가 있다고 했을 때 f(2,3)의 값을 계산하려고 한다면
	우선 2를 x에 대입한다. 그렇게 되면 함수는 f(2,y) = y/2의 형태가 되고 이때 f(2,y)를 g(y)라는 새로운 함수라고 
	생각해 볼 수 있다. 이 g(y)의 y에 3을 대입하면 g(3)이 되고 g(3)는 f(2,3) = 3/2 로 계산된다.
	
###Why curry.scala
	scala> def apply[A, B](a: A)(f: A => B) = f(a)
	apply: [A,B](a: A)(f: (A) => B)B

	scala> apply(1)(_ * 2)
	res0: Int = 2

	scala> def apply[A, B](a: A, f: A => B) = f(a)
	apply: [A,B](a: A,f: (A) => B)B

	scala> apply(1, _ * 2)                        
	<console>:7: error: missing parameter type for expanded function ((x$1) => x$1.$times(2))
       apply(1, _ * 2)
                ^

	scala> apply(1, (_: Int) * 2)
	res2: Int = 2

###Multiple Parameter Lists
	The definition of function that return functions is so useful in functional programming that there is a special syntax for it in Scala.
	
	For example, the following definition of sum is equivalent to the one with
	the nested sumF function, but shorter:
	
	def sum(f: Int => Int)(a: Int, b: Int): Int = 
		if(a>b) 0 else f(a) + sum(f)(a+1, b)
		
###Exercise
	object exercise {
		def product(f: Int => Int)(a: Int, b: Int): Int = 
			if(a > b) 1
			else 
				f(a) * product(f)(a+1, b) 
		product(x => x*x)(3,4)
		
		def fact(n: Int) = product(x => x)(1,n)
		fact(5)
		
	}
	
	
	object exercise {
		def product(f: Int => Int)(a: Int, b: Int): Int = mapReduce(f, (x,y) => x * y, 1)(a, b)
		product(x => x*x)(3,4)
		
		def fact(n: Int) = product(x => x)(1,n)
		fact(5)
		
		def mapReduce(f: Int => Int, combine: (Int, Int) => Int, zero: Int)(a: Int, b: Int) 
			if(a > b) zero
			else
				combine(f(a)), mapReduce(f, combine, zero)(a+1, b))
	}
	
	
	
	
---
#Example: Finding Fixed Points
###Finding a fixed poing of a function
	A number x is called a fixed point of a functionf if
	f(x) = x
	
	For some functions f we can locate the fixed points by starting with an initial estimate 	and then by applying f in a repetitive way
	
	x, f(x), f(f(x)), f(f(f(x))), ...
	
	until the value does not vary anymore(or the change is sufficiently small)



###Programmatic Solution
	This leads to the following function for finding a fixed poing:
	
	var tolerance = 0.0001
	def isCloseEnough(x: Double, y: Double) = 
		abs((x - y) / x) / x < tolerance
	
	def fixedPoing(f: Double => Double)(firstGuess: Double) = {
		def iterate(guess: Double): Double = {
			var next = f(guess)
			if(isCloseEnough(guess, next)) next
			else iterate(next)
		}
		iterate(firstGuess)
	}
	
	def sqrt(x: Double) = fixedPoint(y => x / y)(1)
	sqrt(2) // doesn't converge..
	
	def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1.0)
	

##Function as Return Values
	The previous examples have shown that the expressive power of a language is greatly 	increased if we can pass function arguments.
	
	The following example shows that functions that return functions can also be very useful.
	
	Consider again iteration toward a fixed point.
	
	We begin by observing that sqrt(x) is a fixed point of the function y => x/y
	
	Then, the iteration converges by averaging successive values.
	
	This technique of stabilizing by averaging is general enough to merit being abstracted into
	its own function.
	
		def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2
		
###Write a square root function using 'fixedPoint' and 'averageDamp'
	
	def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

	def sqrt(x: Double) = fixedPoint(averageDamp(y => x/y))(1.0)  



---

#Scala Syntax Summary
	We have seen language  element to express types, expressions and definitions.
	
	Below, we give their context-free syntax in Extended Backus-Naur form(EBNF), where

		| denotes an alternatives,
		[...] an option (0 or 1),
		{...} a repetition (0 or more).

###Types
	type = SimpleType | FunctionType
	FunctionType = SimpleType => Type
				| '(' [Types] ')' '=>' Type
	SimpleType = Ident
	Types = Type {',' Type}
	
	A type can be
	1. A numeric type: Int, Double(and Byte Short, Char, Long, Float),
	2. The Boolean type with the values true and false,
	3. The String type,
	4. A Function type, like Int => Int, (Int, Int) => Int.
	
###Expression
	An expression can be :
		1. An identifier such as x, isGoodEnough,
		2. An literal, like 0, 1.0, "abc",
		3. An function application, like sqrt(x),
		4. An operator application, like -x, y+x,
		5. A selection like math.abs,
		6. A conditional expression like if(x>0) -x else x,
		7. A block like {val x = math.abs(y) ; x * 2}
		8. An anonymous function, like x => x + 1
		

##Definition 

1. function definition, like def square(x: Int) = x * x
2. value definition, like val y = square(2)

##parameter
	call by value parameter, like (x: Int),
	call by name parameter, like (y => Double)




---
#Functions and Data
	We'll learn how functions create and encapsulate data structures.
	
	Example 
	
	Rational Numbers
	we want to design a package for doing rational arithmetic.
	A rational number x/y is represented by two integers:
	
		-> its numerator x, and
		-> its denominator y.
		
###class
	class Rational(x: Int, y: Int) {
		def numer = x
		def denom = y
	}
	
	This definition introduces two entities:
	
	A new type, named Rational.
	A constructor Rational to create elements of this type.
	
**Scala keeps the name of types and values in different namespaces. **

**so there's no conflict between the two definition of Rational**

###Object
we call the element of a class type objects.

*Example*
	
	object rationals {
		val x = new Rational(1, 2)
		x.numer, x.denom
		
	}
	
	class Rational(x: Int, y: Int) {
		def numer = x
		def denom = y
	}

###Methods
	One can go futher and also package functions operating on a data 
	abstraction in the data abstraction itself.
	
	Such functions are called methods.
	
	Example
	
	Rational numbers now would have, in addition to the functions
	numer and denom, the functions add, sub, mul, div, equal, toString.


*Example*
	
	object rationals {
	

		
		val x = new Rational(1, 2)
		x.numer
		x.denom
		val y = new Rational(2,3)
		x.add(y).toString
		
	}
	
	class Rational(x: Int, y: Int) {
	
		require(y != 0, "denominator must be nonzero")
		
		def this(x: Int) = this(x, 1)
		
		private def gcd(a: Int, b: Int): Int = if(b == 0) a else gcd(b, a % b)
		private val g = gcd(x,y)
		def numer = x / g
		def denom = y / g
		
		def less(that : Rational) = numer * that.denom < that.numer * denom
		
		def max(that: Rational) = if(this.less(that)) that else this
		
		def add(that: Rational) = 
		new Rational(numer * that.denom + that.numer* denum, denom * that.denom)
	
		overide	def toString = numer + "/" + denom
		
		def neg: Rational = new Rational(-numer, denom)
		
		def sub(that: Rational) = add(that.neg)
	}
gcd and g are private members; we can only access them from inside the Rational class



In this example, we calculate gcd immediately, so that its value can be re-used in the calculations of numer and denom



###The Client's view
	Clients observe exactly the same behavior in each case.
	
	This ability to choose different implementations of the data without 
	affecting clients is called data abstration
	
	It is a cornerstone of software engineering


###Preconditions
	Let's say our Rational class requires that the denominator is positive.
	
	We can enforce this by callong the require function.
	
		class Rational(x: Int, y: Int) {
			require(y != 0, "denominator must be positive")
		}
		
	require is a predefined function.
	
	It takes a condition and an optional message string.
	
	If the condition passed to require is false, an 
	IllegalArgumentException is thrown with the given message string
	
###Assertions
	Besides require, there is also assert.
	
	Assert also takes a condition and an optiona;l message string as parameters. E.g.
	
	val x = sqrt(y)
	assert(x >= 0)
	
	Like require, a failing assert will also throw an exception, but it's a different one:
	AssertionError for assert, IllegalArgumentException for require.
	
	This reflects a difference in intent
	
		-> require is used to enforce a precondition on the caller of a function.



###Constructors
	In Scala, a class implicitly introduces a constructor. THis one is called the primart 	constructor of the class.
	
	The primary constructor
	
		->takes the parameters of the class
		->and executes all statements in the class body (such as the require a couple of slides 		back)
		
			def this(x: Int) = this(x, 1)


---
#Evaluation and Operators
	We previously defined the meaning of a function application using a computation model based 	on substitution. Now we extend this model to classes and objects.
	
	Question: How is an instantiation of the class new C(e1, ..., em) evaluated?
	
	Answer: The expression arguments e1, ... , em are evealuated like the arguments of a normal
		function. That's it
		
	The resulting expression, say, new C(v1, ..., vm), is already a value.
	
###Classes and Substitutions.
	Now suppose that we have a class definition.
		class C(x1, ..., xm) { ... def f(y1,..yn) = b ...}
	where 
		-> the formal parameters of the class are x1, ... , xm.
		-> The class defines a method f with formal parameters y1, ..., yn.
		
	(The list of function parameters can be absent. For simplicity, we have omitted the 	parameter types)
	
	Question: How is the following expression evaluated?
		new C(v1, ..., vm).f(w1, ... wm)
		
		
###Object Rewriting Examples
	new Rational(1, 2).numer
	new Rational(1, 2).less(new Rationa(2,3))
	
###Operators
	In principle, the rational numbers defined by Rational are as natural as integers.
	
	But for the user of these abstractions, there is a noticeable difference:
		-> We write x + y, if x and y are integers, but
		-> We write r.add(s) if r and s are rational numbers.
		
	In Scala, we can eleminate this difference. We procede in two steps.
	
	r add s == r.add(s) 
	
###Step 2: Relaxed Identifiers
	operators can be used as identifiers
	
	Thus, an identifier can be: 
		-> Alphanumeric: starting with a letter, followed by a sequence of letters or numbers
		-> Symbolic: starting with an operator symbol, followed by other operator symbols.
		-> The underscore character '_' counts as a letter.
		-> Alphanumeric identifiers can also end in an underscore, followed by some operator symbols.
		Examples of identifiers:
		x1	*	+?%&	vector_++	counter_=
		
		
		def < that: Rational) = numeer ....
		instead def less ..
		
		unary_-, binary_-
		
###operators for Rationals
	... and rational numbers can be used like Int or Double:
	
	val x = new Rational(1, 2)
	val y = new Rational(1, 3)
	(y * x) + (y * y)
	
	Precedence Rules..
	(all letters)
	|
	^
	&
	<>
	=!
	:
	+-
	* / %
	(all other special characters)
	
	a + b ^? c ?^ less a ==> b | c
	((a + b) ^? (c ?^ d)) less ((a ==> b) | c)
	every binary operation needs to be put into parentheses, but the structure of the expression should not change.
	
