package de.htwg.mps.Controller

import de.htwg.mps.Model.{GameToken, HumanPlayer, Grid}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 11.01.16.
 */
@RunWith(classOf[JUnitRunner])
class GameControllerSpec extends Specification{

  "A game controller with two set player and a grid (8,8)" should {
    val grid = new Grid(8,8)
    val controller = new GameController(grid)
    controller.addPlayer(new HumanPlayer(1,"player1"))
    controller.addPlayer(new HumanPlayer(2,"player2"))

    "have removed all players" in {
      val controller = new GameController(grid)
      controller.removePlayers()
      controller.players.size must be_==(0)
    }

    "have actual player 'player 1'" in {
      controller.getActualPlayer.name must be_==("player1")
    }

    "have next player 'player 2'" in {
      val controller = new GameController(grid)
      controller.addPlayer(new HumanPlayer(1,"player1"))
      controller.addPlayer(new HumanPlayer(2,"player2"))
      controller.getNextPlayers
      controller.getActualPlayer.name must be_==("player2")
    }

    "have a reset grid" in {
      val controller = new GameController(grid)
      controller.reset
      controller.gridColumns must be_==(8)
    }

    "have set a new Grid(7,7)" in {
      val controller = new GameController(grid)
      controller.newGrid(7,7)
      controller.gridColumns must be_==(7)
      controller.gridRows must be_==(7)
    }

    "have 8 rows" in {
      controller.newGrid(8,8)
      controller.gridRows must be_==(8)
    }

    "have 8 columns" in {
      controller.newGrid(8,8)
      controller.gridColumns must be_==(8)
    }

    "have row index: 0" in {
      val controller = new GameController(grid)
      controller.newGrid(8,8)
      controller.getRowIndex(0) must be_==(7)
    }

    "have not made a turn with column 9" in {
      controller.makeTurn(9) must beFalse
    }

    "have made a turn with column 6" in {
      controller.makeTurn(6) must beTrue
    }

    "have no connect 4" in {
      val controller = new GameController(grid)
      controller.addPlayer(new HumanPlayer(1,"player1"))
      controller.conn4(1, new HumanPlayer(1, "player1")) must beFalse
    }

    "have connect 4" in {
      val controller = new GameController(grid)
      controller.addPlayer(new HumanPlayer(1,"player1"))
      controller.makeTurn(1)
      controller.makeTurn(1)
      controller.makeTurn(1)
      controller.makeTurn(1)
      controller.conn4(1, new HumanPlayer(1, "player1")) must beTrue
    }

    "have next player - player2" in {
      val controller = new GameController(grid)
      val player1 = new HumanPlayer(1,"player1")
      val player2 = new HumanPlayer(2,"player2")

      controller.addPlayer(player1)
      controller.addPlayer(player2)

      controller.getNextPlayers.head must be_==(player2)
    }
  }
}
