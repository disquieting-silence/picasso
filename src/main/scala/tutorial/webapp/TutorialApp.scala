package tutorial.webapp

import org.scalajs.dom
import org.scalajs.dom.document
import scala.scalajs.js.annotation.JSExportTopLevel
import org.w3c.dom.events.EventListener

import tutorial.webapp.mimic.app.MimicApplication
import tutorial.webapp.maze.app.MazeApplication

object TutorialApp {

  def main(args: Array[String]): Unit = {
    document.addEventListener(
      "DOMContentLoaded",
      {
        (e: dom.Event) => {
          if (true) {
            MimicApplication.setupUI();
          } else {
            MazeApplication.setupUI();
          }
        }
      }
    )
  }

}
