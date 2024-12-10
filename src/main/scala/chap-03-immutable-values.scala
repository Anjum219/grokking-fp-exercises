// Practicing immutable slicing and appending

// Write the function called firstTwo, which gets a list and returns a new list that contains
// only the first two elements of the incoming list. This assertion should be true:
// firstTwo(List("a", "b", "c")) == List("a", "b")

def firstTwo(list: List[String]): List[String] =
  val sliceIndex = Math.min(2, list.length)
  list.slice(0, sliceIndex)


object chap03 extends App {
  assert(firstTwo(List("ab", "bc", "cd")) == List("ab", "bc"))
  assert(firstTwo(List("ab", "bc"))== List("ab", "bc"))
  assert(firstTwo(List("ab"))== List("ab"))
  assert(firstTwo(List())== List())
}
