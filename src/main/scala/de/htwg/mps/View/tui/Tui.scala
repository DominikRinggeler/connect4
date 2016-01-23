package de.htwg.mps.View.tui

import java.util.{InputMismatchException, Observable, Observer}
import akka.actor.Actor
import de.htwg.mps.Controller.{ChangeField, StartGame, MakeTurn, GameController}
import de.htwg.mps.Model.{GameStatus, HumanPlayer}

import scala.io.StdIn._
import scala.swing.Reactor
import scala.util.{Success, Failure, Try}

/**
 * Created by dominikringgeler on 25.10.15.
 */
class Tui (var controller: GameController) extends Reactor{
  listenTo(controller)

  reactions += {
    case e: ChangeField => printGameField()
  }

  // to check if its the first turn in the match
  var first = true

  // starts the processInputLine in a loop
  def startGame(): Unit ={
    if(first){
      printGameField()
      first = false
    }
    while(processInputLine(readLine())){}
    System.exit(0);
  }

  // reacts on the input of command line
  def processInputLine(input: String) = {
    var continue = true
    input match {
      case "n" => controller.reset;
      case "e" => continue = false
      case "p2" => controller.set2Player()
      case "p3" => controller.set3Player()
      case _ => {
        if(controller.gameStatus==GameStatus.PLAYING){
          var input1 = Try(input.toInt)
          input1 match {
            case Failure(exception) => {
              print("Nicht gültig, nochmal setzen...")
            }
            case Success(i) => i match {
                case i:Int if (i > 0 && i <= controller.gridColumns) => printAfterTurn(i)
                case i:Int if (i > controller.gridColumns || i <= 0) => print("Nicht gültig, nochmal setzen...")
              }
            }
        } else {
          printGameOver()
        }
      }
    }
    continue
  }

  // print the game over output
  def printGameOver(): Unit ={
    controller.gameStatus match {
      case GameStatus.WIN => println("Das Spiel ist Aus! " + controller.getActualPlayer.name + " hat gewonnen.  n - Neues Spiel, e - Beenden")
      case GameStatus.DRAW => println("Das Spiel ist Aus! Unentschieden. n - Neues Spiel, e - Beenden")
    }
  }

  // prints the field
  def printGameField() {
    println()
    for (i <- 0 until controller.grid.getColumns)
      print(" " + (i+1).toString)

    println()
    for (rowIndex <- (0 to controller.grid.rows-1)) {
      for (columnIndex <- 0 until controller.grid.getColumns) {
        var cell = controller.grid.getCell(rowIndex,columnIndex)
        val stringVar = if(cell!=null && cell.isSet) cell.gameToken.color else " "

        print("|" + stringVar)
      }
      print("|")
      println()
    }
    println()

    if(controller.gameStatus!=GameStatus.PLAYING) {
      printGameOver()
    } else {
      println(controller.getActualPlayer.name + " ist an der Reihe, bitte Spalte wählen...")
    }
  }

  // prints the output of a turn of a player
  def printAfterTurn(input:Int) {
    var isCorrect = false
    try {
      val col = input - 1
      if (col >= 0 && col < controller.gridColumns) {
        isCorrect = controller.makeTurn(col)
        if (!isCorrect) println("Die Eingabe ist keine korrekte Spalte! Bitte Spalte wählen...")
      }
    } catch {
      case e: Exception => {
        printGameField
        println("Die Eingabe ist keine korrekte Spalte! Bitte Spalte wählen...")
      }
    }
  }
}
