package de.htwg.mps.Model

import java.util
import scala.math.sqrt
import scala.util.Try
import scalaz.std.map

/**
 * Created by dominikringgeler on 03.01.16.
 */
class Grid(val cells: Vector[Cell], val rows:Int) {
  def this(r: Int, c: Int) = this(Vector.fill(r * c)(Cell.empty), r)

  /*
   setup grid
    */
  def reset = new Grid(rows, columns)
  def newGrid(r:Int, c:Int) = new Grid(r, c)
  /*
   get/set cell
    */
  def setCellWithRowCol(r:Int, c:Int,value:GameToken): Try[Grid] =
    Try(new Grid(cells.updated(r*columns+c, new Cell(value)), rows))

  def setCell(col: Int, value: GameToken): Try[Grid] =
    Try(new Grid(cells.updated(getPosition(col), new Cell(value)), rows))

  def getCell(row: Int, col: Int): Cell = {
    Try(cells.apply(row * columns + col)) getOrElse Cell.empty
  }

  // returns the first (from down to top) row index that isnt in use
  def getRowIndex(column: Int): Int = {
    var rowIndex:Int = rows-1

    while (getCell(rowIndex, column).isSet && 0<rowIndex)
      rowIndex = rowIndex-1
    rowIndex
  }

  // returns the position of a given row, col tuple in the vector list
  def getPosition(col:Int):Int = getRowIndex(col) * columns + col
  def columns:Int = cells.size/rows
}

