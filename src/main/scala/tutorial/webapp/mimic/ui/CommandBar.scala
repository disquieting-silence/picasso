package tutorial.webapp.mimic.ui

import org.scalajs.dom.raw.Element
import org.scalajs.dom.{Event, document}

object CommandBar {
  def build(onNew: (Int, Int) => Unit): Element = {
    val div = document.createElement("div")
    div.classList.add("command-bar")

    val button4x4 = makeNewPuzzleButton("4x4", () => onNew(4, 4))
    val button5x5 = makeNewPuzzleButton("5x5", () => onNew(5, 5))
    val button6x6 = makeNewPuzzleButton("6x6", () => onNew(6, 6))

    div.appendChild(button4x4)
    div.appendChild(button5x5)
    div.appendChild(button6x6)
    div

  }

  private def makeNewPuzzleButton(text: String, onNew: () => Unit): Element = {
    val button = document.createElement("button")
    button.innerText = text
    button.addEventListener("touchstart", (evt: Event) => {
      onNew()
    })
    return button
  }
}
