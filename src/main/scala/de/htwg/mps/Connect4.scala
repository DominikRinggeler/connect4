package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.{GameField, GameToken, HumanPlayer}
import de.htwg.mps.View.tui.Tui

object Connect4 extends App {

  GameField.initializeField(6,6)

  val tui = new Tui(new GameController)
  tui.processInputLine();
}
