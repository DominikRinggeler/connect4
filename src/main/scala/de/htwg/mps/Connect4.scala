package de.htwg.mps

import java.awt.Color

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.{GameField, GameToken, HumanPlayer}
import de.htwg.mps.View.Tui.Tui

import scala.swing.event.{ButtonClicked, MouseClicked}
import swing._

/*
//TUI:

object Connect4 extends App {

  GameField.initializeField(8,8)

  val tui = new Tui(new GameController)
  tui.processInputLine();
}

*/

//GUI:

object Connect4 extends SimpleSwingApplication {

  GameField.initializeField(6,6)

  val s = new Dimension(50, 500)

  val col1 =  new BoxPanel(Orientation.Vertical) {
    minimumSize = s
    maximumSize = s
    preferredSize = s
    background = Color.black
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked =>
        println("Mouse clicked at " + e.point)
    }
  }

  val col2 =  new BoxPanel(Orientation.Vertical) {
    minimumSize = s
    maximumSize = s
    preferredSize = s
    background = Color.black
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked =>
        println("Mouse clicked at " + e.point)
    }
  }

  val col3 =  new BoxPanel(Orientation.Vertical) {
    minimumSize = s
    maximumSize = s
    preferredSize = s
    background = Color.black
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked =>
        println("Mouse clicked at " + e.point)
    }
  }

  val col4 =  new BoxPanel(Orientation.Vertical) {
    minimumSize = s
    maximumSize = s
    preferredSize = s
    background = Color.black
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked =>
        println("Mouse clicked at " + e.point)
    }
  }

  val col5 =  new BoxPanel(Orientation.Vertical) {
    minimumSize = s
    maximumSize = s
    preferredSize = s
    background = Color.black
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked =>
        println("Mouse clicked at " + e.point)
    }
  }

  val col6 =  new BoxPanel(Orientation.Vertical) {
    minimumSize = s
    maximumSize = s
    preferredSize = s
    background = Color.black
    listenTo(mouse.clicks)
    reactions += {
      case e: MouseClicked =>
        println("Mouse clicked at " + e.point)
    }
  }

  col6.contents += new Button { text = "1"
  }
  col6.contents += new Button { text = "2"
  }
  col6.contents += new Button { text = "3"
  }

  lazy val ui = new FlowPanel(col1, col2, col3, col4, col5, col6) {
    border = Swing.EmptyBorder(10, 10, 10, 10)

  }


  def top = new MainFrame {
    title = "Hello to Connect 4!"
    contents = ui
  }
}