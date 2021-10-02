package tutorial.webapp.maze.ui

import tutorial.webapp.ui.GridOfSquares
import tutorial.webapp.maze.core.MazeSquare
import tutorial.webapp.maze.core.Maze

import org.scalajs.dom.Element

final case class MazeWorld(
  grid: GridOfSquares
)


object MazeWorld {
  def renderIn(grid: GridOfSquares, currentFocus: Maze.MazeFocus, colors: List[List[MazeSquare]], destFocus: Maze.MazeFocus) = {
    colors.zipWithIndex.foreach({
      case (row: List[MazeSquare], rowId: Int) => {
        row.zipWithIndex.foreach({
          case (cell: MazeSquare, cellId: Int) => {
            // Unsafe.
            val element = grid.cells(rowId)._2(cellId)
            element.setAttribute("data-maze-square", cell.toString().toLowerCase())
            if (currentFocus.row == rowId && currentFocus.col == cellId) {
              element.classList.add("current-maze-focus")
            } else {
              element.classList.remove("current-maze-focus")
            }

            if (destFocus.row == rowId && destFocus.col == cellId) {
              element.classList.add("dest-maze-focus")
            } else {
              element.classList.remove("dest-maze-focus")
            }
          }
        })
      }
    })
  }
 
}