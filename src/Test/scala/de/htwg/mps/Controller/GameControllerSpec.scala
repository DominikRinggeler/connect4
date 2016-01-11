package de.htwg.mps.Controller

import de.htwg.mps.Model.{GameToken, Grid, HumanPlayer}
import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 06.01.16.
 */
@RunWith(classOf[JUnitRunner])
class GameControllerSpec extends Specification{

  "A GameController" should {
    val player = new HumanPlayer(1, "p1")
    val token = new GameToken(player)

    "add one player with name: p1" in {
      val controller = new GameController(new Grid(6, 7))
      controller.addPlayer(player.color, player.name)
      controller.getActualPlayer.name must be_==("p1")
    }
  }
}
