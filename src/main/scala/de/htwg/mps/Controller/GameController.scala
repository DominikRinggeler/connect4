package de.htwg.mps.Controller

import java.util.Observable

import de.htwg.mps.Model.{HumanPlayer, GameRuleController, Grid}

/**
 * Created by dominikringgeler on 25.10.15.
 */
class GameController(var grid:Grid) extends Observable {

  var players: List[HumanPlayer] = List()
  var nextPlayers: List[HumanPlayer] = List()

  // player control
  def addPlayer(player: HumanPlayer) {
    players = player :: players
    players = players.reverse
    nextPlayers = players
  }
  def removePlayers() {
    players = List()
    nextPlayers = List()
  }
  def getActualPlayer: HumanPlayer = nextPlayers.head
  def getNextPlayers: List[HumanPlayer] = {
    nextPlayers = nextPlayers.tail
    if (nextPlayers.length == 0) nextPlayers = players
    nextPlayers
  }


  // grid control
  def reset() {grid = grid.reset}
  def newGrid(r:Int, c:Int) {grid = grid.newGrid(r,c)}
  def gridRows = grid.rows
  def gridColumns = grid.columns
  def getRowIndex(index:Int) = grid.getRowIndex(index)

  // game control
  def makeTurn(col: Int): Boolean = {
    if (col >= 0 && col < grid.columns) {
      grid = grid.setCell(col, getActualPlayer.token) getOrElse grid
      true
    } else
      false
  }

  def conn4(c:Int, player: HumanPlayer): Boolean ={
    val grc = new GameRuleController(grid)
    val win = grc.checkConnectFour(c,player)
    if (!win) {
      nextPlayers = getNextPlayers
    }
    win
  }

}
