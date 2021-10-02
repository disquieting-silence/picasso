package tutorial.webapp.mimic.ui

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.document
import tutorial.webapp.ui.DomUtils
import tutorial.webapp.ui.Delegation

import tutorial.webapp.mimic.puzzle.Color

final case class Grid(
  container: Element,
  tbody: Element,
  numDown: Int,
  numAcross: Int,
  cells: List[(Element, List[Element])]
)

object Grid {
  val MAX_TABLE_SIZE = 180

  private def renderCell(cell: Element, color: Color) = {
    cell.setAttribute("data-color", color.toString().toLowerCase())
  }

  def populateTbody(table: Element, tbody: Element, numRows: Int, numCols: Int): Grid = {
    tbody.innerHTML = ""

    // ASSUMPTION: we assume that it is square for now.
    val cellSize = MAX_TABLE_SIZE / numRows

    // So this is going to have to redraw the grid's contents.
    val trows: List[(Element, List[Element])] = Range(0, numRows).toList.map(
      (rowId: Int) => {
        val trow = document.createElement("tr")
        val cellsInRow = Range(0, numCols).toList.map(
          (colId: Int) => {
            val td = document.createElement("td");
            td.classList.add("grid-cell")
            td.setAttribute("data-row", rowId.toString())
            td.setAttribute("data-column", colId.toString())
            td.setAttribute("style", s"width: ${cellSize}px; height: ${cellSize}px")
            td
          }
        )
        cellsInRow.foreach((c) => trow.appendChild(c))
        (trow, cellsInRow)
      }
    )

    trows.foreach((r) => tbody.appendChild(r._1));
    Grid(
      table, tbody, numRows, numCols, trows
    )
  }

  def renderIn(grid: Grid, colors: List[List[Color]]) = {
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

  def extractCoords(cell: Element): Option[(Int, Int)] = for {
    row <- DomUtils.getAttribute(cell, "data-row").map(_.toInt)
    cell <- DomUtils.getAttribute(cell, "data-column").map(_.toInt)
  } yield (row, cell)

  def make(numRows: Int, numCols: Int, onPaint: ((Int, Int)) => Unit): Grid = {
    val table: Element = document.createElement("table")

    val tbody: Element = document.createElement("tbody");

    table.appendChild(tbody);

    tbody.addEventListener("touchstart", (e: dom.Event) => {
      Delegation.delegateOn(
        e,
        extractCoords,
        (_elm: Element, coords: (Int, Int)) => onPaint(coords)
      )
    })

    // Now create the rows
    populateTbody(table, tbody, numRows, numCols)
  }
}