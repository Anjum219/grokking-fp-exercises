case class TvShow(title: String, start: Int, end: Int)

def listShows(shows: List[TvShow]): List[TvShow] =
  shows
    .sortBy(tvShow => tvShow.end - tvShow.start)
    .reverse

def parseShows(rawShows: List[String]): List[TvShow] =
  rawShows.map(rawShow => parseShow(rawShow))

def parseShow(rawShow: String): TvShow =
  val bracketOpen = rawShow.indexOf('(')
  val bracketClose = rawShow.indexOf(')')
  val dash = rawShow.indexOf('-')

  val name = rawShow.substring(0, bracketOpen).trim
  val yearStart = Integer.parseInt(rawShow.substring(bracketOpen + 1, dash))
  val yearEnd = Integer.parseInt(rawShow.substring(dash + 1, bracketClose))

  TvShow(name, yearStart, yearEnd)



object chap06 extends App {
  val shows = List(
    TvShow("Breaking Bad", 2008, 2013),
    TvShow("The Wire", 2002, 2008),
    TvShow("Mad Men", 2007, 2015)
  )
  println(listShows(shows))

  val rawShows: List[String] = List(
    "Breaking Bad (2008-2013)",
    "The Wire (2002-2008)",
    "Mad Men (2007-2015)"
  )
  println(parseShows(rawShows))
}
