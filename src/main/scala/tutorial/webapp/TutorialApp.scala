package tutorial.webapp

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExportTopLevel
import org.w3c.dom.events.EventListener

import tutorial.webapp.mimic.app.MimicApplication

object TutorialApp {

  def main(args: Array[String]): Unit = {
    document.addEventListener(
      "DOMContentLoaded",
      { (e: dom.Event) =>
        MimicApplication.setupUI();
      }
    )
  }

}
