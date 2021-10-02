package tutorial.webapp.maze.app


import org.scalajs.dom
import org.scalajs.dom.document
import org.w3c.dom.events.EventListener

import tutorial.webapp.ui.Heading
import tutorial.webapp.ui.DomUtils
import tutorial.webapp.maze.ui.DirectionPanel
import tutorial.webapp.maze.core.Movement

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


  }
}