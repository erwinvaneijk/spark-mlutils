import Dependencies._
import ReleaseTransformations._

lazy val commonSettings = Seq(
  name := "sparkutils",
  organization := "nl.oakhill.spark",
  version := (version in ThisBuild).value,
  scalaVersion := "2.11.11",
  javaOptions ++= Seq("-Xms512M",
    "-Xmx2048M",
    "-XX:MaxPermSize=2048M",
    "-XX:+CMSClassUnloadingEnabled"),
  fork in Test := true,
  parallelExecution in Test := false,
  ensimeScalaVersion in ThisBuild := scalaVersion.value,
  scalastyleConfig := baseDirectory.value / "project" / "scalastyle_config.xml",
  (scalastyleConfig in Test) := baseDirectory.value / "project" / "scalastyle_config.xml",
  resolvers ++= Seq(
    "Collective Media Bintray" at "https://dl.bintray.com/collectivemedia/releases",
    "JBoss Repository" at "http://repository.jboss.org/nexus/content/repositories/releases/",
    "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/",
    "Apache HBase" at "https://repository.apache.org/content/repositories/releases",
    "Twitter Maven Repo" at "http://maven.twttr.com/",
    "Spring Plugins" at "http://repo.spring.io/plugins-release",
    "scala-tools" at "https://oss.sonatype.org/content/groups/scala-tools",
    "sonatype-releases" at "https://oss.sonatype.org/content/repositories/releases/",
    "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
    "Second Typesafe repo" at "http://repo.typesafe.com/typesafe/maven-releases/",
    "Mesosphere Public Repository" at "http://downloads.mesosphere.io/maven",
    Resolver.sonatypeRepo("public")
  ),
  pomIncludeRepository := { x => false },
  publishMavenStyle := true,
  useGpg := true,
  // publish settings
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value) {
      Some("snapshots" at nexus + "content/repositories/snapshots")
    }
    else {
      Some("releases" at nexus + "service/local/staging/deploy/maven2")
    }
  },
  publishArtifact in Test := false,
  licenses := Seq("Apache License 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html")),
  homepage := Some(url("https://github.com/erwinvaneijk/sparkutils")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/erwinvaneijk/sparkutils"),
          "scm:git@github.com:erwinvaneijk/sparkutils.git"
        )
      ),
    developers := List(
      Developer(id="erwinvaneijk",
        name="Erwin van Eijk",
        email="235739+erwinvaneijk@users.noreply.github.com",
        url=url("https://gibhub.com/erwinvaneijk"))
          ),
  releaseCrossBuild := true, // true if you cross-build the project for multiple Scala versions
  releaseProcess := Seq[ReleaseStep](
    checkSnapshotDependencies,
    inquireVersions,
    runClean,
    runTest,
    setReleaseVersion,
    commitReleaseVersion,
    tagRelease,
    releaseStepCommand("publishSigned"),
    setNextVersion,
    commitNextVersion,
    releaseStepCommand("sonatypeReleaseAll"),
    pushChanges
  )
)

lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

compileScalastyle := scalastyle.in(Compile).toTask("").value

(compile in Compile) := ((compile in Compile) dependsOn compileScalastyle).value

lazy val testScalastyle = taskKey[Unit]("compileScalastyle")

testScalastyle := scalastyle.in(Test).toTask("").value

(test in Test) := ((test in Test) dependsOn testScalastyle).value

lazy val core = (project in file("core"))
  .settings(commonSettings,
      libraryDependencies ++= coreDependencies
      )

lazy val nlp = (project in file("nlp"))
  .dependsOn(core % "compile->compile;test->test")
  .settings(commonSettings,
      libraryDependencies ++= openNlpDependencies)


lazy val root = (project in file("."))
   .settings(commonSettings)
   .aggregate(core, nlp)

