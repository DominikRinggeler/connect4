package de.htwg.mps

import java.awt.Color
import javax.swing.Box

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

  var rows = 8
  GameField.initializeField(rows,rows)

  val dCol = new Dimension(50, rows*45)
  val dCell = new Dimension(40, 40)


  lazy val ui = new FlowPanel()

  for(index <- 0 until rows){
    val col =  new BoxPanel(Orientation.Vertical) {
      minimumSize = dCol
      maximumSize = dCol
      preferredSize = dCol
      background = Color.black
      listenTo(mouse.clicks)
      reactions += {
        case e: MouseClicked =>
          println("Mouse clicked at " + e.point)
      }

      for(indexRow <- 0 until rows) {
        contents += new Panel {
          background = Color.white
          minimumSize = dCell
          maximumSize = dCell
          preferredSize = dCell
          border = Swing.EmptyBorder(5, 5, 5, 5)
        }
        peer.add(Box.createVerticalStrut(5))

      }
    }
    ui.contents += col
  }



    def top = new MainFrame {
    title = "Hello to Connect 4!"
    contents = ui
  }
}