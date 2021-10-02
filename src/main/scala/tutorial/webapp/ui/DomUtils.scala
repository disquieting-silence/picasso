package tutorial.webapp.ui

import org.scalajs.dom.Element

object DomUtils {

  def getAttribute(elm: Element, name: String): Option[String] = {
    val raw = elm.getAttribute(name)
    if (raw != null && raw.nonEmpty) Some(raw) else None
  }
}