package tutorial.webapp.maze.app


import org.scalajs.dom
import org.scalajs.dom.document
import org.w3c.dom.events.EventListener

import tutorial.webapp.ui.Heading
import tutorial.webapp.ui.DomUtils
import tutorial.webapp.ui.Elements
import tutorial.webapp.maze.ui.DirectionPanel
import tutorial.webapp.maze.core.Movement
import tutorial.webapp.maze.core.Maze
import tutorial.webapp.ui.GridOfSquares
import tutorial.webapp.maze.ui.MazeWorld
import tutorial.webapp.maze.core.PathSquare

object MazeApplication {

  def setupUI(): Unit = {
    val heading = Heading.make(Heading(1), "Maze")
    heading.classList.add("banner")
    DomUtils.appendToBody(heading)

    val mazeWorld = GridOfSquares.make(5, 10, (_, _, _) => ())
    var state: Option[(Maze.MazeFocus, Maze, Maze.MazeFocus)] = None

    def createNewMaze(pathLength: Int) = {
      val newMaze = Maze.createRandom(5, 10, pathLength)
      state = Some(newMaze)
      render()
    }

    def render() = {
      state.foreach({
        case (current, world, finish) => {
          document.body.classList.remove("maze-victory") 
          MazeWorld.renderIn(mazeWorld, current, world.squares, finish)
        }
      })
    }


    def tryMoveUpdate(move: Movement): Option[(Maze.MazeFocus, Maze, Maze.MazeFocus)] = {
      state.flatMap({
        case (current, world, finish) => {
          val optNext = Maze.move(current, world, move, PathSquare)
          optNext.map((next) => (next, world, finish))
        }
      })
    }

    val directionPanel = DirectionPanel.build(
      (move: Movement) => tryMoveUpdate(move).foreach(
        (newState) => {
          state = Some(newState)

          
          render()

          // check for victory condition
          if (newState._1.col == newState._3.col && newState._1.row == newState._3.row) {
            document.body.classList.add("maze-victory") 
          }
        }
      )
    )


    val newMazePanel = Elements.container(
      "new-mazes",
      List(3, 5, 8, 10, 15).map((x) => Elements.button(
        s"maze-${x}", x.toString(), _ => createNewMaze(x)
      ))
    )

    
    val commandPanel = Elements.container(
      "commands",
      List(
        newMazePanel,
        directionPanel
      )
    )

    DomUtils.appendToBody(commandPanel)

    

    createNewMaze(5)

    render()

    val container = Elements.container(
      "maze-container",
      List(mazeWorld.container)
    )

    DomUtils.appendToBody(container)




  }
}