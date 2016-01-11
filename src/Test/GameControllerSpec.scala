package de.htwg.mps.Model

import de.htwg.mps.Controller.GameController
import org.junit.runner._
import org.specs2.mutable._
import org.specs2.runner.JUnitRunner

/**
 * Created by dominikringgeler on 09.11.15.
 */

@RunWith(classOf[JUnitRunner])
class GameControllerSpec extends Specification {

  "A Gamefield with set Fields" should {

    val controller = new GameController()

    var player = new HumanPlayer(1, "p1")
    var token = new GameToken(player, player.color)

    GameField.fields(0)(0) = token
    GameField.fields(0)(1) = token
    GameField.fields(0)(2) = token
    GameField.fields(0)(3) = token

    //GameField.setFieldToken(1, token)
    //GameField.setFieldToken(2, token)
    //GameField.setFieldToken(3, token)
    //GameField.setFieldToken(4, token)

    "have 4 in a row" in {
      controller.checkConnectFour(0) must be_==(false)
    }
  }
}

