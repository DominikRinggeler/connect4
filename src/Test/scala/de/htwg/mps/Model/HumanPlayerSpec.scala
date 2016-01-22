package de.htwg.mps.Model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 06.01.16.
 */
@RunWith(classOf[JUnitRunner])
class HumanPlayerSpec extends Specification {
  "A Human Player without name and color " should {
    val player = new HumanPlayer(0, "")
    val token = new GameToken(player);

    "have color 1" in {
      player.color must be_==(0)
    }

    "have name 'Test Name'" in {
      player.name must be_==("")
    }

    "have token color 0" in {
      player.token.color must be_==(0)
    }

    "return the owner" in {
      player.token.owner must be_==(player)
    }
  }

  "A Human Player set name and color " should {
    val player = new HumanPlayer(1,"Test Name")

    "have color 1" in {
      player.color must be_==(1)
    }

    "have name 'Test Name'" in {
      player.name must be_==("Test Name")
    }

    "have token color 1" in {
      player.token.color must be_==(1)
    }

    "return the owner" in {
      player.token.owner must be_==(player)
    }
  }
}
