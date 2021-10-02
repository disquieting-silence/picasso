package tutorial.webapp.ui

import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.Element
import org.scalajs.dom.Node

object Elements {

  def paragraph(text: String): Element = {
    val parNode = document.createElement("p")
    parNode.textContent = text
    parNode
  }

  def container(className: String, children: List[Node]) = {
    val parent = document.createElement("div")
    parent.classList.add(className)
    children.foreach((c) => DomUtils.append(parent, c))
    parent
  }

}