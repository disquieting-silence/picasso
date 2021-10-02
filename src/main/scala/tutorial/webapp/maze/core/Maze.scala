package tutorial.webapp.maze.core

final case class Maze(
  numRows: Int,
  numCols: Int,
  squares: List[List[MazeSquare]]
) 

object Maze {
  final case class MazeFocus(row: Int, col: Int)



  def getDelta(movement: Movement) = movement match {
    case Movement.MoveLeft => MazeFocus(0, -1)
    case Movement.MoveRight => MazeFocus(0, +1)
    case Movement.MoveUp => MazeFocus(-1, 0)   
    case Movement.MoveDown => MazeFocus(+1, 0)
  }

  def isExplored(nextFocus: MazeFocus, maze: Maze): Boolean = {
    maze.squares(nextFocus.row)(nextFocus.col) == PathSquare
  }

  def createRandom(numRows: Int, numCols: Int): (MazeFocus, Maze) = {
    // start from (0, 0), go in a direction
    val current: (MazeFocus, Maze) = (
      MazeFocus(0, 0),
      Maze.empty(numRows, numCols)
    )

    def move(focus: MazeFocus, maze: Maze, movement: Movement): Option[MazeFocus] = {
      // Identify how much the position will change and see if it's a valid result
      // (within bounds)
      val delta = getDelta(movement);
      val optNextFocus = Some(
        focus.copy(row = focus.row + delta.row, col = focus.col + delta.col)
      ).filter(
        (f) => f.col >= 0 && f.col < numCols && f.row >= 0 && f.row < numRows
      )

      // Ensure the new position is not explored yet.
      optNextFocus.filter(
        (nextFocus: MazeFocus) => !isExplored(nextFocus, maze)
      )
    }

    def excavate(before: (MazeFocus, Maze), movement: Movement): Option[(MazeFocus, Maze)] = {
      // Try to excavate in the given direction
      val optNextFocus: Option[MazeFocus] = move(before._1, before._2, movement)
      optNextFocus.map(
        (nextFocus: MazeFocus) => {
          val nextMaze = before._2.copy(
            squares = before._2.squares.zipWithIndex.map({
              case (row, rowId) => {
                row.zipWithIndex.map({
                  case (col, colId) => {
                    if (nextFocus.col == colId && nextFocus.row == rowId) {
                      PathSquare
                    } else {
                      col
                    }
                  }
                })
              }
            })
          )
          (nextFocus, nextMaze)
        }
      )
    }

    // Excavate until desired path length
    def excavateUntil(before: (MazeFocus, Maze), stepsLeft: Int): Option[(MazeFocus, Maze)] = {
      if (stepsLeft == 0) {
        Some(before)
      } else {
        val movements = Movement.getRandomOrder()

        movements.to(LazyList).map(
          (movement: Movement) => {
            excavate(before, movement).flatMap({
              case (nextFocus, nextMaze) => excavateUntil((nextFocus, nextMaze), stepsLeft - 1)
            })
          }
        ).collectFirst({
          case Some(d) => d
        })
      }
    }

    excavateUntil(current, 5).get
  }

  def empty(numRows: Int, numCols: Int): Maze = {
    Maze(
      numRows = numRows,
      numCols = numCols,
      squares = Range(0, numRows).toList.map((row) => {
        Range(0, numCols).toList.map(_ => SolidSquare)
      })
    )
  }
}