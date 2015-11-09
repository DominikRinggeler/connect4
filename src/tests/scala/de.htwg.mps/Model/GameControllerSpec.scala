package de.htwg.mps

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.{HumanPlayer, GameToken, GameField}
import org.specs2.mutable._

/**
 * Created by dominikringgeler on 09.11.15.
 */
class GameControllerSpec extends SpecificationWithJUnit{
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

