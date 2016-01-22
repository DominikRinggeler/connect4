package de.htwg.mps.Model

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 04.01.16.
 */
@RunWith(classOf[JUnitRunner])
class GameTokenSpec extends Specification{
  "A token with set owner" should {
    val player = new HumanPlayer(1, "test")
    "have color 1" in {
      new GameToken(player).color must be_==(1)
    }

    "have an owner with color 1 und name 'test'" in {
      val token = new GameToken(player)
      token.owner.color must be_==(1)
      token.owner.name must be_==("test")
    }
  }
}
