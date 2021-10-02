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
        (g) => g.map(_ => Unset)
      )
    )
  }


  // We need to return a grid wher the coord is set to newColor
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

  // Get a random color from one of the candidates
  def getRandomColor(candidates: List[Color]): Color = {
    val colIndex = Math.floor(Math.random() * candidates.length).toInt
    candidates(colIndex)
  }

  // Generate a random grid with random colors
  def generateRandom(height: Int, width: Int, candidates: List[Color]): Canvas = {
    Canvas(
      numAcross = width,
      numDown = height,
      grid = Range(0, height).toList.map(
        (r: Int) => Range(0, width).toList.map(
          (c: Int) => getRandomColor(candidates)
        )
      )
    )
  }
}
