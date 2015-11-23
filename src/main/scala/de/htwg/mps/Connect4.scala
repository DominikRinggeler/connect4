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

  val controller = new GameController
  var rows = 8

  def newField = new TextField {
    text=""
    columns=5
    horizontalAlignment = Alignment.Right
  }

  val fieldplayer1 = newField
  val fieldplayer2 = newField

  GameField.initializeField(rows,rows)

  val dCol = new Dimension(50, rows*45)
  val dCell = new Dimension(40, 40)

  var gameStartet = false
  var gameOver = false


  lazy val gameFieldUi = new FlowPanel()
  gameFieldUi.enabled = false

  for(indexCol <- 0 until rows){
    val col =  new BoxPanel(Orientation.Vertical) {
      minimumSize = dCol
      maximumSize = dCol
      preferredSize = dCol
      background = Color.black
      listenTo(mouse.clicks)

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


      reactions += {
        case e: MouseClicked =>

          if (gameStartet==true && gameOver == false){
            var isCorrect = false
            var win = false
            var color = controller.getColor()

            isCorrect = controller.makeTurn(indexCol)
            if (isCorrect) {
              win = controller.checkConnectFour(indexCol)

              val rowIndexLastToken = GameField.getRowIndex(indexCol)-1
              val numberOfContents = rows+rows-2


              if (color ==1)
                this.contents(numberOfContents-rowIndexLastToken*2).background = Color.red
              else if (color == 2)
                this.contents(numberOfContents-rowIndexLastToken*2).background = Color.green


            }
            if (win) {
              gameOver = true
              println("winning")
            }
          }


      }


    }
    gameFieldUi.contents += col
  }

  val goButton = new Button{
    text = "Spiel starten"

  }
  listenTo(goButton)

  reactions += {
    case ButtonClicked(goButton) =>

      if (fieldplayer1.text == ""){
        Dialog.showMessage(fieldplayer1, "Bitte geheben Sie einen Name für Spieler 1 an", "Name von Spieler 1 fehlt", Dialog.Message.Error)
      }
      else  if (fieldplayer2.text == ""){
        Dialog.showMessage(fieldplayer2, "Bitte geheben Sie einen Name für Spieler 2 an", "Name von Spieler 2 fehlt", Dialog.Message.Error)
      }
      else  if (fieldplayer2.text == fieldplayer1.text){
        Dialog.showMessage(fieldplayer1, "Bitte geheben Sie unterschiedliche Namen an", "Namen sind identisch", Dialog.Message.Error)
      }
      else{
        gameStartet = true
        gameOver = false

        controller.addPlayer(1, fieldplayer1.text)
        controller.addPlayer(2, fieldplayer2.text)
      }


  }




  lazy val header = new FlowPanel(new Label("Spieler 1:"),fieldplayer1, new Label("  Spieler 2:"),fieldplayer2,goButton){
      border = Swing.EmptyBorder(10,10,10,10)
  }

  def top = new MainFrame {
      title = "Hello to Connect 4!"

    contents = new BoxPanel(Orientation.Vertical) {
      contents += header
      contents += gameFieldUi

    }
  }



}


