package tutorial.webapp.alien

import org.scalajs.dom.Element

object DomUtils {

  def getAttribute(elm: Element, name: String): Option[String] = {
    val raw = elm.getAttribute(name)
    System.out.println("raw: " + raw)
    if (raw != null && raw.nonEmpty) Some(raw) else None
  }
}