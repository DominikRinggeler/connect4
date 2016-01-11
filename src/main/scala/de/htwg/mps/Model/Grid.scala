package de.htwg.mps.Model

import java.util
import scala.math.sqrt

/**
 * Created by dominikringgeler on 03.01.16.
 */
class Grid(val cells: Vector[Cell], val rows:Int) {
  def this(r: Int, c: Int) = this(Vector.fill(r * c)(new Cell(null)), r)

  // setup grid
  def reset = new Grid(rows, columns)
  def newGrid(r:Int, c:Int) = new Grid(r, c)
  // get/set cell
  def setCellWithRowCol(r:Int, c:Int,value:GameToken): Grid = new Grid(cells.updated(r*columns+c, new Cell(value)), rows)
  def setCell(col: Int, value: GameToken): Grid = if (getPosition(col) <= rows*columns)
      new Grid(cells.updated(getPosition(col), new Cell(value)), rows)
    else
      null
  def getCell(row: Int, col: Int):Cell = if(0<=row && row<rows && 0<=col && col<columns) cells.apply(row * columns + col) else null
  def getCells: Vector[Cell] = cells
  def getRowIndex(column: Int): Int = {
    var rowIndex:Int = rows-1
    var cell = getCell(rowIndex, column)
    while (getCell(rowIndex, column)!=null && getCell(rowIndex, column).gameToken!=null && 0<=rowIndex)
      rowIndex = rowIndex-1
    rowIndex
  }
  def getPosition(col:Int):Int = getRowIndex(col) * columns + col
  def columns:Int = cells.size/rows

  def printField() = {

    var col = columns

    println()
    for (i <- 0 until columns)
      print(" " + (i+1).toString)

    println()
    for (rowIndex <- (0 to rows-1)) {
      for (columnIndex <- 0 until columns) {

        val token = getCell(rowIndex, columnIndex).gameToken
        val stringVar = if(token==null) " " else token.color

        print("|" + stringVar)
      }
      print("|")
      println()
    }
    println()
  }

}