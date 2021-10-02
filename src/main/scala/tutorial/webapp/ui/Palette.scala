package tutorial.webapp.ui

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.document

import tutorial.webapp.puzzle.Color
import tutorial.webapp.alien.DomUtils

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

  def getAllItems(paletteElement: Element): List[(Element, Color)] = {
    val all = paletteElement.querySelectorAll("[data-color]")
    val list = new scala.collection.mutable.ListBuffer[(Element, Color)]()
    System.out.println("list: " + all.length)
    for (i <- 0 to all.length) {
      all(i) match {
        case item: Element => {
          val optEntry = for {
            colorString <- DomUtils.getAttribute(item, "data-color")
            color <- Color.identify(colorString)
          } yield ((item, color))

          optEntry.foreach((e) => list.addOne(e))
        }
        case _ => ()
      }
    }
    list.toList
  }

  // Hacky DOM approach.
  def setActiveColor(paletteElement: Element, activeColor: Color): Unit = {
    val candidates = getAllItems(paletteElement)
    candidates.foreach({
      case (elm, color) => if (activeColor == color) {
        elm.classList.add("active-color")
      } else {
        elm.classList.remove("active-color")
      }
    })
  }

  def renderPalette(colors: List[Color], onChooseColor: Color => Unit): Element = {
    val div = document.createElement("div")
    div.classList.add("color-palette");

    val all = colors.map(renderColour)
    all.foreach((a) => div.appendChild(a));

    div.addEventListener("touchstart", (evt: dom.Event) => {
      Delegation.delegateOn(
        evt,
        (elm) => extractColor(colors, elm),
        onEvent = (_, color) => onChooseColor(color)
      )
    })
    div
  }
}