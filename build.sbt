import sbt.Keys.dependencyOverrides

lazy val versions = new {
  val finatra = "18.11.0"
  val slf4j = "1.7.25"
  val jackson = "2.9.7"
  val guava = "27.0.1-jre"
  val caffeine = "2.6.2"
  val guice = "4.2.2"
  val findbugs = "3.0.2"
  val jodatime = "2.10.1"
}

val jacksonDependencies = Seq(
  "com.fasterxml.jackson.core" % "jackson-annotations" % versions.jackson,
  "com.fasterxml.jackson.core" % "jackson-core" % versions.jackson,
  "com.fasterxml.jackson.core" % "jackson-databind" % versions.jackson,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jdk8" % versions.jackson,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-jsr310" % versions.jackson,
  "com.fasterxml.jackson.datatype" % "jackson-datatype-joda" % versions.jackson,
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-cbor" % versions.jackson,
  "com.fasterxml.jackson.dataformat" % "jackson-dataformat-yaml" % versions.jackson,
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % versions.jackson
)

val logsDependencies = Seq(
  "org.slf4j" % "slf4j-api" % versions.slf4j,
  "ch.qos.logback" % "logback-classic" % "1.2.3",
  "ch.qos.logback.contrib" % "logback-json-classic" % "0.1.5" excludeAll
    ExclusionRule("ch.qos.logback"),
  "ch.qos.logback.contrib" % "logback-jackson" % "0.1.5"
)

val versionControl = Seq(
  "com.google.inject" % "guice" % versions.guice,
  "com.google.code.findbugs" % "jsr305" % versions.findbugs,
  "joda-time" % "joda-time" % versions.jodatime,
  "com.github.ben-manes.caffeine" % "caffeine" % versions.caffeine,
  "com.google.guava" % "guava" % versions.guava
) ++ logsDependencies ++ jacksonDependencies

val projectSettings = Seq(
  scalaVersion := "2.12.7",
  scalacOptions in ThisBuild ++= Seq(
    "-feature",
    "-unchecked",
    "-deprecation",
    "-language:postfixOps",
    "-language:implicitConversions"
  ),
  conflictManager := ConflictManager.strict,
  resolvers ++= Seq(
    Resolver.mavenLocal,
  ),
  dependencyOverrides := versionControl
)

lazy val `hello-world` = (project in file("hello-world"))
  .settings(projectSettings: _*)
  .settings(
    name := "hello-world",
    organization := "com.idzivinskyi",
    version := "0.0.1",
    parallelExecution in Test := false,
    mainClass in(Compile, run) := Some("com.idzivinskyi.Main"),
    libraryDependencies ++= Seq(
      "com.google.inject" % "guice" % versions.guice,
      "com.twitter" %% "finatra-http" % versions.finatra,
      "com.twitter" %% "finatra-httpclient" % versions.finatra,
    ) ++ logsDependencies
  )