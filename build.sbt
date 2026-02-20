ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.8.1"

lazy val root = (project in file("."))
  .enablePlugins(JmhPlugin)
  .settings(
    name := "opaque-codec",
    libraryDependencies ++= Seq(
      "io.spray" %% "spray-json" % "1.3.6" cross CrossVersion.for3Use2_13,
      "org.scalameta" %% "munit" % "1.2.2" % Test
    )
  )
