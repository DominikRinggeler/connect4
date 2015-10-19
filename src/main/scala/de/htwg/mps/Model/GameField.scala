package de.htwg.mps.Model

/**
 * Created by dominikringgeler on 12.10.15.
 */
class GameField(rows:Int,columns: Int) {

  val fields = Array.ofDim[GameToken](columns,rows)

  for(rowIndex <- 0 until rows){
    for(columnIndex <- 0 until columns){
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
        fields(row)(column);
    }
      null
  }

  def setFieldStatus(column: Int,gameToken: GameToken): Unit ={
    if (column < 0 || column >= columns){
      var rowIndex =0;
      while (getFieldStatus(rowIndex,column)){
        rowIndex=rowIndex+1;
      }
      fields(rowIndex)(column)=gameToken
    /*
      for(index <- 0 until rows; if(!getFieldStatus(index,column)))
         getFieldStatus(index+1)
    }
    */
    }
  }
}
