package de.htwg.mps.Model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 04.01.16.
 */
@RunWith(classOf[JUnitRunner])
class GridSpec extends Specification {

  "A new Grid with 1 empty Cell" should {
    val player = new HumanPlayer(1, "p1")
    val token = new GameToken(player)
    val grid = new Grid(1, 1)


    /*
    "have no game token" {
      val grid = new Grid(1, 1)
      val cell = grid.getCell(0, 0)
      cell.gameToken must be_==(null)
    }
    */

    "have no game token" in {
      grid.rows must be_==(1)
    }

    "have 1 row" in {
      grid.rows must be_==(1)
    }

    "have 1 column" in {
      grid.columns must be_==(1)
    }

    "have set" in {
      //var cells:Vector[Cell] = Vector.fill(1*1)(new Cell(token))
      grid.setCell(0,token).getCell(0,0).gameToken must be_==(token)
    }

    "have row index 1" in {
      grid.getRowIndex(0) must be_==(0)
    }

    "have 1 cell" in {
      grid.getCells.size must be_==(1)
    }

    /*
    "generate a String of the form\n" +
      " 1 \n" +
      "| |\n" in {
      var grid = new Grid(1, 1)
      grid.printField must be_==("\n\n 1 \n| |\n\n\n")
    }
    */
  }

  "A new Grid with 6x7 Cells" should {
    val player = new HumanPlayer(1, "p1")
    val token = new GameToken(player)

    "have position 35 at column 1" in {
      var grid = new Grid(6, 7)
      grid.getPosition(0) must be_==(35)
    }

    "have set one value in the field (6,2)" in {
      var grid = new Grid(6,7)
      grid = grid.setCell(1,token)
      grid.getCell(5,1).isSet must be_==(true)
    }

    "have set one value in the field (5,2)" in {
      var grid = new Grid(6,7)
      grid = grid.setCell(1,token)
      grid = grid.setCell(1,token)
      grid.getCell(4,1).isSet must be_==(true)
    }

    "have set second set value in the field (0,2)" in {
      var grid = new Grid(3,3)
      grid = grid.reset
      grid = grid.setCell(1,token)
      grid = grid.setCell(1,token)
      grid.getCell(1,1).isSet must be_==(true)
    }

    "be empty" in {
      var grid = new Grid(3,3)
      grid.setCell(1,token)
      grid.reset.getCell(0,1).isSet must be_==(false)
    }

  }
}
