import sbt.Keys._

name := "connect4"

version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "junit" % "junit" % "4.8.1" % "test",
  "org.scala-lang" % "scala-swing" % "2.11.0-M7",
  "junit" % "junit" % "4.12",
  "org.specs2" %% "specs2" % "2.4.9" % "test",
  "org.scalaz.stream" % "scalaz-stream_2.11" % "0.8"
)