object Location:
  opaque type Location = String

  def apply(value: String): Location = value

  extension (location: Location) def name: String = location

object chap07 extends App {
  import Location.Location

  case class Artist(
      name: String,
      genre: String,
      origin: Location,
      yearsActiveStart: Int,
      isActive: Boolean,
      yearsActiveEnd: Int
  )

  def isActiveDuringYears(
      activeStart: Int,
      isActive: Boolean,
      activeEnd: Int,
      activeRangeStart: Int,
      activeRangeEnd: Int
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
            artist.yearsActiveStart,
            artist.isActive,
            artist.yearsActiveEnd,
            activeAfter,
            activeBefore
          ))
      )

  val artists = List(
    Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0),
    Artist("Led Zeppelin", "Hard Rock", Location("England"), 1968, false, 1980),
    Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
  )

  val searchRes1 = searchArtists(artists, List("Pop"), List("England"), true, 1950, 2022)
  assert(
    searchRes1 == List(Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003))
  )

  val searchRes2 = searchArtists(artists, List.empty, List("England"), true, 1950, 2022)
  assert(
    searchRes2 == List(
      Artist("Led Zeppelin", "Hard Rock", Location("England"), 1968, false, 1980),
      Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
    )
  )

  val searchRes3 = searchArtists(artists, List.empty, List.empty, true, 1981, 2003)
  assert(
    searchRes3 == List(
      Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0),
      Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
    )
  )

  val searchRes4 = searchArtists(artists, List.empty, List("U.S."), false, 0, 0)
  assert(
    searchRes4 == List(
      Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0)
    )
  )

  val searchRes5 = searchArtists(artists, List.empty, List.empty, false, 2019, 2022)
  assert(
    searchRes5 == List(
      Artist("Metallica", "Heavy Metal", Location("U.S."), 1981, true, 0),
      Artist("Led Zeppelin", "Hard Rock", Location("England"), 1968, false, 1980),
      Artist("Bee Gees", "Pop", Location("England"), 1958, false, 2003)
    )
  )
}

object chap07HigherOrderFuncs extends App {
  case class User(name: String, city: Option[String], favoriteArtists: List[String])

  val users = List(
    User("Alice", Some("Melbourne"), List("Bee Gees")),
    User("Bob", Some("Lagos"), List("Bee Gees")),
    User("Eve", Some("Tokyo"), List.empty),
    User("Mallory", None, List("Metallica", "Bee Gees")),
    User("Trent", Some("Buenos Aires"), List("Led Zeppelin"))
  )

  def getUsersNotOutsideOfCity(users: List[User], city: String) =
    users.filter(_.city.forall(_ == city)).map(_.name)

  def getUsersInsideCity(users: List[User], city: String) =
    users.filter(_.city.exists(_ == city)).map(_.name)

  def getFansOfArtist(users: List[User], artist: String) =
    users.filter(_.favoriteArtists.contains(artist)).map(_.name)

  def getUsersInsideCityWithLetter(users: List[User], letter: String) =
    users.filter(_.city.exists(_.startsWith(letter))).map(_.name)

  def getFansOfArtistWithLengthOrNoArtist(users: List[User], artistLen: Int) =
    users.filter(_.favoriteArtists.forall(_.length() > artistLen)).map(_.name)

  def getFansOfArtistWithLetter(users: List[User], letter: String) =
    users.filter(_.favoriteArtists.exists(_.startsWith(letter))).map(_.name)

  println(getUsersNotOutsideOfCity(users, "Melbourne")) // List("Alice", "Mallory")
  println(getUsersInsideCity(users, "Lagos"))           // List("Bob")
  println(getFansOfArtist(users, "Bee Gees"))           // List("Alice", "Bob", "Mallory")
  println(getUsersInsideCityWithLetter(users, "T"))     // List("Eve")
  println(getFansOfArtistWithLengthOrNoArtist(users, 8)) // List("Eve", "Trent")
  println(getFansOfArtistWithLetter(users, "M"))         // List("Mallory"))
}

object chap07WithADT extends App {
  import Location.Location

  enum MusicGenre:
    case HeavyMetal
    case Pop
    case HardRock

  import MusicGenre.*

  enum YearsActive:
    case StillActive(since: Int)
    case ActiveBetween(start: Int, end: Int)

  import YearsActive.*

  case class Artist(
      name: String,
      genre: MusicGenre,
      origin: Location,
      yearsActive: YearsActive
  )

  enum SearchCondition:
    case SearchByGenre(genres: List[MusicGenre])
    case SearchByOrigin(locations: List[Location])
    case SearchByActiveYears(start: Int, end: Int)

  import SearchCondition.*

  def wasArtistActive(
      artist: Artist,
      yearStart: Int,
      yearEnd: Int
  ): Boolean =
    artist.yearsActive match
      case StillActive(since)        => since <= yearEnd
      case ActiveBetween(start, end) => start <= yearEnd && end >= yearStart

