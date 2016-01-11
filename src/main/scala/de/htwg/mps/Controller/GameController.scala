package de.htwg.mps.Controller

import java.util.Observable

import de.htwg.mps.Model.{GameRuleController, Grid, HumanPlayer}

/**
 * Created by dominikringgeler on 25.10.15.
 */
class GameController(var grid:Grid) extends Observable {

  var players: List[HumanPlayer] = List()
  var nextPlayers: List[HumanPlayer] = List()

  // add playe new
  def addPlayerNew(oldPlayers: List[HumanPlayer], color: Int, name: String):List[HumanPlayer] = {
    var players = new HumanPlayer(color, name) :: oldPlayers
    players = players.reverse
    nextPlayers = players
    players
  }

  // player control
  def addPlayer(color: Int, name: String) {
    players = new HumanPlayer(color, name) :: players
    players = players.reverse
    nextPlayers = players
  }
  def removePlayers() {
    players = List()
    nextPlayers = List()
  }
  def getActualPlayer(): HumanPlayer = nextPlayers.head

  // grid control
  def reset {grid = grid.reset}
  def newGrid(r:Int, c:Int) {grid = grid.newGrid(r,c)}
  def gridRows = grid.rows
  def gridColumns = grid.columns
  def printField = grid.printField
  def getRowIndex(index:Int) = grid.getRowIndex(index)

  // game control
  def makeTurn(col: Int): Boolean = {
    if (col >= 0 && col < grid.columns) {
      val oldGrid = grid
      grid = grid.setCell(col, getActualPlayer.token)
      if (grid!=null) true else {
        grid = oldGrid
        false
      }
    } else false
  }
  def conn4(c:Int, player: HumanPlayer): Boolean ={
    val grc = new GameRuleController(grid)
    val win = grc.checkConnectFour(c,player)
    if (!win) {
      nextPlayers = nextPlayers.tail
      if (nextPlayers.length == 0) {
        nextPlayers = players
      }
    }
    win
  }
}
