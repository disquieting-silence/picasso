package tutorial.webapp.maze.core

sealed trait Movement { } 

object Movement {
  final case object MoveLeft extends Movement
  final case object MoveRight extends Movement
  final case object MoveUp extends Movement
  final case object MoveDown extends Movement
}

