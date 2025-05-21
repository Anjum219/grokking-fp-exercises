// Coffee break: Dealing with lists of lists

case class Book(title: String, authors: List[String])

def recommendedBooks(friend: String): List[Book] =
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

def getBookRecommendations(friends: List[String]): List[Book] =
  friends.flatMap(recommendedBooks)

def getAuthorRecommendations(friends: List[String]): List[String] = friends
  .flatMap(recommendedBooks)
  .flatMap(_.authors)

// Coffee break: Filtering techniques

case class Point(x: Int, y: Int)

def isPointInsideCircle(point: Point, radius: Int): Boolean =
  radius * radius >= point.x * point.x + point.y * point.y

def getPointsInsideCircleUsingFilter(points: List[Point], radiuses: List[Int]): List[String] =
  for {
    r <- radiuses.filter(_ >= 0)
    p <- points.filter(p => isPointInsideCircle(p, r))
  } yield s"$p is within a radius of $r"

def getPointsInsideCircleUsingGuard(points: List[Point], radiuses: List[Int]): List[String] =
  for {
    r <- radiuses
    p <- points
    if r >= 0
    if isPointInsideCircle(p, r)
  } yield s"$p is within a radius of $r"

def radiusFilter(r: Int): List[Int] =
  if(r >= 0) List(r) else List.empty

def insideFilter(point: Point, r: Int): List[Point] =
  if(isPointInsideCircle(point, r)) List(point) else List.empty

def getPointsInsideCircleUsingFunction(points: List[Point], radiuses: List[Int]): List[String] =
  for {
    anyRadius <- radiuses
    r <- radiusFilter(anyRadius)
    anyPoint <- points
    p <- insideFilter(anyPoint, r)
  } yield s"$p is within a radius of $r"



object chap05 extends App {
  val recommendations = getBookRecommendations(List("Alice", "Bob", "Charlie"))
  println(recommendations)
  val authors = getAuthorRecommendations(List("Alice", "Bob", "Charlie"))
  println(authors)

  val points = List(Point(5, 2), Point(1, 1))
  val riskyRadiuses = List(-10, 0, 2)
  println(getPointsInsideCircleUsingFilter(points, riskyRadiuses))
  println(getPointsInsideCircleUsingGuard(points, riskyRadiuses))
  println(getPointsInsideCircleUsingFunction(points, riskyRadiuses))
}
