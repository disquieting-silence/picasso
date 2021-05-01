package tutorial.webapp.puzzle

sealed trait Color

final case object Black extends Color
final case object Red extends Color
final case object Blue extends Color
final case object Green extends Color
final case object White extends Color
final case object Yellow extends Color
final case object Pink extends Color
final case object Purple extends Color
final case object Orange extends Color
final case object Cyan extends Color
final case object Brown extends Color
final case object MultiColor extends Color

object Color {
  def getAll(): List[Color] = List(
    Black,
    Red,
    Blue,
    Green,
    White,
    Yellow,
    Pink,
    Purple,
    Orange,
    Cyan,
    Brown,
    MultiColor
  )

  def identify(colorString: String): Option[Color] = {
    System.out.println("identifying :  " + colorString)
    getAll().find(
      (c: Color) => colorString == c.toString().toLowerCase()
    )
  }
}