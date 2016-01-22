package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model._
import de.htwg.mps.View.gui.Gui
import de.htwg.mps.View.tui.Tui

object Connect4 {

  def main(args: Array[String]) = {

    val grid = new Grid(6,7)
    val controller = new GameController(grid)

    val ui = new Gui(controller)
    ui.main(args)

    val tui = new Tui(controller)
    tui.processInputLine()
  }
}
