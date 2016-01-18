package controllers

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.GameField
import play.api._
import play.api.mvc._
import tui.mps.Tui
import views.html.helper._


object Application extends Controller {

  val controller = new GameController
  val tui = new Tui(controller)

  def init () = Action {
    Ok(views.html.index("Hello to Connect4"))
  }

  def start () = Action {implicit request =>
    val rows = 8
    val columns = 8
    GameField.initializeField(rows, columns)

    var player1 =  request.getQueryString("s1")
    var player2 =  request.getQueryString("s2")

    controller.addPlayer(1, player1.get)
    controller.addPlayer(2, player2.get)


    val result = tui.processInputLine("printfield")
    Ok(views.html.main(result))
  }

  def command () = Action {implicit request =>

    val line =  request.getQueryString("line")
    val result = tui.processInputLine(line.get)
    Ok(views.html.main(result))

  }

}