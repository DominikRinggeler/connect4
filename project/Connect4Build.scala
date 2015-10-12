import sbt._
import sbt.Keys._

object Connect4Build extends Build {

  lazy val connect4 = Project(
    id = "connect4",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "connect4",
      organization := "de.htwg.mps",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.10.2"
      // add other settings here
    )
  )
}
