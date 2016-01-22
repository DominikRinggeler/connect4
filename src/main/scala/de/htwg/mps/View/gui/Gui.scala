package de.htwg.mps.View.gui

import java.awt.{Color, Dimension}
import javax.swing.{JPanel, JFrame, Box}

import de.htwg.mps.Connect4._
import de.htwg.mps.Controller.{ChangeField, StartGame, MakeTurn, GameController}
import de.htwg.mps.Model.{HumanPlayer, GameStatus}

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
  var cols = new Array[BoxPanel](controller.gridColumns)

  listenTo(controller)
  reactions += {
    case e: ChangeField => setToken()
  }
  
  /*
   *  Init GameField
   */
  lazy val gameFieldUi = new FlowPanel()

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
            if (controller.gameStatus==GameStatus.PLAYING) {
              var isCorrect = false

              // make the players turn and check if his turn is correct
              controller.makeTurn(indexCol)

              setToken
            }
        }
      }
      cols(indexCol) = col
      gameFieldUi.contents += col
    }
  }

  def setColor(c:Int):Color = c match {
    case 0 => Color.white
    case 1 => Color.red
    case 2 => Color.green
    case 3 => Color.blue

  }

  /*
   *  Init Footer
   */
  val outputText = new Label{ text = controller.getActualPlayer.name + " ist an der Reihe" }
  val footer = new FlowPanel(outputText)
  footer.preferredSize = new swing.Dimension(100,50)
  footer.background = Color.ORANGE

  def printField() {
    val grid = controller.grid
    for (rowIndex <- (0 to controller.grid.rows-1)) {
      for (columnIndex <- 0 until controller.grid.getColumns) {
        val cell = controller.grid.getCell(rowIndex,columnIndex)
        val color = if(cell!=null && cell.isSet) cell.gameToken.color else 0
        cols.apply(columnIndex).contents(rowIndex*2).background = setColor(color)
      }
    }
  }

  def nextPlayersTurn() {
    outputText.text = controller.getActualPlayer.name + " ist an der Reihe!"
  }

  def setToken() {
      printField
      controller.gameStatus match {
        case GameStatus.WIN => outputText.text = controller.getActualPlayer.name + " hat gewonnen!"
        case GameStatus.DRAW => outputText.text = "Unentschieden!"
        case GameStatus.PLAYING => nextPlayersTurn
      }
  }

  /*
   *  Setup View
   */
  lazy val box = new BoxPanel(Orientation.Vertical) {
    contents += gameFieldUi
    contents += footer
  }

  // Actions
  val quitAction = Action("Beenden") { System.exit(0) }
  val newGameAction = Action("Neu starten") { controller.reset() }
  val set2PlayersAction = Action("2 Spieler") { controller.set2Player() }
  val set3PlayersAction = Action("3 Spieler") { controller.set3Player() }

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
      contents += new Menu("Spieler")
      {
        contents += new MenuItem(set2PlayersAction)
        contents += new MenuItem(set3PlayersAction)
      }
    }
  }
}