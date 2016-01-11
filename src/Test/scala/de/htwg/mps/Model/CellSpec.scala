package de.htwg.mps.Model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 04.01.16.
 */
@RunWith(classOf[JUnitRunner])
class CellSpec extends Specification {
  "An empty cell" should {
    "have gameToken null" in {
      new Cell(null).gameToken must be_==(null)
    }
    "be not set" in {
      val cell = new Cell(null)
      cell.isSet must beFalse
    }
  }

  "A set cell" should {
    val token = new GameToken(new HumanPlayer(1,"test"))
    "have a game token with color 1 and name 'test'" in {
      new Cell(token).gameToken must be_==(token)
    }
    "be set" in {
      val cell = new Cell(token)
      cell.isSet must beTrue
    }
  }
}