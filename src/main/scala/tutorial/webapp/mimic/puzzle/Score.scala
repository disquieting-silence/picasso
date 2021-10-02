package tutorial.webapp.mimic.puzzle

object Score {
  def calculatePercent(user: Canvas, solution: Canvas) = {
    // Use immutable things.
    val overallCorrect = user.grid.zipWithIndex.foldRight(0)({
      case ((row, rowId), acc) => {
        acc + row.zipWithIndex.foldRight(0)({
          case ((cell, colId), acc2) => {
            val numCorrect = cell == solution.grid(rowId)(colId)
            if (numCorrect) acc2 + 1 else acc2
          }
        })
      }
    })

    Math.ceil(((overallCorrect + 0.0) / (user.numAcross * user.numDown)) * 100).toInt
  }
}