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
        "my.inspections.DisallowJavaDate"),
      scapegoatCustomInspectionsClasspath := List(
        new File("inspections/target/scala-2.11/scapegoat-classes/").getAbsoluteFile))
    // FIXME: this dependency ought to be in scope 'provided', as it is
    // compile only, so is not needed at runtime.
    // Unfortunately, the current definition of the SBT 'scapegoat' task
    // includes a "clean", which means that SBT will delete the compiled
    // inspection classes just before attempting to run the inspections.
    // If the dependency here is "compile" then SBT knows to rebuild the
    // inspections before running them, however it does not add that
    // task dependency if this is marked "provided". I think that is a
    // bug in SBT.
    .dependsOn(inspections)

  lazy val inspections = Project("inspections", file("inspections"))
    .settings(
      libraryDependencies ++= List(
        "com.sksamuel.scapegoat" %% "scalac-scapegoat-plugin" % "1.1.0",
        "org.scala-lang" % "scala-compiler" % scalaV),
      scalaVersion := scalaV)
}
