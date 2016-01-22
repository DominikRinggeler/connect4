package de.htwg.mps.View.tui

import java.util.{InputMismatchException, Observable, Observer}
import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.HumanPlayer

/**
 * Created by dominikringgeler on 25.10.15.
 */
class Tui (var controller: GameController){

  def printField() = {
    println()
    for (i <- 0 until controller.grid.columns)
      print(" " + (i+1).toString)

    println()
    for (rowIndex <- (0 to controller.grid.rows-1)) {
      for (columnIndex <- 0 until controller.grid.columns) {

        val cell = controller.grid.getCell(rowIndex,columnIndex)
        val stringVar = if(cell!=null && cell.isSet) cell.gameToken.color else " "

        print("|" + stringVar)
      }
      print("|")
      println()
    }
    println()
  }

  def toInt(s: String): Option[Int] = {
    try {
      Some(s.toInt)
    } catch {
      case e: Exception => None
    }
  }

  def describe(x: Any, t:String):Int = x match {
    case n:Int if n > 3 && n <= 9 => n
    case z:Int if z < 4 => println("Minimal 4 " + t + " erlaubt! Bitte nochmals wählen..."); 0
    case z:Int if z > 9 => println("Maximal 9 " + t + " erlaubt! Bitte nochmals wählen..."); 0
    case _  => println("Keine Zahl! Bitte nochmals wählen..."); 0
  }

  def outputInitField(): Unit ={
    println("Sie spielen 4-Gewinnt!\n")

    println("Bitte wählen Sie die Spielfeldgröße: ")

    var rows=0
    var cols=0
    println("Reihen: ")
    do {
      var input = toInt(scala.io.StdIn.readLine()).getOrElse("None")
      rows = describe(input, "Reihen")
    } while (rows==0)

    println("Spalten: ")
    do {
      var input = toInt(scala.io.StdIn.readLine()).getOrElse("None")
      cols = describe(input, "Spalten")
    } while (cols==0)

    controller.newGrid(rows, cols)

    println("Bitte Name für Spieler 1 angeben:")
    controller.addPlayer(new HumanPlayer(1, readLine()))
    println("Bitte Name für Spieler 2 angeben:")
    controller.addPlayer(new HumanPlayer(2, readLine()))

    // actualize field
    printField()
  }

  def processInputLine() {
    var init = true

    while (true) {
      if(init) {
        outputInitField()
      }
      var win = false
      while (!win) {
        println(controller.getActualPlayer.color + " ist an der Reihe, bitte Spalte wählen...")
        win = makeTurnAndCheck()

        printField()
      }
      println("Das Spiel ist Aus! " + controller.getActualPlayer.color + " hat gewonnen.")
      init = restart(controller)
    }
  }

  def makeTurnAndCheck():Boolean = {
    var isCorrect = false
    var input = ""
    do {
      input = readLine()
      try {
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
    controller.conn4(input.toInt-1, controller.getActualPlayer)
  }

  def restart(controller: GameController):Boolean = {
    println("Nochmal spielen? [r-Revance | n-Neues Spiel | sonst-Beenden ] ...")
    var restart = readLine()
    restart match {
      case "r" => controller.reset; false
      case "n" => controller.newGrid(4, 4); true
      case _ => System.exit(0); false
    }
  }
}
