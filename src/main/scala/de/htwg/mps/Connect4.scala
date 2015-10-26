package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.{GameField, GameToken, HumanPlayer}
import de.htwg.mps.View.tui.Tui

object Connect4 extends App {
  println("Hello, you are playing connect4")

  val player = new HumanPlayer(1,"Max")
  val token = new GameToken(player,player.color)

  GameField.initializeField(2,2)

  val tui = new Tui(new GameController)
  tui.printField()

  GameField.setFieldToken(1,token)
  GameField.setFieldToken(3,token)
  GameField.setFieldToken(1,token)

  var gtoken = GameField.getFieldToken(1,1)
  tui.printField()

}
