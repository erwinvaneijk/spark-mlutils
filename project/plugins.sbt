logLevel := Level.Warn

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// Support for building fat jars, which are excellent for spark.
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")

// Support for signed releases
addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0-M1")

// Helper for creating releases
addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.6")

// To get everything in the repository
addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.0")

// Enable coverage analysis
addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.5.1")