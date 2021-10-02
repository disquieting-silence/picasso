package tutorial.webapp.ui

import org.scalajs.dom.Element
import org.scalajs.dom.Node
import org.scalajs.dom.document

object DomUtils {

  def getAttribute(elm: Element, name: String): Option[String] = {
    val raw = elm.getAttribute(name)
    if (raw != null && raw.nonEmpty) Some(raw) else None
  }

  def append(parent: Node, child: Node) {
    parent.appendChild(child)
  }

  def appendToBody(child: Node) {
    val body = document.body
    append(body, child)
  }
}