name := """optobots"""

version := "0.0.1"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.3.11",
  "com.typesafe.play" %% "play-ws" % "2.3.8",
  "com.typesafe.play" %% "play-json" % "2.3.8",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.11" % "test",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test")


fork in run := true
