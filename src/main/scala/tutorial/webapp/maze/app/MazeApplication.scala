package tutorial.webapp.maze.app


import org.scalajs.dom
import org.scalajs.dom.document
import org.w3c.dom.events.EventListener

import tutorial.webapp.ui.Heading
import tutorial.webapp.ui.DomUtils
import tutorial.webapp.maze.ui.DirectionPanel
import tutorial.webapp.maze.core.Movement
import tutorial.webapp.maze.core.Maze
import tutorial.webapp.ui.GridOfSquares
import tutorial.webapp.maze.ui.MazeWorld

object MazeApplication {

  def setupUI(): Unit = {
    val heading = Heading.make(Heading(1), "Maze")
    heading.classList.add("banner")
    DomUtils.appendToBody(heading)

    val directionPanel = DirectionPanel.build({
      case Movement.MoveLeft => ()
      case Movement.MoveRight => ()
      case Movement.MoveDown => ()
      case Movement.MoveUp => ()
    })
    DomUtils.appendToBody(directionPanel)

    val maze = Maze.createRandom(10, 10)

    val mazeWorld = GridOfSquares.make(10, 10, (_, _, _) => ())

    MazeWorld.renderIn(mazeWorld, maze._2.squares)
    DomUtils.appendToBody(mazeWorld.container)




  }
}