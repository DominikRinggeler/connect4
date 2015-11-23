package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.{GameField, GameToken, HumanPlayer}
import de.htwg.mps.View.gui.Gui
import de.htwg.mps.View.tui.Tui

object Connect4 {
  def main(args: Array[String]) = {

    val gui = true

    val controller = new GameController
    val rows = 8
    val columns = 8
    GameField.initializeField(rows, columns)

    if (gui) {

      val ui = Gui
      ui.setController(controller)
      ui.main(args)
    }
    else{
      val tui = new Tui(new GameController)
      tui.processInputLine();
    }
  }
}
