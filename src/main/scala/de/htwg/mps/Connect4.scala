package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model._
import de.htwg.mps.View.gui.Gui
import de.htwg.mps.View.tui.Tui

import scala.io.StdIn._

object Connect4 {

  val grid = new Grid(6,7)
  val controller = new GameController(grid)

  controller.addPlayer(new HumanPlayer(2,"Spieler 2"))
  controller.addPlayer(new HumanPlayer(1,"Spieler 1"))

  controller.gameStatus = GameStatus.PLAYING
  val ui = new Gui(controller)
  val tui = new Tui(controller)

  def main(args: Array[String]) {
    ui.main(args)

    println(controller.getActualPlayer.color + " ist an der Reihe, bitte Spalte wählen...")
    while (tui.processInputLine(readLine())) {}
  }
}
