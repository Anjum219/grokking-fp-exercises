// Practicing immutable slicing and appending

// Write the function called firstTwo, which gets a list and returns a new list that contains
// only the first two elements of the incoming list. This assertion should be true:
// firstTwo(List("a", "b", "c")) == List("a", "b")

def firstTwo(list: List[String]): List[String] =
  val sliceIndex = Math.min(2, list.length)
  list.slice(0, sliceIndex)

// Write the function called lastTwo, which gets a list and returns a new list that contains
// only the last two elements of the incoming list. The assertion below should be true:
// lastTwo(List("a", "b", "c")) == List("b", "c")

def lastTwo(list: List[String]): List[String] =
  val beginningIndex = Math.max(0, list.length-2)
  list.slice(beginningIndex, list.length)

// Write the function called movedFirstTwoToTheEnd, which gets a list and returns a new
// list with the first two elements of the incoming list moved to the end. The following
// assertion should be true:
// movedFirstTwoToTheEnd(List("a", "b", "c")) == List("c", "a", "b")

def movedFirstTwoToTheEnd(list: List[String]): List[String] =
  if list.length < 3 then
    list
  else
    val firstTwo = list.slice(0, 2)
    val withoutFirstTwo = list.slice(2, list.length)
    withoutFirstTwo.appendedAll(firstTwo)
  // alternate solution
  // if list.length < 3 then list else list.drop(2) ++ list.take(2)


object chap03 extends App {
  assert(firstTwo(List("ab", "bc", "cd")) == List("ab", "bc"))
  assert(firstTwo(List("ab", "bc"))== List("ab", "bc"))
  assert(firstTwo(List("ab"))== List("ab"))
  assert(firstTwo(List())== List())

  assert(lastTwo(List("one", "two", "three")) == List("two", "three"))
  assert(lastTwo(List("one", "two")) == List("one", "two"))
  assert(lastTwo(List("one")) == List("one"))
  assert(lastTwo(List()) == List())

  assert(
    movedFirstTwoToTheEnd(List("qwe", "asd", "poi", "cvb")) ==
      List("poi", "cvb", "qwe", "asd")
  )
  assert(movedFirstTwoToTheEnd(List("qwe", "asd")) == List("qwe", "asd"))
  assert(movedFirstTwoToTheEnd(List("qwe")) == List("qwe"))
  assert(movedFirstTwoToTheEnd(List()) == List())
}
