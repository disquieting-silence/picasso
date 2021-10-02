package tutorial.webapp.mimic.ui


import org.scalajs.dom
import org.scalajs.dom.raw.Element
import org.scalajs.dom.document

final case class ProgressBar(container: Element, bar: Element)

object ProgressBar {

  def setValue(pb: ProgressBar, value: Int) = {
    // Not ideal. where as the CSS APIs ?
    pb.bar.setAttribute("style", "width: " + value + "%")
  }


  def make(): ProgressBar = {
    val container = document.createElement("div")
    container.classList.add("progressbar")

    val bar = document.createElement("div");
    bar.classList.add("progress");

    container.appendChild(bar)
    ProgressBar(container, bar)
  }
}