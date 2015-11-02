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

    if (isCorrect){
      nextPlayers = nextPlayers.tail

      if (nextPlayers.length==0){
        nextPlayers = players
      }
    }

    return isCorrect

  }

  def getName(): String={
   return nextPlayers.head.name
  }



}
