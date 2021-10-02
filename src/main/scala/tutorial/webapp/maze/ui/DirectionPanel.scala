package tutorial.webapp.maze.ui

import org.scalajs.dom.Element
import tutorial.webapp.ui.Elements

import tutorial.webapp.maze.core.Movement

object DirectionPanel {
  def build(onMove: Movement => Unit): Element = {
    Elements.container(
      "directions",
      List(
        Elements.button("left", "<-", _ => onMove(Movement.MoveLeft)),
        Elements.button("right", "->", _ => onMove(Movement.MoveRight)),
        Elements.button("up", "^", _ => onMove(Movement.MoveUp)),
        Elements.button("down", "v", _ => onMove(Movement.MoveDown))
      )

    )
  }

  
}
