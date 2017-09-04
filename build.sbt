

name := "mlutils"

organization := "nl.oakhill.spark"

version := "0.1.2"

scalaVersion := "2.11.11"

fork in Test := true

javaOptions ++= Seq("-Xms512M", "-Xmx2048M", "-XX:MaxPermSize=2048M", "-XX:+CMSClassUnloadingEnabled")

parallelExecution in Test := false

scalastyleConfig := baseDirectory.value / "project" / "scalastyle_config.xml"

lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

compileScalastyle := scalastyle.in(Compile).toTask("").value

(compile in Compile) := ((compile in Compile) dependsOn compileScalastyle).value

lazy val testScalastyle = taskKey[Unit]("compileScalastyle")

testScalastyle := scalastyle.in(Test).toTask("").value

(test in Test) := ((test in Test) dependsOn testScalastyle).value

(scalastyleConfig in Test) := baseDirectory.value / "project" / "scalastyle_config.xml"

resolvers += "Collective Media Bintray" at "https://dl.bintray.com/collectivemedia/releases"

libraryDependencies ++= Seq(
  "com.jsuereth" %% "scala-arm" % "1.4",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
  "org.apache.spark" %% "spark-mllib" % "2.2.0",
  "org.apache.spark" %% "spark-sql" % "2.2.0",
  "com.github.nscala-time" %% "nscala-time" % "2.12.0",
  "org.apache.lucene" % "lucene-analyzers-common" % "6.6.0",
  "com.holdenkarau" %% "spark-testing-base" % "2.2.0_0.7.2" % "test",
  "org.apache.hive" % "hive-exec" % "2.3.0" % "test",
  "org.pentaho" % "pentaho-aggdesigner-algorithm" % "5.1.5-jhyde" % "test",
  "org.apache.logging.log4j" % "log4j-core" % "2.9.0" % "test",
  "org.apache.logging.log4j" % "log4j-jul" % "2.9.0" % "test",
  "commons-net" % "commons-net" % "3.1",
  "org.scalactic" %% "scalactic" % "3.0.0" % "test",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test"
    exclude("org.scala-lang", "scala-reflect")
    exclude("org.scala-lang.modules", "scala-xml_2.11")
)

pomIncludeRepository := { x => false }

resolvers ++= Seq(
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
)

publishMavenStyle := true

// publish settings
publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

licenses := Seq("Apache License 2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0.html"))

homepage := Some(url("https://github.com/erwinvaneijk/spark-mlutils"))

scmInfo := Some(
  ScmInfo(
    url("https://github.com/erwinvaneijk/spark-mlutils"),
      "scm:git@github.com:erwinvaneijk/spark-mlutils.git"
    )
  )

developers := List(
  Developer(id="erwinvaneijk",
            name="Erwin van Eijk",
            email="erwin.vaneijk@gmail.com",
            url=url("https://gibhub.com/erwinvaneijk"))
)


import ReleaseTransformations._

releaseCrossBuild := true // true if you cross-build the project for multiple Scala versions
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
