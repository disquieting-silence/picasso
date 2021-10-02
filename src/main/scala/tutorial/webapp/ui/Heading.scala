package tutorial.webapp.ui

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.document

final case class Heading(level: Int)

object Heading {

  def make(level: Heading, text: String) = {
    val h: Element = document.createElement(s"h${level.level}")
    h.textContent = text
    h
  }
}