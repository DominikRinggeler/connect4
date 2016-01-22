package de.htwg.mps.Model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 04.01.16.
 */
@RunWith(classOf[JUnitRunner])
class CellSpec extends Specification {
  "A cell with set token" should {
    val token = new GameToken(new HumanPlayer(1,"test"))
    val cell = new Cell(token)
    "be set" in {
      cell.isSet must beTrue
    }
    "have a game token with color 1 and name 'test'" in {
      new Cell(token).gameToken must be_==(token)
    }
  }
  "An empty cell" should {
    "be not set" in {
      val cell = new Cell(null)
      cell.isSet must beFalse
    }
    "have gameToken null" in {
      new Cell(null).gameToken must be_==(null)
    }
    "be empty" in {
      Cell.empty.isSet must beFalse
    }
  }
}