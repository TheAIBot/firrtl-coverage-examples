scalaVersion := "2.12.12"

scalacOptions := Seq("-Xsource:2.11")

resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.sonatypeRepo("releases")
)

libraryDependencies += "edu.berkeley.cs" %% "chiseltest" % "0.3-SNAPSHOT"
libraryDependencies += "edu.berkeley.cs" %% "chisel3" % "3.4-SNAPSHOT"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.5" % "test"
