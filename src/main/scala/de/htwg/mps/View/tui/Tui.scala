package de.htwg.mps.View.tui

import java.util.{InputMismatchException, Observable, Observer}
import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.HumanPlayer

/**
 * Created by dominikringgeler on 25.10.15.
 */
class Tui (var controller: GameController) extends Observer{

  override def update(o: Observable, arg: scala.Any): Unit = ???


  def printField() = {
    controller.printField
  }

    /*
    println()
    for (i <- 0 until controller.grid.columns)
      print(" " + (i+1).toString)

    println()
    for (rowIndex <- (0 to controller.grid.rows-1).reverse) {
      for (columnIndex <- 0 until controller.grid.columns) {

        var cell = controller.grid.getCell(rowIndex,columnIndex)
        var stringVar = if(cell==null) " " else cell.gameToken.color

        print("|" + stringVar)
      }
      print("|")
      println()
    }
    println()
  }
  */

  def processInputLine() {

    println("Sie spielen 4-Gewinnt!\n")
    println("Bitte Name für Spieler 1 angeben:")
    controller.addPlayer(1, readLine())
    println("Bitte Name für Spieler 2 angeben:")
    controller.addPlayer(2, readLine())

    // actualize field
    printField()
    var win = false

    while (!win) {
      println(controller.getActualPlayer.color + " ist an der Reihe, bitte Spalte wählen...")
      win = makeTurnAndCheck()

      // actualize field
      printField()
    }
    println("Das Spiel ist Aus! "+controller.getActualPlayer.color + " hat gewonnen.")
  }

  def makeTurnAndCheck():Boolean = {
    var isCorrect = false
    var input = ""
    do {
      input = readLine()
      try {
        Some(input.toInt)
        isCorrect = controller.makeTurn(input.toInt-1)
        if (isCorrect){
          println("Token erfolgreich gesetzt!")
        }
        else{
          println("Token konnte nicht gesetzt werden! Bitte neue Eingabe wählen.")
        }
      } catch {
        case e: Exception => println("Die Eingabe ist keine korrekte Spalte! Bitte Spalte wählen...")
      }
    }while (!isCorrect)
    return controller.conn4(input.toInt-1, controller.getActualPlayer)
  }
}
