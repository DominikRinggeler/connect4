package de.htwg.mps.Controller

import java.util.Observable

import de.htwg.mps.Model.{GameStatus, HumanPlayer, GameRuleController, Grid}

import scala.swing.Publisher
import scala.swing.event.Event

case class ChangeField() extends Event
case class MakeTurn() extends Event
case class StartGame() extends Event

/**
 * Created by dominikringgeler on 25.10.15.
 */
class GameController(var grid:Grid) extends Publisher {

  var players: List[HumanPlayer] = List()
  var nextPlayers: List[HumanPlayer] = List()

  var gameStatus = GameStatus.NOT_PLAYING

  // player control
  def addPlayer(player: HumanPlayer) {
    players = player :: players
    nextPlayers = players
  }
  def removePlayers(): Unit ={
    players = List()
    nextPlayers = players
  }

  def set2Player(): Unit = {
    removePlayers()

    addPlayer(new HumanPlayer(2,"Spieler 2"))
    addPlayer(new HumanPlayer(1,"Spieler 1"))
    reset
  }
  def set3Player(): Unit = {
    removePlayers()

    addPlayer(new HumanPlayer(3,"Spieler 3"))
    addPlayer(new HumanPlayer(2,"Spieler 2"))
    addPlayer(new HumanPlayer(1,"Spieler 1"))
    reset
  }


  def getActualPlayer: HumanPlayer = nextPlayers.head
  def getNextPlayers: List[HumanPlayer] = {
    nextPlayers = nextPlayers.tail
    if (nextPlayers.length == 0) nextPlayers = players
    nextPlayers
  }

  // grid control
  def reset() {
    grid = grid.reset
    gameStatus = GameStatus.PLAYING
    nextPlayers = players
    publish(new ChangeField)
  }

  def gridRows = grid.rows

  def gridColumns = grid.getColumns

  def getRowIndex(index:Int) = grid.getRowIndex(index)

  // game control
  def makeTurn(col: Int) = {
      val actualPlayer = getActualPlayer
      grid = grid.setCell(col, actualPlayer.token) getOrElse grid
      if(conn4(col, actualPlayer)) gameStatus = GameStatus.WIN
      publish(new ChangeField)
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