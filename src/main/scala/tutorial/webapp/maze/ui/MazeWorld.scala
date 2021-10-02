package tutorial.webapp.maze.ui

import tutorial.webapp.ui.GridOfSquares
import tutorial.webapp.maze.core.MazeSquare

import org.scalajs.dom.Element

final case class MazeWorld(
  grid: GridOfSquares
)


object MazeWorld {
  private def renderCell(cell: Element, sq: MazeSquare) = {
    cell.setAttribute("data-maze-square", sq.toString().toLowerCase())
  }

  def renderIn(grid: GridOfSquares, colors: List[List[MazeSquare]]) = {
    colors.zipWithIndex.foreach({
      case (row: List[MazeSquare], rowId: Int) => {
        row.zipWithIndex.foreach({
          case (cell: MazeSquare, cellId: Int) => {
            // Unsafe.
            val element = grid.cells(rowId)._2(cellId)
            renderCell(element, cell)
          }
        })
      }
    })
  }
 
}