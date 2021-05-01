package tutorial.webapp.ui

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.document

import tutorial.webapp.puzzle.Color

object Palette {

  def renderColour(color: Color): Element = {
    val col = document.createElement("div")
    List[String](
      "color",
      "color-" + color.toString().toLowerCase()
    ).foreach((c) => col.classList.add(c))

    col.setAttribute("data-color", color.toString().toLowerCase())
    col
  }

  def extractColor(colors: List[Color], targetElement: Element): Option[Color] = {
    val colorOfEl = targetElement.getAttribute("data-color")
    if (colorOfEl.nonEmpty) {
      colors.find(
        (c: Color) => colorOfEl == c.toString().toLowerCase()
      )
    } else {
      None
    }
  }

  def renderPalette(colors: List[Color], onChooseColor: Color => Unit): Element = {
    val div = document.createElement("div")
    div.classList.add("color-palette");

    val all = colors.map(renderColour)
    all.foreach((a) => div.appendChild(a));

    div.addEventListener("click", (evt: dom.Event) => {
      Delegation.delegateOn(
        evt,
        (elm) => extractColor(colors, elm),
        onEvent = (_, color) => onChooseColor(color)
      )
    })
    div
  }
}