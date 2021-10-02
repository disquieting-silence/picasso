package tutorial.webapp.mimic.app

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExportTopLevel
import org.w3c.dom.events.EventListener
import tutorial.webapp.mimic.ui._
import tutorial.webapp.mimic.puzzle._

import tutorial.webapp.mimic.state.State
import tutorial.webapp.ui.Heading
import tutorial.webapp.ui.Elements
import tutorial.webapp.ui.DomUtils

object MimicApplication {
  def newGame(numDown: Int, numAcross: Int) : State = {
    val solution = Canvas.generateRandom(numDown, numAcross, Color.getAll())
    State(
      user = Canvas.clear(solution),
      solution = solution,
      activeColor = White
    )
  }


  var state: State = newGame(4, 4)


  def appendPar(targetNode: dom.Node, text: String): Unit = {
    val parNode = Elements.paragraph(text)
    DomUtils.append(targetNode, parNode)
  }

  
  def setupUI(): Unit = {
    val heading = Heading.make(Heading(1), "Mimic")
    heading.classList.add("banner")
    DomUtils.appendToBody(heading)

// hacky solution until I think about it. Just a katamari cell really.
    var hackyGrid: Option[Grid] = None
    var hackySolution: Option[Grid] = None

    var hackyPalette: Option[dom.raw.Element] = None
    var hackyProgress: Option[ProgressBar] = None

    def resizeGrid(original: Grid, newNumRows: Int, newNumCols: Int): Grid = {
      if (original.numAcross == newNumCols && original.numDown == newNumRows) {
        original
      } else {
        Grid.populateTbody(original.container, original.tbody, newNumRows, newNumCols)
      }
    }

    def renderCurrent() = {
      hackySolution.foreach((g) => Grid.renderIn(g, state.solution.grid))
      hackyGrid.foreach((g) => Grid.renderIn(g, state.user.grid));
      hackyPalette.foreach((p) => Palette.setActiveColor(p, state.activeColor))

      val percentSolved = Score.calculatePercent(state.user, state.solution)

      hackyProgress.foreach((pb) => {
        ProgressBar.setValue(pb, percentSolved)
      })
    }

    val grid = Grid.make(
      state.solution.numDown,
      state.solution.numAcross,
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

    val solutionGrid = Grid.make(state.solution.numDown, state.solution.numAcross, _ => ())
    Grid.renderIn(solutionGrid, state.solution.grid)
    hackySolution = Some(solutionGrid)

    val container = Elements.container(
      "container",
      List(solutionGrid.container, grid.container)
    )

    val outerContainer = Elements.container(
      "outer-container",
      List(container)
    )

    DomUtils.appendToBody(outerContainer);

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
    DomUtils.appendToBody(rawPalette)

    val progressBar = ProgressBar.make()
    hackyProgress = Some(progressBar)
    DomUtils.appendToBody(progressBar.container)

    val commandBar = CommandBar.build(
      (numDown, numAcross) => {
        state = newGame(numDown, numAcross)
        hackySolution = hackySolution.map(s => resizeGrid(s, numDown, numAcross))
        hackyGrid = hackyGrid.map(u => resizeGrid(u, numDown, numAcross))
        renderCurrent()
      }
    )

    DomUtils.appendToBody(commandBar)
    document.body.appendChild(commandBar)

    renderCurrent()
  }

}
