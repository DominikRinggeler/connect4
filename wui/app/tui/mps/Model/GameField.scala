package de.htwg.mps.Model

/**
 * Created by dominikringgeler on 12.10.15.
 */
object GameField {

  var columns = 0
  var rows = 0
  var fields = Array.ofDim[GameToken](rows,columns)

  def initializeField(rows: Int, columns: Int) = {

    this.rows= rows
    this.columns= columns

    fields = Array.ofDim[GameToken](columns,rows)

    for(rowIndex <- 0 until rows; columnIndex <- 0 until columns){
        fields(rowIndex)(columnIndex)=null;
    }
  }

  def getFieldStatus(row: Int,column: Int): Boolean = {
    if (column < 0 || column >= columns || row < 0 || row >= rows || fields(row)(column) == null) {
      return false
    }
    return true;
  }

  def getFieldToken(row: Int,column: Int):GameToken = {
    if (getFieldStatus(row,column) ==true) {
      return fields(row)(column);
    }
    null
  }

  def setFieldToken(column: Int,gameToken: GameToken): Boolean ={
    if (column >= 0 && column < this.columns){

      val rowIndex = getRowIndex(column)

      if(rowIndex<rows) {
        fields(rowIndex)(column)=gameToken
        true
      }
      else {
        false
      }
    }
    else {
      false
    }
  }

  def getRowIndex(column: Int): Int = {
    var rowIndex:Int =0

    while (getFieldStatus(rowIndex,column) && rowIndex < rows)
      rowIndex= rowIndex+1
    rowIndex
  }
}
