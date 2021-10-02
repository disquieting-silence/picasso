package tutorial.webapp.alien

import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.document

object Delegation {

  def delegateOn[X](evt: dom.Event, extract: Element => Option[X], onEvent: (Element, X) => Unit) {
    val target: dom.raw.EventTarget = evt.target;
    target match {
      case e: Element => {
        extract(e).foreach(
          (x: X) => onEvent(e, x)
        )
      }
      case _ => None
    }
  }
}