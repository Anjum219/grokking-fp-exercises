case class TvShow(title: String, start: Int, end: Int)

def listShows(shows: List[TvShow]): List[TvShow] =
  shows
    .sortBy(tvShow => tvShow.end - tvShow.start)
    .reverse

def parseShows(rawShows: List[String]): List[TvShow] =
  rawShows.flatMap(rawShow => parseShow(rawShow))

def parseShowsWithAllOrNothing(rawShows: List[String]): Option[List[TvShow]] =
  val initialValue: Option[List[TvShow]] = Some(List.empty)
  rawShows
    .map(parseShow)
    .foldLeft(initialValue)(addOrResign)

def addOrResign(
  parsedShows: Option[List[TvShow]],
  newParsedShow: Option[TvShow]
): Option[List[TvShow]] =
  for {
    shows <- parsedShows
    parsedShow <- newParsedShow
  } yield shows.appended(parsedShow)

def parseShow(rawShow: String): Option[TvShow] =
  for {
    name <- extractName(rawShow)
    yearStart <- extractYearStart(rawShow).orElse(extractSingleYear(rawShow))
    yearEnd <- extractYearEnd(rawShow).orElse(extractSingleYear(rawShow))
  } yield TvShow(name, yearStart, yearEnd)

def extractYearStart(rawShow: String): Option[Int] =
  val openBracketIndex = rawShow.indexOf('(')
  val dashIndex = rawShow.indexOf('-')
  for {
    yearStr <- if (openBracketIndex > -1 && dashIndex > openBracketIndex + 1)
                  Some(rawShow.substring(openBracketIndex + 1, dashIndex))
                else None
    year <- yearStr.toIntOption
  } yield year

def extractYearEnd(rawShow: String): Option[Int] =
  val dashIndex = rawShow.indexOf('-')
  val bracketCloseIndex = rawShow.indexOf(')')
  for {
    yearStr <- if (dashIndex > -1 && bracketCloseIndex > dashIndex + 1)
                  Some(rawShow.substring(dashIndex + 1, bracketCloseIndex))
                else None
    year <- yearStr.toIntOption
  } yield year

def extractSingleYear(rawShow: String): Option[Int] =
  val dashIndex = rawShow.indexOf('-')
  val bracketOpenIndex = rawShow.indexOf('(')
  val bracketCloseIndex = rawShow.indexOf(')')
  for {
    yearStr <- if (dashIndex == -1 && bracketOpenIndex > -1 && bracketCloseIndex > bracketOpenIndex + 1)
                Some(rawShow.substring(bracketOpenIndex + 1, bracketCloseIndex))
               else
                None
    year <- yearStr.toIntOption
  } yield year

def extractName(rawShow: String): Option[String] =
  val bracketOpenIndex = rawShow.indexOf('(')
  if (bracketOpenIndex > 0)
    Some(rawShow.substring(0, bracketOpenIndex).trim)
  else None

def extractSingleYearOrYearEnd(rawShow: String): Option[Int] =
  extractSingleYear(rawShow).orElse(extractYearEnd(rawShow))

def extractAnyYear(rawShow: String): Option[Int] =
  extractYearStart(rawShow)
    .orElse(extractYearEnd(rawShow))
    .orElse(extractSingleYear(rawShow))

def extractSingleYearIfNameExists(rawShow: String): Option[Int] =
  extractName(rawShow).flatMap(name => extractSingleYear(rawShow))

def extractAnyYearIfNameExists(rawShow: String): Option[Int] =
  extractName(rawShow).flatMap(name => extractAnyYear(rawShow))



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
    "Mad Men (-2015)",
    "Chernobyl (2013)"
  )
  println(parseShows(rawShows))

  println(addOrResign(Some(List.empty), Some(TvShow("Chernobyl", 2019, 2019))))
  println(
    addOrResign(
      Some(List(TvShow("Chernobyl", 2019, 2019))),
      Some(TvShow("The Wire", 2002, 2008))
    )
  )
  println(addOrResign(Some(List(TvShow("Chernobyl", 2019, 2019))), None))
  println(addOrResign(None, Some(TvShow("Chernobyl", 2019, 2019))))
  println(addOrResign(None, None))

  println(parseShows(List("Chernobyl (2019)", "Breaking Bad")))
  println(parseShows(List("Chernobyl (2019)")))
  println(parseShows(List()))
  println(parseShowsWithAllOrNothing(List("Chernobyl (2019)", "Breaking Bad")))
  println(parseShowsWithAllOrNothing(List("Chernobyl (2019)")))
  println(parseShowsWithAllOrNothing(List()))
}
