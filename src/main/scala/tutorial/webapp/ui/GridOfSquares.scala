package tutorial.webapp.ui

import org.scalajs.dom.Element
import org.scalajs.dom.document

final case class GridOfSquares(
  container: Element,
  tbody: Element,
  numDown: Int,
  numAcross: Int,
  cells: List[(Element, List[Element])]
)


object GridOfSquares {
  val MAX_TABLE_SIZE = 180

  def populateTbody(table: Element, tbody: Element, numRows: Int, numCols: Int, f: ((Int, Int, Element) => Unit)): GridOfSquares = {
    tbody.innerHTML = ""

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
            f(rowId, colId, td)
            td
          }
        )
        cellsInRow.foreach((c) => trow.appendChild(c))
        (trow, cellsInRow)
      }
    )

    trows.foreach((r) => tbody.appendChild(r._1));
    GridOfSquares(
      table, tbody, numRows, numCols, trows
    )
  }

  def extractCoords(cell: Element): Option[(Int, Int)] = for {
    row <- DomUtils.getAttribute(cell, "data-row").map(_.toInt)
    cell <- DomUtils.getAttribute(cell, "data-column").map(_.toInt)
  } yield (row, cell)

  def make(numRows: Int, numCols: Int, f: ((Int, Int, Element) => Unit)): GridOfSquares = {
    val table: Element = document.createElement("table")
    val tbody: Element = document.createElement("tbody");
    table.appendChild(tbody);
    // Now create the rows
    populateTbody(table, tbody, numRows, numCols, f)
  }

}