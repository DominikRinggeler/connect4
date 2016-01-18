package tui.mps

import java.util.{Observable, Observer}

import de.htwg.mps.Controller.GameController
import de.htwg.mps.Model.GameField

class Tui (var controller: GameController) extends Observer{

  override def update(o: Observable, arg: scala.Any): Unit = ???
  var win = false

  def printField():String = {

    var result = ""

    println()
    for (i <- 0 until GameField.columns)
      result = result +" " + (i+1).toString

    result = result +" \n "

    for (rowIndex <- (0 to GameField.rows-1).reverse) {
      for (columnIndex <- 0 until GameField.columns) {

        var gtoken = GameField.getFieldToken(rowIndex,columnIndex)
        var stringVar = if(gtoken==null) "  " else gtoken.color

        result = result + "|" + stringVar
      }
      result = result + "|"
      result = result +" \n "
    }
    result = result +" \n "

    result
  }

  def processInputLine(line:String): String = {


    if (win==true){
      val rows = 8
      val columns = 8
      GameField.initializeField(rows, columns)
      win == false
    }


    // actualize field

    var result = ""
    printField()


    result = printField()

    try {
      if (line != "printfield" &&line.toInt > 0 && line.toInt < 8){
        win = makeTurnAndCheck(line)
        result = printField()
      }
    } catch {
      case e: Exception => 0
    }


    if (win){
      result = result +" \n  Das Spiel ist Aus! "+controller.getName + " hat gewonnen."

    }
    else {

      result = result +" \n "+ controller.getName + " ist an der Reihe, bitte Spalte wählen..."

    }
    result

  }

  def makeTurnAndCheck(line:String):Boolean = {
    var isCorrect = false
    do {
      try {
        isCorrect = controller.makeTurn(line.toInt-1)
      } catch {
        case e: Exception =>
      }
    }while (!isCorrect)
    return controller.checkConnectFour(line.toInt-1)
  }
}
