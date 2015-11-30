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

  def removePlayers(): Unit ={
    players = List()
    nextPlayers= List()
  }

  def makeTurn(col: Int): Boolean={
    var isCorrect = nextPlayers.head.makeTurn(col)
    isCorrect
  }

  def getName: String={
   nextPlayers.head.name
  }

  def getColor: Int={
    nextPlayers.head.color
  }

  def checkConnectFour(column:Int): Boolean ={
    val rowIndexLastToken = GameField.getRowIndex(column)-1

    var win = checkFourInColumn(rowIndexLastToken,column)
    if(!win) {
      win = checkFourInRow(rowIndexLastToken,column)
    }
    if(!win) {
      win = checkFourDiagonalLeftRight(rowIndexLastToken,column)
    }
    if(!win) {
      win = checkFourDiagonalRightLeft(rowIndexLastToken,column)
    }

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

    var currentColor = nextPlayers.head.color
    for(rowIndex <- -3 until 4 if GameField.getFieldToken(rowIndex+row, column)!= null){
      var tmpColor = GameField.getFieldToken(rowIndex+row, column).color
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

  def checkFourInRow(row:Int, column:Int): Boolean ={

    var countToken = 0
    var win = false

    var currentColor = nextPlayers.head.color

    for(colIndex <- -3 until 4 ) {
      if (GameField.getFieldToken(row, column + colIndex) != null) {
        var tmpColor = GameField.getFieldToken(row, column + colIndex).color
        if (currentColor == tmpColor) {
          countToken = countToken + 1
          if (countToken >= 4)
            win = true
        }
        else {
          countToken = 0
        }
      } else {
      countToken = 0
      }
    }
    win
  }

  def checkFourDiagonalLeftRight(row:Int, column:Int): Boolean ={

    var countToken = 0
    var win = false

    var currentColor = nextPlayers.head.color
    for(index <- -3 until 4 ){
      if(GameField.getFieldToken(index+row, index+column)!= null){
        var tmpColor = GameField.getFieldToken(index+row, index+column).color
        if(currentColor == tmpColor){
          countToken= countToken+1

          if(countToken >=4)
            win = true
        }
        else
          countToken=0
      } else {
        countToken=0
      }
    }
    win
  }

  def checkFourDiagonalRightLeft(row:Int, column:Int): Boolean ={

    var countToken = 0
    var win = false

    var currentColor = nextPlayers.head.color
    for(index <- -3 until 4){
      if (GameField.getFieldToken((-1*index)+row, index+column)!= null) {
        var tmpColor = GameField.getFieldToken((-1 * index) + row, index + column).color
        if (currentColor == tmpColor) {
          countToken = countToken + 1

          if (countToken >= 4)
            win = true
        }
        else
          countToken = 0
      }else{
        countToken = 0
      }
    }
    win
  }
}
