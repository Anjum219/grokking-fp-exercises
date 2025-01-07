// Practicing function passing

// Sort the list of Strings by their length in ascending order.
// input: List("scala", "rust", "ada")
// expected output: List("ada", "rust", "scala")
def sortByLength(list: List[String]): List[String] =
  list.sortBy(_.length)

// Sort the list of Strings provided below by number of the letter 's' inside
// these Strings, in ascending order.
// input: List("rust", "ada") expected output: List("ada", "rust")
def sortByLetterSCount(list: List[String]): List[String] =
  list.sortBy(_.count(_ == 's'))

// Sort the list of Ints provided below in descending order.
// input: List(5, 1, 2, 4, 3) expected output: List(5, 4, 3, 2, 1)
def sortIntsDesc(list: List[Int]): List[Int] =
  list.sortBy(_ * -1)

// Similarly to the second one, sort the list of Strings provided below by number
// of the letter 's' inside these Strings but in descending order.
// input: List("ada", "rust") output: List("rust", "ada")
def sortByLetterSCountDesc(list: List[String]): List[String] =
  list.sortBy(_.count(_ == 's') * -1)


// Practicing filter

// Return words that are shorter than five characters.
// input: List("scala", "rust", "ada") output: List("rust", "ada")
def filterShorts(list: List[String]): List[String] =
  list.filter(_.length < 5)

// Return words that have more than two of the letter 's'.
// input: List("rust", "ada") output: List()
def filterMore(list: List[String]): List[String] =
  list.filter(_.count(_ == 's') > 2)

// Return a new List with only odd numbers.
// input: List(5, 1, 2, 4, 0) output: List(5, 1)
def filterOdd(list: List[Int]): List[Int] =
  list.filter(_ % 2 == 1)

// Return a new List with all numbers larger than 4.
// input: List(5, 1, 2, 4, 0) output: List(5)
def filterLarge(list: List[Int]): List[Int] =
  list.filter(_ > 4)


// Returning functions practice

// Return a new List with all numbers larger than 4.
// input: List(5, 1, 2, 4, 0) output: List(5)
// Change: Now return a new List with all numbers larger than 1.
// input: List(5, 1, 2, 4, 0) output: List(5, 2, 4)
def largerThan(n: Int): Int => Boolean = i => i > n
def filterListLargerThan(list: List[Int], n: Int): List[Int] =
  list.filter(largerThan(n))

// Return a new List that contains only numbers divisible by 5.
// input: List(5, 1, 2, 4, 15) output: List(5, 15)
// Change: Now return a new List that contains only number divisible by 2.
// input: List(5, 1, 2, 4, 15) output: List(2, 4)
def divisibleBy(n: Int): Int => Boolean = i => i % n == 0
def filterListDivisibleBy(list: List[Int], n: Int): List[Int] =
  list.filter(divisibleBy(n))

// Return words that are shorter than four characters.
// input: List("scala", "ada") output: List("ada")
// Change: Now return words that are shorter than seven characters.
// input: List("scala", "ada") output: List("scala", "ada")
def shorterThan(threshold: Int): String => Boolean = str => str.length < threshold
def filterListShorterThan(list: List[String], threshold: Int): List[String] =
  list.filter(shorterThan(threshold))

// Return words that have more than two of the letter 's' inside.
// input: List("rust", "ada") output: List()
// Change: Now return words that have more than zero of the letter 's'
// inside.
// input: List("rust", "ada") output: List("rust")
def filterMoreThan(threshold: Int): String => Boolean = str => str.count(_ == 's') > threshold
def filterListMoreThan(list: List[String], threshold: Int): List[String] =
  list.filter(filterMoreThan(threshold))


object chap04 extends App {
  assert(sortByLength(List("abc", "ac", "ab", "b", "a")) == List("b", "a", "ac", "ab", "abc"))

  assert(sortByLetterSCount(List("ssss", "qwe", "zxc", "asd", "")) == List("qwe", "zxc", "", "asd", "ssss"))

  assert(sortIntsDesc(List(1, 2, 3, 4, 5)) == List(5, 4, 3, 2, 1))

  assert(sortByLetterSCountDesc(List("ssss", "qwe", "zxc", "asd", "")) == List("ssss", "asd", "qwe", "zxc", ""))

  assert(filterShorts(List("scala", "rust", "ada")) == List("rust", "ada"))

  assert(filterMore(List("scala", "rust", "ada")) == List())

  assert(filterOdd(List(5, 1, 2, 4, 0)) == List(5, 1))

  assert(filterLarge(List(5, 1, 2, 4, 0)) == List(5))

  assert(filterListLargerThan(List(5, 1, 2, 4, 0), 4) == List(5))
  assert(filterListLargerThan(List(5, 1, 2, 4, 0), 1) == List(5, 2, 4))

  assert(filterListDivisibleBy(List(5, 1, 2, 4, 15), 5) == List(5, 15))
  assert(filterListDivisibleBy(List(5, 1, 2, 4, 15), 2) == List(2, 4))

  assert(filterListShorterThan(List("scala", "ada"), 4) == List("ada"))
  assert(filterListShorterThan(List("scala", "ada"), 7) == List("scala", "ada"))

  assert(filterListMoreThan(List("rust", "ada"), 2) == List())
  assert(filterListMoreThan(List("rust", "ada"), 0) == List("rust"))
}
