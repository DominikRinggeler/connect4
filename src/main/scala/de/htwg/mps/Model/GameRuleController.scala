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
  def checkFourInRow(row:Int, col:Int, currentColor:Int): Boolean ={
    var countToken = 0
    var win = false
    for(colIndex <- -3 until 4){
      val cell = grid.getCell(row, col+colIndex)
      val (c,w) = check(cell,currentColor,countToken)
      countToken = c
      if(w) win = w
    }
    win
  }
  def checkFourDiagonalLeftRightDown(row:Int, col:Int, currentColor:Int): Boolean ={
    var countToken = 0
    var win = false

    for(index <- -3 until 4 ){
      val cell = grid.getCell(index+row, index+col)
      val (c,w) = check(cell,currentColor,countToken)
      countToken = c
      if(w) win = w
    }
    win
  }
  def checkFourDiagonalLeftRightUp(row:Int, col:Int, currentColor:Int): Boolean ={
    var countToken = 0
    var win = false
    for(index <- -3 until 4){
      val cell = grid.getCell((-1*index)+row, index+col)
      val (c,w) = check(cell,currentColor,countToken)
      countToken = c
      if(w) win = w
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