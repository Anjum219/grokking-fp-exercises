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


// Practicing currying

// Return a new List with all numbers larger than 4 (pass 4 as an argument).
// input: List(5, 1, 2, 4, 0) output: List(5)
def largerThanN(n: Int)(i: Int): Boolean = i > n

// Return a new List that contains numbers divisible by 5 (pass 5 as an
// argument).
// input: List(5, 1, 2, 4, 15) output: List(5, 15)
def divisibleByN(n: Int)(i: Int): Boolean = i % n == 0

// Return words that are shorter than four characters (pass 4 as an argument).
// input: List("scala", "ada") output: List("ada")
def shorterThanThreshold(threshold: Int)(word: String): Boolean = word.length < threshold

// Return words that have more than two of the letter 's' inside (pass 2 as an
// argument).
// input: List("rust", "ada") output: List()
def filterMoreThanThreshold(threshold: Int)(word: String): Boolean =
  word.count(_ == 's') > threshold


// Practicing foldLeft

// Return a sum of all integers in the given list.
// input: List(5, 1, 2, 4, 100) output: 112
def sum(list: List[Int]): Int = list.foldLeft(0)((sum, i) => sum + i)

// Return the total length of all the words in the given list.
// input: List("scala", "rust", "ada") output: 12
def totalLengthOfWords(words: List[String]): Int =
  words.foldLeft(0)((len, word) => len + word.length)

// Return the number of the letter 's' found in all the words in the given list.
// input: List("scala", "haskell", "rust", "ada") output: 3
def totalOccurrencesOfS(words: List[String]): Int =
  words.foldLeft(0)((sum, word) => sum + word.count(_ == 's'))

// Return the maximum of all integers in the given list.
// input: List(5, 1, 2, 4, 15) output: 15
def max(numbers: List[Int]): Int =
  numbers.foldLeft(Int.MinValue)((max, i) => if (i > max) i else max)


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

  assert(List(5, 1, 2, 4, 0).filter(largerThanN(4)) == List(5))
  assert(List(5, 1, 2, 4, 15).filter(divisibleByN(5)) == List(5, 15))
  assert(List("scala", "ada").filter(shorterThanThreshold(4)) == List("ada"))
  assert(List("rust", "ada").filter(filterMoreThanThreshold(2)) == List())

  assert(sum(List(5, 1, 2, 4, 100)) == 112)
  assert(sum(List()) == 0)
  assert(totalLengthOfWords( List("scala", "rust", "ada")) == 12)
  assert(totalLengthOfWords(List()) == 0)
  assert(totalOccurrencesOfS(List("scala", "haskell", "rust", "ada")) == 3)
  assert(totalOccurrencesOfS(List("", "qwe")) == 0)
  assert(max(List(5, 1, 2, 4, 15)) == 15)
  assert(max(List(-100, 0, 100)) == 100)
  assert(max(List()) == Int.MinValue)
}
