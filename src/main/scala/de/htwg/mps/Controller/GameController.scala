package de.htwg.mps.Controller

import java.util.Observable

import de.htwg.mps.Model.{GameField, HumanPlayer}

/**
 * Created by dominikringgeler on 25.10.15.
 */
class GameController extends Observable{

  var players:List[HumanPlayer] = List()

  def addPlayer(color:Int,name:String): Unit ={
     new HumanPlayer(color,name)::players
  }

}
