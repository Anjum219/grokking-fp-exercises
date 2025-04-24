// Coffee break:
// Dealing with lists of lists

case class Book(title: String, authors: List[String])

def recommendedBooks(friend: String): List[Book] = {
  val scala = List(
    Book("FP in Scala", List("Chiusano", "Bjarnason")),
    Book("Get Programming with Scala", List("Sfregola")),
  )
  val fiction = List(
    Book("Harry Potter", List("Rowling")),
    Book("The Lord of the Rings", List("Tolkien")),
  )
  if(friend == "Alice") scala
  else if(friend == "Bob") fiction
  else List.empty
}

def getBookRecommendations(friends: List[String]): List[Book] =
  friends.flatMap(recommendedBooks)

def getAuthorRecommendations(friends: List[String]): List[String] = friends
  .flatMap(recommendedBooks)
  .flatMap(_.authors)



object chap05 extends App {
  val recommendations = getBookRecommendations(List("Alice", "Bob", "Charlie"))
  println(recommendations)
  val authors = getAuthorRecommendations(List("Alice", "Bob", "Charlie"))
  println(authors)
}
