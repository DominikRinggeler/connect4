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
    for (rowIndex <- 0 until GameField.rows) {
      for (columnIndex <- 0 until GameField.columns) {

        var gf = GameField
        var gtoken = GameField.getFieldToken(1,1)
        var stringVar = if(gtoken==null) " " else gtoken.color

        print("|" + stringVar)
      }
      print("|")
      println()
    }
  }
  def insertToken(){
  }


}
