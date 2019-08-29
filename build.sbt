import Dependencies._

ThisBuild / scalaVersion     := "2.12.0"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "com.example"
ThisBuild / organizationName := "example"


lazy val core = (project in file("."))
  .settings(
    name := "prototype-scala-topic-listeners",
    assemblyJarName in assembly := "tdr-api-lambda.jar",
    libraryDependencies += scalaTest % Test,
    resolvers += Resolver.bintrayRepo("jarlakxen", "maven"),
    libraryDependencies += "com.softwaremill.sttp" %% "core" % "1.6.4",
    libraryDependencies += "com.softwaremill.sttp" %% "circe" % "1.6.4",
    libraryDependencies += "ca.ryangreen" % "apigateway-generic-java-sdk" % "1.3",
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.11.1",
      "io.circe" %% "circe-generic" % "0.11.1",
      "io.circe" %% "circe-parser" % "0.11.1"
    )
    )

lazy val checksum = (project in file("checksum"))
  .settings(
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.tomakehurst" % "wiremock-jre8" % "2.24.1" % Test,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.11.1",
      "io.circe" %% "circe-generic" % "0.11.1",
      "io.circe" %% "circe-parser" % "0.11.1"
    )
  ).dependsOn(core)


lazy val virusscan = (project in file("virusscan"))
  .settings(
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.tomakehurst" % "wiremock-jre8" % "2.24.1" % Test,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.11.1",
      "io.circe" %% "circe-generic" % "0.11.1",
      "io.circe" %% "circe-parser" % "0.11.1"
    )
  ).dependsOn(core)

lazy val fileformat = (project in file("fileformat"))
  .settings(
    libraryDependencies += "com.amazonaws" % "aws-lambda-java-core" % "1.1.0",
    libraryDependencies += scalaTest % Test,
    libraryDependencies += "com.github.tomakehurst" % "wiremock-jre8" % "2.24.1" % Test,
    libraryDependencies ++= Seq(
      "io.circe" %% "circe-core" % "0.11.1",
      "io.circe" %% "circe-generic" % "0.11.1",
      "io.circe" %% "circe-parser" % "0.11.1"
    )
  ).dependsOn(core)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
