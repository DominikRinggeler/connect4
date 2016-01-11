package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model._
import de.htwg.mps.View.gui.Gui
import de.htwg.mps.View.tui.Tui

object Connect4 {
  def main(args: Array[String]) = {

    val gui = true
    //val gui = false

    val controller = new GameController(new Grid(6,7))

    if (gui) {

      val ui = new Gui(controller)
      ui.main(args)
    }
    else{
      val tui = new Tui(new GameController(new Grid(6,7)))
      tui.processInputLine();
    }
  }
}
