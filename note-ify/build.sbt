name := """note-ify"""
organization := "org.hvm"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala, PlayEbean)

scalaVersion := "2.11.11"

libraryDependencies ++= Seq(
  guice,
  filters,
  "org.postgresql" % "postgresql" % "9.4.1207",
  "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
)

