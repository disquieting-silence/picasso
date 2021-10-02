package tutorial.webapp.mimic.ui

import org.scalajs.dom.Element
import org.scalajs.dom.{Event, document}
import tutorial.webapp.ui.Elements

object CommandBar {
  def build(onNew: (Int, Int) => Unit): Element = {
    val button4x4 = makeNewPuzzleButton("4x4", () => onNew(4, 4))
    val button5x5 = makeNewPuzzleButton("5x5", () => onNew(5, 5))
    val button6x6 = makeNewPuzzleButton("6x6", () => onNew(6, 6))

    Elements.container(
      "command-bar",
      List(button4x4, button5x5, button6x6)
    )
  }

  private def makeNewPuzzleButton(text: String, onNew: () => Unit): Element = {
    Elements.button(
      "new-puzzle-" + text,
      text,
      (evt: Event) => onNew()
    )
  }
}
