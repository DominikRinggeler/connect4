package de.htwg.mps.Model
/**
 * Created by dominikringgeler on 12.10.15.
 */
class HumanPlayer (val color: Int, val name: String){

  def makeTurn(column:Int): Boolean ={

    val token = new GameToken(this, color)
    val isCorrect = GameField.setFieldToken(column-1, token)
    isCorrect
  }

  override def toString():String = {
     "Name: " + name + " - Spielstein: " + color
  }
}
