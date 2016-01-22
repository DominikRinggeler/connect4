package de.htwg.mps.View.tui

import java.util.{InputMismatchException, Observable, Observer}
import de.htwg.mps.Controller.{ChangeField, StartGame, MakeTurn, GameController}
import de.htwg.mps.Model.{GameStatus, HumanPlayer}

import scala.swing.Reactor

/**
 * Created by dominikringgeler on 25.10.15.
 */
class Tui (var controller: GameController) extends Reactor{
  listenTo(controller)

  reactions += {
    case e: ChangeField => outputPrintField()
  }

  def processInputLine(input: String) = {
    var continue = true
    input match {
      case "n" => controller.reset;
      case "e" => continue = false
      case "p2" => controller.set2Player()
      case "p3" => controller.set3Player()
      case _ => {
        input.toList.filter(c => c != ' ').map(c => c.toString.toInt) match {
          case i :: Nil => i match {
            case i if (i > 0 && i <= 9) => makeTurnAndCheck(i)
            case i if (i > 9 || i <= 0) => print("Nicht gültig")
          }
          case _ =>
        }
      }
    }
    continue
  }

  def gameOver(): Unit ={
    controller.gameStatus match {
      case GameStatus.WIN => println("Das Spiel ist Aus! " + controller.getActualPlayer.color + " hat gewonnen.  n - Neues Spiel, e - Beenden")
      case GameStatus.DRAW => println("Das Spiel ist Aus! Unentschieden. n - Neues Spiel, e - Beenden")
    }
  }

  def outputPrintField() {
    println()
    for (i <- 0 until controller.grid.getColumns)
      print(" " + (i+1).toString)

    println()
    for (rowIndex <- (0 to controller.grid.rows-1)) {
      for (columnIndex <- 0 until controller.grid.getColumns) {

        val cell = controller.grid.getCell(rowIndex,columnIndex)
        val stringVar = if(cell!=null && cell.isSet) cell.gameToken.color else " "

        print("|" + stringVar)
      }
      print("|")
      println()
    }
    println()

    if(controller.gameStatus!=GameStatus.PLAYING) {
      gameOver()
    } else {
      println(controller.getActualPlayer.color + " ist an der Reihe, bitte Spalte wählen...")
    }
  }

  def makeTurnAndCheck(input:Int) {
    var isCorrect = false
    do {
      try {
        val col = input - 1
        if (col >= 0 && col < controller.gridColumns) {
          controller.makeTurn(col)
          isCorrect = true
        }
      } catch {
        case e: Exception => println("Die Eingabe ist keine korrekte Spalte! Bitte Spalte wählen...")
      }
    } while (!isCorrect)
  }

}
