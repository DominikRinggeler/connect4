package de.htwg.mps.Model

import de.htwg.mps.Controller.GameController
import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 09.11.15.
 */

@RunWith(classOf[JUnitRunner])
class GameRuleControllerSpec extends Specification{

  "A GameRuleController without set Fields" should {

    "have a checked return (0, false) " in {
      val grid = new Grid(6,7)
      val controller = new GameRuleController(grid)
      val cell = grid.getCell(0, 0)

      controller.check(cell,0,0) must be_==((0,false))
    }

    "have not 4 in a column" in {
      val grid = new Grid(6,7)
      val controller = new GameRuleController(grid)
      controller.checkFourInColumn(2,0,1) must be_==(false)
    }

    "have not 4 in a row" in {
      val grid = new Grid(6,7)
      val controller = new GameRuleController(grid)
      controller.checkFourInRow(5,4,1) must be_==(false)
    }

    "have not 4 in a diagonal from left to right up" in {
      val grid = new Grid(6,7)
      val controller = new GameRuleController(grid)
      controller.checkFourDiagonalLeftRightUp(2,4,1) must be_==(false)
    }

    "have not 4 in a diagonal from left to right down" in {
      var grid = new Grid(6,7)
      val controller = new GameRuleController(grid)
      controller.checkFourDiagonalLeftRightDown(1,1,1) must be_==(false)
    }
  }

  "A GameRuleController with set Fields" should {
    val player = new HumanPlayer(1, "p1")
    val token = new GameToken(player)

    "have a checked return (4, true) " in {
      var grid = new Grid(6,7)
      grid = grid.setCellWithRowCol(0, 0, token) getOrElse grid
      val controller = new GameRuleController(grid)
      val cell = grid.getCell(0, 0)

      controller.check(cell,1,3) must be_==((4, true))
    }

    "have 4 in a column" in {
      var grid = new Grid(6,7)
      grid = grid.setCellWithRowCol(2, 0, token) getOrElse grid
      grid = grid.setCellWithRowCol(3, 0, token) getOrElse grid
      grid = grid.setCellWithRowCol(4, 0, token) getOrElse grid
      grid = grid.setCellWithRowCol(5, 0, token) getOrElse grid

      val controller = new GameRuleController(grid)
      controller.checkFourInColumn(2,0,1) must be_==(true)
    }

    "have 4 in a row" in {
      var grid = new Grid(6,7)
      grid = grid.setCellWithRowCol(5, 1, token) getOrElse grid
      grid = grid.setCellWithRowCol(5, 2, token) getOrElse grid
      grid = grid.setCellWithRowCol(5, 3, token) getOrElse grid
      grid = grid.setCellWithRowCol(5, 4, token) getOrElse grid

      val controller = new GameRuleController(grid)
      controller.checkFourInRow(5,4,1) must be_==(true)
    }

    "have 4 in a diagonal from left to right up" in {
      var grid = new Grid(6,7)
      grid = grid.setCellWithRowCol(5, 1, token) getOrElse grid
      grid = grid.setCellWithRowCol(4, 2, token) getOrElse grid
      grid = grid.setCellWithRowCol(3, 3, token) getOrElse grid
      grid = grid.setCellWithRowCol(2, 4, token) getOrElse grid

      val controller = new GameRuleController(grid)
      controller.checkFourDiagonalLeftRightUp(2,4,1) must be_==(true)
    }

    "have 4 in a diagonal from left to right down" in {
      var grid = new Grid(6,7)
      grid = grid.setCellWithRowCol(1, 1, token) getOrElse grid
      grid = grid.setCellWithRowCol(2, 2, token) getOrElse grid
      grid = grid.setCellWithRowCol(3, 3, token) getOrElse grid
      grid = grid.setCellWithRowCol(4, 4, token) getOrElse grid

      val controller = new GameRuleController(grid)
      controller.checkFourDiagonalLeftRightDown(1,1,1) must be_==(true)
    }
  }
}