  def activeLength(artist: Artist, currentYear: Int): Int =
    artist.yearsActive match
      case StillActive(since)        => currentYear - since
      case ActiveBetween(start, end) => end - start

  def searchArtists(
      artists: List[Artist],
      requiredConditions: List[SearchCondition]
  ): List[Artist] =
    artists.filter(artist =>
      requiredConditions.forall(condition =>
        condition match {
          case SearchByGenre(genres)           => genres.contains(artist.genre)
          case SearchByOrigin(locations)       => locations.contains(artist.origin)
          case SearchByActiveYears(start, end) => wasArtistActive(artist, start, end)
        }
      )
    )

  assert(
    activeLength(
      Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1981)),
      2022
    ) == 41
  )
  assert(
    activeLength(
      Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(1968, 1980)),
      2022
    ) == 12
  )
  assert(
    activeLength(
      Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003)),
      2022
    ) == 45
  )

  val artists = List(
    Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1981)),
    Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(1968, 1980)),
    Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
  )

  val searchRes1 = searchArtists(
    artists,
    List(
      SearchByGenre(List(Pop)),
      SearchByOrigin(List(Location("England"))),
      SearchByActiveYears(1950, 2022)
    )
  )
  assert(
    searchRes1 == List(
      Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
    )
  )

  val searchRes2 = searchArtists(
    artists,
    List(SearchByOrigin(List(Location("England"))), SearchByActiveYears(1950, 20222))
  )
  assert(
    searchRes2 == List(
      Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(1968, 1980)),
      Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
    )
  )

  val searchRes3 = searchArtists(artists, List(SearchByActiveYears(1981, 2003)))
  assert(
    searchRes3 == List(
      Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1981)),
      Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
    )
  )

  val searchRes4 = searchArtists(artists, List(SearchByOrigin(List(Location("U.S.")))))
  assert(
    searchRes4 == List(
      Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1981))
    )
  )

  val searchRes5 = searchArtists(artists, List.empty)
  assert(
    searchRes5 == List(
      Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(1981)),
      Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(1968, 1980)),
      Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
    )
  )
}

object chap07Playlist extends App {
  opaque type Artist = String
  object Artist {
    def apply(value: String): Artist            = value
    extension (artist: Artist) def name: String = artist
  }

  opaque type User = String
  object User {
    def apply(value: String): User          = value
    extension (user: User) def name: String = user
  }

  enum MusicGenre:
    case Classical
    case Nazrul
    case Latino
    case African

  import MusicGenre.*

  enum PlaylistKind:
    case CuratedByUser(user: User)
    case BasedOnArtist(artist: Artist)
    case BasedOnGenres(genres: Set[MusicGenre])

  import PlaylistKind.*

  case class Song(
      artist: Artist,
      title: String
  )

  case class Playlist(
      name: String,
      kind: PlaylistKind,
      songs: List[Song]
  )

  def gatherSongs(
      playlists: List[Playlist],
      artist: Artist,
      genre: MusicGenre
  ): List[Song] =
    playlists.flatMap(playlist =>
      playlist.kind match {
        case CuratedByUser(user) => playlist.songs.filter(_.artist == artist)
        case BasedOnArtist(playlistArtist) =>
          if (playlistArtist == artist) playlist.songs
          else List.empty
        case BasedOnGenres(genres) =>
          if (genres.contains(genre)) playlist.songs
          else List.empty
      }
    )

  val playlists: List[Playlist] = List(
    Playlist(
      "Anjum's favorite",
      CuratedByUser(User("Anjum")),
      List(
        Song(Artist("Cigarettes After Sex"), "Apocalypse"),
        Song(Artist("Cigarettes After Sex"), "Tejano Blue"),
        Song(Artist("Marine"), "Tidal Waves"),
        Song(Artist("ForgotName"), "Static on the radio")
      )
    ),
    Playlist(
      "Samudro's favorite",
      CuratedByUser(User("Samudro")),
      List(
        Song(Artist("Coldplay"), "Yellow"),
        Song(Artist("Coldplay"), "Scientist"),
        Song(Artist("Backstreet Boys"), "Show me the meaning")
      )
    ),
    Playlist(
      "Samudro's favorite",
      BasedOnArtist(Artist("Cigarettes After Sex")),
      List(
        Song(Artist("Cigarettes After Sex"), "Apocalypse"),
        Song(Artist("Cigarettes After Sex"), "Sunsetz"),
        Song(Artist("Cigarettes After Sex"), "Tejano Blue"),
        Song(Artist("Cigarettes After Sex"), "Bubble Gum")
      )
    ),
    Playlist(
      "Bos Robo favorite",
      BasedOnGenres(Set(Nazrul, African)),
      List(
        Song(Artist("Coke Studio"), "Bulbuli"),
        Song(Artist("Mathy"), "Ameyatchi"),
        Song(Artist("Cassav"), "Ou lè")
      )
    )
  )

  val songs = gatherSongs(playlists, Artist("Cigarettes After Sex"), African)
  println(songs)
}
