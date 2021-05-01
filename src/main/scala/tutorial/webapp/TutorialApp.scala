package tutorial.webapp

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExportTopLevel
import org.w3c.dom.events.EventListener
import tutorial.webapp.ui._
import tutorial.webapp.puzzle._

import tutorial.webapp.state.State

object TutorialApp {
  val solution = Canvas(
    3,
    3,
    List(
      List(White, White, Red),
      List(Red, Blue, Green),
      List(White, White, Black)
    )
  )

  var state: State = State(
    user = Canvas.clear(solution),
    solution = solution,
    activeColor = White
  )

  def main(args: Array[String]): Unit = {
    document.addEventListener(
      "DOMContentLoaded",
      { (e: dom.Event) =>
        setupUI();
      }
    )
  }

  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = document.createElement("p")
    parNode.textContent = text
    targetNode.appendChild(parNode)
  }

  def setupUI(): Unit = {
    val heading = Heading.make(Heading(1), "Picasso")
    heading.classList.add("banner")
    document.body.appendChild(heading)

// hacky solution until I think about it. Just a katamari cell really.
    var hackyGrid: Option[Grid] = None

    var hackyPalette: Option[dom.raw.Element] = None

    def renderCurrent() = {
      hackyGrid.foreach((g) => Grid.renderIn(g, state.user.grid));
      hackyPalette.foreach((p) => Palette.setActiveColor(p, state.activeColor))
    }

    val grid = Grid.make(
      solution.numDown,
      solution.numAcross,
      ({
        case (rowNum, colNum) => {
          state = state.copy(
            user =
              Canvas.setColor(state.user, (rowNum, colNum), state.activeColor)
          )

          renderCurrent()
          ()
        }
      })
    )

    hackyGrid = Some(grid)

    val solutionGrid = Grid.make(solution.numDown, solution.numAcross, _ => ())
    Grid.renderIn(solutionGrid, state.solution.grid)

    val container = document.createElement("div")
    container.classList.add("container")
    container.appendChild(solutionGrid.container)
    container.appendChild(grid.container)

    val outerContainer = document.createElement("div")
    outerContainer.classList.add("outer-container")
    outerContainer.appendChild(container)

    document.body.appendChild(outerContainer)

    val rawPalette = Palette.renderPalette(
      Color.getAll(),
      onChooseColor = (activeColor: Color) => {
        state = state.copy(
          activeColor = activeColor
        )
        renderCurrent()
      }
    )

    hackyPalette = Some(rawPalette)
    document.body.appendChild(rawPalette)

    renderCurrent()
  }

}
