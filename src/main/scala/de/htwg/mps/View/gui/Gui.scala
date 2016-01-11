package de.htwg.mps.View.gui

import java.awt.{Color, Dimension}
import javax.swing.{JPanel, JFrame, Box}

import de.htwg.mps.Connect4._
import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.GameStatus

import scala.swing
import scala.swing.event.{ButtonClicked, MouseClicked}
import scala.swing._

/**
 * Created by dominikringgeler on 23.11.15.
 */
class Gui(controller: GameController) extends SimpleSwingApplication {
  val rows = controller.gridRows
  val dCol = new Dimension(50, rows*45)
  val dCell = new Dimension(40, 40)
  var gameStatus = GameStatus.NOT_PLAYING

  //var gameStartet = false
  //var gameOver = false

  /*
   *    Init Header
   */
  val fieldplayer1 = newField
  val fieldplayer2 = newField
  val goButton = new Button{
    text = "Spiel starten"
  }
  listenTo(goButton)

  lazy val header = new FlowPanel(new Label("Spieler 1:"),fieldplayer1, new Label("  Spieler 2:"),fieldplayer2,goButton){
    border = Swing.EmptyBorder(10,10,10,10)
  }
  def newField = new TextField {
    text=""
    columns=5
    horizontalAlignment = Alignment.Right
  }

  reactions += {
    case ButtonClicked(goButton) =>
      resetGame
      initPlayers
      goButton.text="Neu starten"
  }

  /*
   *  Init GameField
   */
  lazy val gameFieldUi = new FlowPanel()

  var cols = new Array[BoxPanel](controller.gridColumns)

  def initField {
    var counter = 0
    gameFieldUi.contents.clear()
    cols = new Array[BoxPanel](controller.gridColumns)
    for (indexCol <- 0 until controller.gridColumns) {
      var col = new BoxPanel(Orientation.Vertical) {
        minimumSize = dCol
        maximumSize = dCol
        preferredSize = dCol
        background = Color.black
        listenTo(mouse.clicks)

        for (indexRow <- 0 until controller.gridRows) {
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
            if (gameStatus== GameStatus.PLAYING) {
              var isCorrect = false
              var win = false

              // get the player to make his turn
              val player = controller.getActualPlayer

              // make the players turn and check if his turn is correct
              isCorrect = controller.makeTurn(indexCol)

              if (isCorrect) {
                win = controller.conn4(indexCol, player)

                val rowIndexLastToken = controller.getRowIndex(indexCol) + 1

                this.contents((rows-(rows-rowIndexLastToken))*2).background = setColor(player.color)
                counter = counter + 1
                var co = controller.gridRows*controller.gridColumns
                if (counter >= controller.gridRows*controller.gridColumns) {
                  outputText.text = "Unentschieden!"
                  gameStatus = GameStatus.DRAW
                } else {
                  nextPlayer
                }
              }
              if (win) {
                gameStatus=GameStatus.WIN
                println("winning")
                outputText.text = controller.getActualPlayer.name + " hat gewonnen!"
                goButton.text = "Revanche!"
              }
            }
        }
      }
      cols(indexCol) = col
      gameFieldUi.contents += col
    }
  }

  def setColor(c:Int):Color = c match {
    case 1 => Color.red
    case 2 => Color.green
  }

  /*
   *  Init Footer
   */
  val outputText = new Label{ text = "Bitte die Spielernamen eingeben und Spiel starten"}
  val footer = new FlowPanel(outputText)
  footer.preferredSize = new swing.Dimension(100,50)
  footer.background = Color.ORANGE

  /*
   *  Setup game
   */
  val quitAction = Action("Beenden") {System.exit(0)}
  val newGameAction = Action("Neu starten") {
    newGame
  }

  def initPlayers() {
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
      gameStatus = GameStatus.PLAYING
      controller.addPlayer(1, fieldplayer1.text)
      controller.addPlayer(2, fieldplayer2.text)

      startGame
    }
  }

  def startGame = outputText.text = controller.getActualPlayer.name + " ist an der Reihe"

  def newGame() {
    controller.newGrid(4, 4)
    controller.removePlayers
    initPlayers
    initField
    box.repaint()
  }

  def resetGame() {
    controller.reset
    controller.removePlayers
    initField
  }

  def nextPlayer {
    outputText.text = controller.getActualPlayer.name + " ist an der Reihe!"
  }

  /*
   *  Setup View
   */
  lazy val box = new BoxPanel(Orientation.Vertical) {
    contents += header
    contents += gameFieldUi
    contents += footer
  }

  def top = new MainFrame {
    title = "Hello to Connect 4!"
    //resizable = false

    initField

    contents = box

    menuBar = new MenuBar {
      contents += new Menu("Spiel")
      {
        contents += new MenuItem(newGameAction)
        contents += new MenuItem(quitAction)
      }
    }
  }
}

