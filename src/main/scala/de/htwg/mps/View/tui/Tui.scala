package de.htwg.mps.View.tui

import java.util.{Observable, Observer}

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.GameField

/**
 * Created by dominikringgeler on 25.10.15.
 */
class Tui (var controller: GameController) extends Observer{

  override def update(o: Observable, arg: scala.Any): Unit = ???

  def printField() = {

    println()
    println(" 1 2 3 4 5 6")
    for (rowIndex <- (0 to GameField.rows).reverse) {
      for (columnIndex <- 0 until GameField.columns) {

        var gtoken = GameField.getFieldToken(rowIndex,columnIndex)
        var stringVar = if(gtoken==null) " " else gtoken.color

        print("|" + stringVar)
      }
      print("|")
      println()
    }
  }

  def processInputLine(): Unit ={

    println("Hello, you are playing connect4")

    println("Bitte Name für Spieler 1 angeben:")
    controller.addPlayer(1,readLine())

    println("Bitte Name für Spieler 2 angeben:")
    controller.addPlayer(2,readLine())

    var players = controller.players
    while(true){
      println()
    }
  }
}
