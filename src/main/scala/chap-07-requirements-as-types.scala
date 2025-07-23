object Location:
  opaque type Location = String

  def apply(value: String): Location = value

  extension(location: Location)
    def name: String = location

object chap07 extends App {
  import Location.Location

  case class Artist(
    name: String,
    genre: String,
    origin: Location,
    yearsActiveStart: Int,
    isActive: Boolean,
    yearsActiveEnd: Int,
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
        (genres.isEmpty || genres.contains(artist.genre))
        && (locations.isEmpty || locations.contains(artist.origin.name))
        && (!searchByActiveYears || isActiveDuringYears(
          artist.yearsActiveStart, artist.isActive, artist.yearsActiveEnd, activeAfter, activeBefore
        ))
      )



  val artists = List(
    Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0),
    Artist("Led Zeppelin", "Hard Rock", Location("England"), 1968, false, 1980),
    Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
  )

  val searchRes1 = searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022)
  assert(searchRes1 == List(Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)))

  val searchRes2 = searchArtists(artists, List.empty, List("England"), true, 1950, 2022)
  assert(searchRes2 == List(
    Artist("Led Zeppelin", "Hard Rock", Location("England"), 1968, false, 1980),
    Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
  ))

  val searchRes3 = searchArtists(artists, List.empty, List.empty, true, 1981, 2003)
  assert(searchRes3 == List(
    Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0),
    Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
  ))

  val searchRes4 = searchArtists(artists, List.empty, List("U.S."), false, 0, 0)
  assert(searchRes4 == List(Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0)))

  val searchRes5 = searchArtists(artists, List.empty, List.empty, false, 2019, 2022)
  assert(searchRes5 == List(
    Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0),
    Artist("Led Zeppelin", "Hard Rock", Location("England"), 1968, false, 1980),
    Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
  ))
}
