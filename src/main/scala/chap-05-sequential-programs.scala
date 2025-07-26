// Coffee break: Dealing with lists of lists

case class Book(title: String, authors: List[String])

def recommendedBooks(friend: String): List[Book] =
  val scala = List(
    Book("FP in Scala", List("Chiusano", "Bjarnason")),
    Book("Get Programming with Scala", List("Sfregola"))
  )
  val fiction = List(
    Book("Harry Potter", List("Rowling")),
    Book("The Lord of the Rings", List("Tolkien"))
  )
  if (friend == "Alice") scala
  else if (friend == "Bob") fiction
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

def getPointsInsideCircleUsingFilter(
    points: List[Point],
    radiuses: List[Int]
): List[String] =
  for {
    r <- radiuses.filter(_ >= 0)
    p <- points.filter(p => isPointInsideCircle(p, r))
  } yield s"$p is within a radius of $r"

def getPointsInsideCircleUsingGuard(
    points: List[Point],
    radiuses: List[Int]
): List[String] =
  for {
    r <- radiuses
    p <- points
    if r >= 0
    if isPointInsideCircle(p, r)
  } yield s"$p is within a radius of $r"

def radiusFilter(r: Int): List[Int] =
  if (r >= 0) List(r) else List.empty

def insideFilter(point: Point, r: Int): List[Point] =
  if (isPointInsideCircle(point, r)) List(point) else List.empty

def getPointsInsideCircleUsingFunction(
    points: List[Point],
    radiuses: List[Int]
): List[String] =
  for {
    anyRadius <- radiuses
    r         <- radiusFilter(anyRadius)
    anyPoint  <- points
    p         <- insideFilter(anyPoint, r)
  } yield s"$p is within a radius of $r"

// Coffee break:
// Parsing with Option

case class Event(name: String, start: Int, end: Int)

def validateName(name: String): Option[String] =
  if name.size > 0 then Some(name) else None

def validateStart(start: Int, end: Int): Option[Int] =
  if start <= end then Some(start) else None

def validateEnd(end: Int): Option[Int] =
  if end < 3000 then Some(end) else None

def validateLength(start: Int, end: Int, minLength: Int): Option[Int] =
  val length = end - start
  if length > minLength then Some(length) else None

def parseLongEvent(name: String, start: Int, end: Int, minLength: Int): Option[Event] =
  for {
    validName  <- validateName(name)
    validEnd   <- validateEnd(end)
    validStart <- validateStart(start, end)
    length     <- validateLength(start, end, minLength)
  } yield Event(validName, validStart, validEnd)

object chap05 extends App {
  val recommendations = getBookRecommendations(List("Alice", "Bob", "Charlie"))
  println(recommendations)
  val authors = getAuthorRecommendations(List("Alice", "Bob", "Charlie"))
  println(authors)

  val points        = List(Point(5, 2), Point(1, 1))
  val riskyRadiuses = List(-10, 0, 2)
  println(getPointsInsideCircleUsingFilter(points, riskyRadiuses))
  println(getPointsInsideCircleUsingGuard(points, riskyRadiuses))
  println(getPointsInsideCircleUsingFunction(points, riskyRadiuses))

  println(parseLongEvent("Apollo Program", 1961, 1972, 10))
  println(parseLongEvent("World War II", 1939, 1945, 10))
  println(parseLongEvent("", 1939, 1945, 10))
  println(parseLongEvent("Apollo Program", 1972, 1961, 10))
}
