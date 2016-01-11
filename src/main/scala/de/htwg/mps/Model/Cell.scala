package de.htwg.mps.Model

/**
 * Created by dominikringgeler on 03.01.16.
 */
class Cell(val gameToken: GameToken) {
  def isSet = gameToken != null
}