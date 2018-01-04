import sbt._

object Dependencies {
  lazy val scalaLoggingVersion = "3.4.0"
  lazy val sparkTestingBaseVersion = "2.2.0_0.7.2"
  lazy val sparkVersion = "2.2.0"
  lazy val scalaTestVersion = "3.0.0"
  lazy val scalaTimeVersion = "2.12.0"
  lazy val apacheLoggingVersion = "2.10.0"
  lazy val apacheLuceneVersion = "6.6.0"
  lazy val apacheHiveVersion = "2.3.0"
  lazy val pentahoVersion = "5.1.5-jhyde"
  lazy val openNlpVersion = "1.8.4"

  val scalaLogging =
    ("com.typesafe.scala-logging" %% "scala-logging" % scalaLoggingVersion)
      .exclude("log4j", "log4j").exclude("org.slf4j", "slf4j-log4j12")
  val sparkSql =
    ("org.apache.spark" %% "spark-sql" % sparkVersion)
    .exclude("commons-net", "commons-net")
    .exclude("log4j", "log4j").exclude("org.slf4j", "slf4j-log4j12")
  val sparkMlLib =
    ("org.apache.spark" %% "spark-mllib" % sparkVersion)
    .exclude("commons-net", "commons-net")
    .exclude("log4j", "log4j").exclude("org.slf4j", "slf4j-log4j12")
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
  private val apacheLoggingOrganization = "org.apache.logging.log4j"
  val apacheLoggingCore  =
    apacheLoggingOrganization % "log4j-core" % apacheLoggingVersion
  val apacheLoggingApi   =
    apacheLoggingOrganization % "log4j-api" % apacheLoggingVersion
  val apacheLoggingSlf4j =
    (apacheLoggingOrganization % "log4j-slf4j-impl" % apacheLoggingVersion)
      .exclude("log4j", "log4j").exclude("org.slf4j", "slf4j-log4j12")
  val apacheCommonsNet   =
    "commons-net" % "commons-net" % "3.1"
  val apacheLucene       =
    "org.apache.lucene" % "lucene-analyzers-common" % apacheLuceneVersion

  val openNlp =
    "org.apache.opennlp" % "opennlp" % openNlpVersion

  val testDependencies = Seq(
    scalaTic % Test,
    scalaTest % Test
    )

  val loggingDependencies = Seq(
    scalaLogging,
    apacheLoggingApi % Test,
    apacheLoggingCore % Test,
    apacheLoggingSlf4j % Test
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

  val openNlpDependencies = Seq(
    openNlp,
    apacheLucene
  )

  val coreDependencies = sparkDependencies ++
    loggingDependencies ++ testDependencies
}
