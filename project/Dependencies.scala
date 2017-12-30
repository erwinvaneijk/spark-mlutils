import sbt._

object Dependencies {
  lazy val scalaLoggingVersion = "3.4.0"
  lazy val sparkTestingBaseVersion = "2.2.0_0.7.2"
  lazy val sparkVersion = "2.2.0"
  lazy val scalaTestVersion = "3.0.0"
  lazy val scalaTimeVersion = "2.12.0"
  lazy val apacheLoggingVersion = "2.9.0"
  lazy val apacheLuceneVersion = "6.6.0"
  lazy val apacheHiveVersion = "2.3.0"
  lazy val pentahoVersion = "5.1.5-jhyde"

  val scalaLogging =
    "com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion
  val sparkSql =
    "org.apache.spark" %% "spark-sql" % sparkVersion
  val sparkMlLib =
    "org.apache.spark" %% "spark-mllib" % sparkVersion
  val scalaTic =
    "org.scalactic" %% "scalactic" % scalaTestVersion
  val scalaTest =
    "org.scalatest" %% "scalatest" % scalaTestVersion
  val sparkTesting =
    "com.holdenkarau" %% "spark-testing-base" % sparkTestingBaseVersion
  val scalaTime =
    "com.github.nscala-time" %% "nscala-time" % scalaTimeVersion
  val apacheHive =
    "org.apache.hive" % "hive-exec" % apacheHiveVersion
  val aggDesignerAlgorithm =
    "org.pentaho" % "pentaho-aggdesigner-algorithm" % pentahoVersion
  val apacheLoggingCore =
    "org.apache.logging.log4j" % "log4j-core" % apacheLoggingVersion
  val apacheLoggingJul =
    "org.apache.logging.log4j" % "log4j-jul" % apacheLoggingVersion
  val apacheCommonsNet =
    "commons-net" % "commons-net" % "3.1"
  val apacheLucene =
    "org.apache.lucene" % "lucene-analyzers-common" % apacheLuceneVersion

  val testDependencies = Seq(
    scalaTic % Test,
    scalaTest % Test
    )

  val loggingDependencies = Seq(
    scalaLogging,
    apacheLoggingCore % Test,
    apacheLoggingJul % Test
    )

  val sparkDependencies = Seq(
    sparkSql,
    sparkMlLib,
    apacheCommonsNet,
    sparkTesting % Test,
    apacheHive % Test,
    aggDesignerAlgorithm % Test
    )

  val luceneDependencies = Seq(
    apacheLucene
    )

  val coreDependencies = sparkDependencies ++
    loggingDependencies ++ testDependencies
}
