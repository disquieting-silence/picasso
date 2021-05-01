package tutorial.webapp.puzzle

final case class Canvas(
  numAcross: Int,
  numDown: Int,
  grid: List[
    List[Color]
  ]
)

object Canvas {
  def clear(canvas: Canvas): Canvas = {
    canvas.copy(
      grid = canvas.grid.map(
        (g) => g.map(_ => White)
      )
    )
  }

  def setColor(canvas: Canvas, coord: (Int, Int), newColor: Color) = {
    canvas.copy(
      grid = canvas.grid.zipWithIndex.map({
        case (row: List[Color], rowId: Int) => {
          if (rowId == coord._1) {
            row.zipWithIndex.map({
              case (existingColor: Color, cellId: Int) => {
                if (cellId == coord._2) {
                  newColor
                } else {
                  existingColor
                }
              }
            })
          } else {
            row
          }
        }
      })
    )
  }
}
