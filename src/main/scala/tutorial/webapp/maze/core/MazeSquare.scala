package tutorial.webapp.maze.core

sealed trait MazeSquare { }

final case object SolidSquare extends MazeSquare
final case object PathSquare extends MazeSquare