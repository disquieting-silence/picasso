package tutorial.webapp.state

import tutorial.webapp.puzzle.Color
import tutorial.webapp.puzzle.Canvas


final case class State(
  user: Canvas,
  solution: Canvas,
  activeColor: Color
)