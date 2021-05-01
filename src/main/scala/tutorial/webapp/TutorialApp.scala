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
    val button = document.createElement("button")
    button.textContent = "Click me!"
    button.addEventListener(
      "click",
      { (e: dom.MouseEvent) =>
        addClickedMessage()
      }
    )
    document.body.appendChild(
      Heading.make(Heading(1), "Picasso")
    )

// hacky solution until I think about it. Just a katamari cell really.
var hackyGrid: Option[Grid] = None

    val grid = Grid.make(
      solution.numDown,
      solution.numAcross,
      ({
        case (rowNum, colNum) => {
          state = state.copy(
            user =
              Canvas.setColor(state.user, (rowNum, colNum), state.activeColor)
          )

          hackyGrid.foreach((g) => Grid.renderIn(g, state.user.grid));
          ()
        }
      })
    )

    hackyGrid = Some(grid)

    val solutionGrid = Grid.make(solution.numDown, solution.numAcross, _ => ())
    Grid.renderIn(solutionGrid, state.solution.grid)

    val container = document.createElement("div")
    container.appendChild(solutionGrid.container)
    container.appendChild(grid.container)

    val outerContainer = document.createElement("div")
    outerContainer.appendChild(container)

    document.body.appendChild(outerContainer)

    document.body.appendChild(
      Palette.renderPalette(
        List(Black, Red, Green, Blue),
        onChooseColor = (activeColor: Color) => {
          state = state.copy(
            activeColor = activeColor
          )
        }
      )
    )

    document.body.appendChild(button)

    appendPar(document.body, "Hello World")
  }

  @JSExportTopLevel("addClickedMessage")
  def addClickedMessage(): Unit = {
    appendPar(document.body, "You clicked the button!")
  }
}
