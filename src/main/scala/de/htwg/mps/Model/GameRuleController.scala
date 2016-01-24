package de.htwg.mps.Model

/**
 * Created by dominikringgeler on 06.01.16.
 */
class GameRuleController (val grid:Grid){
  // check winning situation
  def checkConnectFour(column: Int, humanPlayer: HumanPlayer): Boolean = {
    val rowIndexLastToken = if(0<=grid.getRowIndex(column)) grid.getRowIndex(column)+1 else 0
    var win = checkFourInColumn(rowIndexLastToken, column, humanPlayer.color)

    if (!win) win = checkFourInRow(rowIndexLastToken, column, humanPlayer.color)
    if (!win) win = checkFourDiagonalLeftRightDown(rowIndexLastToken, column, humanPlayer.color)
    if (!win) win = checkFourDiagonalLeftRightUp(rowIndexLastToken, column, humanPlayer.color)
    win
  }

  // check    --XXXX---
  def checkFourInColumn(row:Int, col:Int, currentColor:Int): Boolean ={
    var countToken = 0
    var win = false
    if(row <= (grid.rows-4)) {
      for (rowIndex <- 0 until 4) {
        val cell = grid.getCell(rowIndex + row, col)
        val (c,w) = check(cell,currentColor,countToken)
        countToken = c
        if(w) win = w
      }
    }
    win
  }

  // check      -
  //            X
  //            X
  //            X
  //            X
  //            -
  def checkFourInRow(row:Int, col:Int, currentColor:Int): Boolean ={
    var countToken = 0
    var win = false
    for(colIndex <- -3 until 4){
      val tempCol = col+colIndex
      if (!win) {
        if(tempCol>=0) {
          val cell = grid.getCell(row, tempCol)
          val (c,w) = check(cell,currentColor,countToken)
          countToken = c
          if(w) win = w
        } else {
          countToken = 0
          win= false
        }
      }
    }
    win
  }

  // check
  //
  //          X
  //            X
  //              X
  //                X
  def checkFourDiagonalLeftRightDown(row:Int, col:Int, currentColor:Int): Boolean ={

    var countToken = 0
    var win = false

    for(index <- -3 until 4 ) {
      if (!win) {
        val tmpRow = index + row
        val tempCol = index + col
        if(tmpRow>=0 && tempCol>=0) {
          val cell = grid.getCell(tmpRow, tempCol)
          val (c, w) = check(cell, currentColor, countToken)
          countToken = c
          if (w) win = w
        } else {
          countToken = 0
          win= false
        }
      }
    }
    win
  }

  // check
  //
  //                X
  //              X
  //            X
  //          X
  def checkFourDiagonalLeftRightUp(row:Int, col:Int, currentColor:Int): Boolean ={
    var countToken = 0
    var win = false
    for(index <- -3 until 4){
      if(!win){
        val tmpRow = (-1*index)+row
        val tempCol = index+col

        if(tmpRow>=0 && tempCol>=0) {
          val cell = grid.getCell(tmpRow, tempCol)
          var result = check(cell, currentColor, countToken)
          countToken = result._1
          if (result._2) win = result._2
        }
        else {
            countToken = 0
            win= false
        }
      }
    }
    win
  }

  def check(cell:Cell, currentColor:Int, _count:Int) = {
    var count = _count
    var win=false
    if(cell!=null && cell.isSet){
      if (currentColor == cell.gameToken.color) {
        count = count + 1
        if (count >= 4)
          win = true
      } else count = 0
    } else count = 0
    (count, win)
  }
}