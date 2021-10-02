package tutorial.webapp.mimic.state

import tutorial.webapp.mimic.puzzle.Color
import tutorial.webapp.mimic.puzzle.Canvas


final case class State(
  user: Canvas,
  solution: Canvas,
  activeColor: Color
)