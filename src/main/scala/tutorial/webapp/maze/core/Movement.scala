package tutorial.webapp.maze.core

import scala.util.Random

sealed trait Movement { } 

object Movement {
  final case object MoveLeft extends Movement
  final case object MoveRight extends Movement
  final case object MoveUp extends Movement
  final case object MoveDown extends Movement


  // TODO: Keep a consistent random seed.
  def getRandom() = {
    val r = Math.random()
    if (r <= 0.25) MoveLeft
    else if (r <= 0.5) MoveRight
    else if (r <= 0.75) MoveUp
    else MoveDown
  }

  def getRandomOrder(): List[Movement] = {
    Random.shuffle(
      List(MoveLeft, MoveRight, MoveUp, MoveDown)
    )
  }
}

