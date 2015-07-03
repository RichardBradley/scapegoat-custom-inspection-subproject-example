import com.sksamuel.scapegoat.sbt.ScapegoatSbtPlugin.autoImport._
import sbt.Keys._
import sbt._

object ScapegoatCustomInspectionsExample extends Build {

  val scalaV = "2.11.6"

  lazy val project = Project("ScapegoatCustomInspectionsExample", file("."))
    // "aggregate" causes these child projects to be built and tested whenever
    // the parent project is built and tested.
    .aggregate(inspections)
    .settings(
      scalaVersion := scalaV,
      scapegoatCustomInspections := List(
        "msw.mpaf.inspections.logging.LoggingArgumentsMismatch"),
      scapegoatCustomInspectionsClasspath := List(
        new File("inspections/target/scala-2.11/classes/").getAbsoluteFile))

  lazy val inspections = Project("inspections", file("inspections"))
    .settings(
      libraryDependencies ++= List(
        "com.sksamuel.scapegoat" %% "scalac-scapegoat-plugin" % "1.1.0",
        "org.scala-lang" % "scala-compiler" % scalaV % "provided"),
      scalaVersion := scalaV)
}
