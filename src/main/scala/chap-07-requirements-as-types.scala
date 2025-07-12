object Location:
  opaque type Location = String

  def apply(value: String): Location = value

  extension(location: Location)
    def name: String = location

object Genre:
  opaque type Genre = String

  def apply(value: String): Genre = value

  extension(genre: Genre)
    def name: String = genre

object YearsActiveStart:
  opaque type YearsActiveStart = Int

  def apply(value: Int): YearsActiveStart = value

  extension(year: YearsActiveStart)
    def value: Int = year

object YearsActiveEnd:
  opaque type YearsActiveEnd = Int

  def apply(value: Int): YearsActiveEnd = value

  extension(year: YearsActiveEnd)
    def value: Int = year

object chap07 extends App {
  import Location.Location
  import Genre.Genre
  import YearsActiveStart.YearsActiveStart
  import YearsActiveEnd.YearsActiveEnd

  case class Artist(
    name: String,
    genre: Genre,
    origin: Location,
    yearsActiveStart: YearsActiveStart,
    isActive: Boolean,
    yearsActiveEnd: YearsActiveEnd,
  )

  def isActiveDuringYears(
    activeStart: Int,
    isActive: Boolean,
    activeEnd: Int,
    activeRangeStart: Int,
    activeRangeEnd: Int,
  ): Boolean =
    (activeStart >= activeRangeStart && activeStart <= activeRangeEnd)
    || (!isActive && activeEnd >= activeRangeStart && activeEnd <= activeRangeEnd)
    || (activeStart <= activeRangeStart && (isActive || activeEnd >= activeRangeEnd))

  def searchArtists(
    artists: List[Artist],
    genres: List[String],
    locations: List[String],
    searchByActiveYears: Boolean,
    activeAfter: Int,
    activeBefore: Int
  ): List[Artist] =
    artists
      .filter(artist =>
        (genres.isEmpty || genres.contains(artist.genre.name))
        && (locations.isEmpty || locations.contains(artist.origin.name))
        && (!searchByActiveYears || isActiveDuringYears(
          artist.yearsActiveStart.value, artist.isActive, artist.yearsActiveEnd.value, activeAfter, activeBefore
        ))
      )

  
  
  val artists = List(
    Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearsActiveStart(1981), true, YearsActiveEnd(0)),
    Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearsActiveStart(1968), false, YearsActiveEnd(1980)),
    Artist("Bee Gees", Genre("Pop"), Location("England"), YearsActiveStart(1958), false, YearsActiveEnd(2003))
  )

  val searchRes1 = searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022)
  assert(searchRes1 == List(Artist("Bee Gees", Genre("Pop"), Location("England"), YearsActiveStart(1958), false, YearsActiveEnd(2003))))

  val searchRes2 = searchArtists(artists, List.empty, List("England"), true, 1950, 2022)
  assert(searchRes2 == List(
    Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearsActiveStart(1968), false, YearsActiveEnd(1980)),
    Artist("Bee Gees", Genre("Pop"), Location("England"), YearsActiveStart(1958), false, YearsActiveEnd(2003))
  ))

  val searchRes3 = searchArtists(artists, List.empty, List.empty, true, 1981, 2003)
  assert(searchRes3 == List(
    Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearsActiveStart(1981), true, YearsActiveEnd(0)),
    Artist("Bee Gees", Genre("Pop"), Location("England"), YearsActiveStart(1958), false, YearsActiveEnd(2003))
  ))

  val searchRes4 = searchArtists(artists, List.empty, List("U.S."), false, 0, 0)
  assert(searchRes4 == List(Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearsActiveStart(1981), true, YearsActiveEnd(0))))

  val searchRes5 = searchArtists(artists, List.empty, List.empty, false, 2019, 2022)
  assert(searchRes5 == List(
    Artist("Metallica", Genre("Heavy Metal"), Location("U.S."), YearsActiveStart(1981), true, YearsActiveEnd(0)),
    Artist("Led Zeppelin", Genre("Hard Rock"), Location("England"), YearsActiveStart(1968), false, YearsActiveEnd(1980)),
    Artist("Bee Gees", Genre("Pop"), Location("England"), YearsActiveStart(1958), false, YearsActiveEnd(2003))
  ))
}