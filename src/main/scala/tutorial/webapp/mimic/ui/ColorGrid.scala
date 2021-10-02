package tutorial.webapp.mimic.ui

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.document
import tutorial.webapp.ui.DomUtils
import tutorial.webapp.ui.Delegation
import tutorial.webapp.ui.GridOfSquares

import tutorial.webapp.mimic.puzzle.Color

object ColorGrid {
  val MAX_TABLE_SIZE = 180

  private def renderCell(cell: Element, color: Color) = {
    cell.setAttribute("data-color", color.toString().toLowerCase())
  }

  def populateTbody(table: Element, tbody: Element, numRows: Int, numCols: Int): GridOfSquares = {
    GridOfSquares.populateTbody(table, tbody, numRows, numCols, resizeCell(numRows))
  }

  def renderIn(grid: GridOfSquares, colors: List[List[Color]]) = {
    colors.zipWithIndex.foreach({
      case (row: List[Color], rowId: Int) => {
        row.zipWithIndex.foreach({
          case (cell: Color, cellId: Int) => {
            // Unsafe.
            val element = grid.cells(rowId)._2(cellId)
            renderCell(element, cell)
          }
        })
      }
    })
  }

  def resizeCell(numRows: Int)(row: Int, col: Int, td: Element): Unit = {
    val MAX_TABLE_SIZE = 180

    // ASSUMPTION: we assume that it is square for now.
    val cellSize = MAX_TABLE_SIZE / numRows

    td.setAttribute("style", s"width: ${cellSize}px; height: ${cellSize}px")
  }

  def make(numRows: Int, numCols: Int, onPaint: ((Int, Int)) => Unit): GridOfSquares = {
    val grid = GridOfSquares.make(numRows, numCols, resizeCell(numRows))

    grid.tbody.addEventListener("touchstart", (e: dom.Event) => {
      Delegation.delegateOn(
        e,
        GridOfSquares.extractCoords,
        (_elm: Element, coords: (Int, Int)) => onPaint(coords)
      )
    })

    grid
  }
}