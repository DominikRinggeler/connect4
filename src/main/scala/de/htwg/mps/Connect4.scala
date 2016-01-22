package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model._
import de.htwg.mps.View.gui.Gui
import de.htwg.mps.View.tui.Tui

object Connect4 {

  def main(args: Array[String]) = {

    var grid = new Grid(1, 1)

    var g = grid.getRowIndex(0)

    //val gui = true
    val gui = false
    val controller = new GameController(new Grid(6, 7))

    if (gui) {
      val ui = new Gui(controller)
      ui.main(args)
    }
    else {
      val ui = new Gui(controller)
      ui.main(args)
      val tui = new Tui(controller)
      tui.processInputLine()
    }
  }
}
