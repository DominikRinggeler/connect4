package de.htwg.mps.View.tui

import java.util.{InputMismatchException, Observable, Observer}

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.{HumanPlayer, GameField}

/**
 * Created by dominikringgeler on 25.10.15.
 */
class Tui (var controller: GameController) extends Observer{

  override def update(o: Observable, arg: scala.Any): Unit = ???

  def printField() = {

    println()
    for (i <- 0 until GameField.columns)
      print(" " + (i+1).toString)

    println()
    for (rowIndex <- (0 to GameField.rows-1).reverse) {
      for (columnIndex <- 0 until GameField.columns) {

        var gtoken = GameField.getFieldToken(rowIndex,columnIndex)
        var stringVar = if(gtoken==null) " " else gtoken.color

        print("|" + stringVar)
      }
      print("|")
      println()
    }
    println()
  }

  def processInputLine(): Unit = {

    println("Sie spielen 4-Gewinnt!\n")

    println("Bitte Name für Spieler 1 angeben:")
    controller.addPlayer(1, readLine())

    println("Bitte Name für Spieler 2 angeben:")
    controller.addPlayer(2, readLine())


    // actualize field
    printField()
    var win = false

    while (!win) {

      // Player 1
      println(controller.getName + " ist an der Reihe, bitte Spalte wählen...")
      win = makeTurnAndCheck()

      // actualize field
      printField()

    }
    println("Das Spiel ist Aus! "+controller.getName + " hat gewonnen.")

  }

  def makeTurnAndCheck():Boolean = {
    var win = false
    var isCorrect = false
    do {
      var input = readLine()
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
      if(isCorrect)
        win = controller.checkConnectFour(input.toInt-1)
    }while (!isCorrect)
    win
  }
}
