import cats.effect.{IO, IOApp}
import cats.effect.unsafe.implicits.global

object chap08 extends IOApp.Simple {
  val dieCast: IO[Int] = IO.pure(23)
  println(dieCast.unsafeRunSync())

  def run: IO[Unit] =
    dieCast.flatMap(IO.println(_))
}
