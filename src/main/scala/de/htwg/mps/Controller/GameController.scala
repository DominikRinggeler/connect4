package de.htwg.mps.Controller

import java.util.Observable

import de.htwg.mps.Model.{GameField, HumanPlayer}

/**
 * Created by dominikringgeler on 25.10.15.
 */
class GameController extends Observable{

  var players:List[HumanPlayer] = List()
  var nextPlayers:List[HumanPlayer] = List()

  def addPlayer(color:Int,name:String): Unit ={
     players = new HumanPlayer(color,name)::players
     players = players.reverse
     nextPlayers = players

  }

  def makeTurn(col: Int): Boolean={
    var isCorrect = nextPlayers.head.makeTurn(col)
    isCorrect

  }

  def getName(): String={
   return nextPlayers.head.name
  }

  def checkConnectFour(column:Int): Boolean ={
    val rowIndex = GameField.getRowIndex(column)

    var win = checkFourInColumn(rowIndex,column)

    if(!win) {
      nextPlayers = nextPlayers.tail

      if (nextPlayers.length == 0) {
        nextPlayers = players
      }
      false
    }
    else
      true
  }

  def checkFourInColumn(row:Int, column:Int): Boolean ={

    var countToken = 0
    var win = false

    // check left row
    var currentColor = nextPlayers.head.color
    for(rowIndex <- -3 until 3 if GameField.getFieldToken(rowIndex+row, column-1)!= null){
      var tmpColor = GameField.getFieldToken(rowIndex+row, column-1).color
      if(currentColor == tmpColor){
        countToken= countToken+1

        if(countToken >=4)
          win = true
      }
      else
        countToken=0
    }
    win
  }
}
