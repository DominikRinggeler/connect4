package de.htwg.mps.Model
/**
 * Created by dominikringgeler on 12.10.15.
 */
class HumanPlayer (val color: Int, val name: String){

  def makeTurn(column:Int): Unit ={

    val token = new GameToken(this, color)
    GameField.setFieldToken(column, token)
    
  }

}
