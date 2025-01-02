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


object chap04 extends App {
  assert(sortByLength(List("abc", "ac", "ab", "b", "a")) == List("b", "a", "ac", "ab", "abc"))

  assert(sortByLetterSCount(List("ssss", "qwe", "zxc", "asd", "")) == List("qwe", "zxc", "", "asd", "ssss"))

  assert(sortIntsDesc(List(1, 2, 3, 4, 5)) == List(5, 4, 3, 2, 1))

  assert(sortByLetterSCountDesc(List("ssss", "qwe", "zxc", "asd", "")) == List("ssss", "asd", "qwe", "zxc", ""))

  assert(filterShorts(List("scala", "rust", "ada")) == List("rust", "ada"))

  assert(filterMore(List("scala", "rust", "ada")) == List())

  assert(filterOdd(List(5, 1, 2, 4, 0)) == List(5, 1))

  assert(filterLarge(List(5, 1, 2, 4, 0)) == List(5))
}
