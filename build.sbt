name := """new"""
organization := "com.nexxo"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.13.0"

libraryDependencies += guice
libraryDependencies ++= Seq("mysql" % "mysql-connector-java" % "8.0.17")
// https://mvnrepository.com/artifact/io.github.keetraxx/recaptcha
//libraryDependencies += "io.github.keetraxx" % "recaptcha" % "0.2"
libraryDependencies += "com.nappin" %% "play-recaptcha" % "2.4"
// https://mvnrepository.com/artifact/org.scala-sbt/sbt
//libraryDependencies += "org.scala-sbt" % "sbt" % "1.2.8" % "provided"
// https://mvnrepository.com/artifact/guru.nidi/graphviz-java
libraryDependencies += "guru.nidi" % "graphviz-java" % "0.11.0"

// https://mvnrepository.com/artifact/net.sourceforge.plantuml/plantuml
libraryDependencies += "net.sourceforge.plantuml" % "plantuml" % "8039"
libraryDependencies += "org.beangle.sas" % "beangle-sas-core" % "0.6.5"
libraryDependencies += "org.webjars.bower" % "compass-mixins" % "0.12.7"
libraryDependencies += "org.webjars.bower" % "bootstrap-sass" % "3.3.6"
// https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.5" % Test
