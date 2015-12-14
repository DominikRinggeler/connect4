package de.htwg.mps.View.gui

import java.awt.{Color, Dimension}
import javax.swing.Box

import de.htwg.mps.Connect4._
import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.GameField

import scala.swing
import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing._

/**
 * Created by dominikringgeler on 23.11.15.
 */
object Gui extends SimpleSwingApplication {

  var controller : GameController = null

  def setController(controller: GameController){
    this.controller = controller
  }

  var rows = GameField.rows
  val dCol = new Dimension(50, rows*45)
  val dCell = new Dimension(40, 40)

  var gameStartet = false
  var gameOver = false
  var counter = 0

  // Header
  def newField = new TextField {
    text=""
    columns=5
    horizontalAlignment = Alignment.Right
  }

  val fieldplayer1 = newField
  val fieldplayer2 = newField

  lazy val header = new FlowPanel(new Label("Spieler 1:"),fieldplayer1, new Label("  Spieler 2:"),fieldplayer2,goButton){
    border = Swing.EmptyBorder(10,10,10,10)
  }

  val goButton = new Button{
    text = "Spiel starten"

  }
  listenTo(goButton)

  reactions += {
    case ButtonClicked(goButton) =>
      if(gameStartet){
        resetGame()
      }
      initGame
      if(gameStartet){
        goButton.text="Neu starten"
      }
  }

  // GameField
  lazy val gameFieldUi = new FlowPanel()

  var cols = new Array[BoxPanel](GameField.columns)

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
            var color = controller.getColor

            isCorrect = controller.makeTurn(indexCol)
            if (isCorrect) {
              win = controller.checkConnectFour(indexCol)

              val rowIndexLastToken = GameField.getRowIndex(indexCol)-1
              val numberOfContents = rows+rows-2

              if (color ==1)
                this.contents(numberOfContents-rowIndexLastToken*2).background = Color.red
              else if (color == 2)
                this.contents(numberOfContents-rowIndexLastToken*2).background = Color.green

              counter = counter+1
              if(counter==(GameField.rows+1)*(GameField.columns+1)){
                outputText.text = "Unentschieden!"
              }else{
                nextPlayer
              }
            }
            if (win) {
              gameOver = true
              println("winning")
              outputText.text = controller.getName + " hat gewonnen!"
              goButton.text="Revanche!"
            }
          }
      }
    }
    cols(indexCol) = col
    gameFieldUi.contents += col
  }

  // Footer
  val outputText = new Label {
    text = "Bitte die Spielernamen eingeben und Spiel starten"
  }
  val footer = new FlowPanel(outputText)


  def top = new MainFrame {
    title = "Hello to Connect 4!"
    //resizable = false

    contents = new BoxPanel(Orientation.Vertical) {
      contents += header
      contents += gameFieldUi
      contents += footer
    }

    menuBar = new MenuBar {
      contents += new Menu("Spiel")
      {
        contents += new MenuItem(newGameAction)
        contents += new MenuItem(quitAction)
      }
    }
  }

  val quitAction = Action("Beenden") {System.exit(0)}
  val newGameAction = Action("Neu starten") {
    resetGame
    initGame
  }

  def initGame(): Unit ={
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

      outputText.text = controller.getName + " ist an der Reihe"
    }
  }

  def resetGame(): Unit ={

    controller.removePlayers()

    for (col <- cols){
      for(cellIndex <- 0 until rows) {
        val numberOfContents = rows+rows-2
        col.contents(numberOfContents-cellIndex*2).background = Color.white
        GameField.initializeField(GameField.rows,GameField.columns)
      }
    }
  }

  def nextPlayer: Unit ={
    outputText.text = controller.getName + " ist an der Reihe!"
  }
}

