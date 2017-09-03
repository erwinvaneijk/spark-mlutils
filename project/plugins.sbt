logLevel := Level.Warn

addSbtPlugin("org.scalastyle" %% "scalastyle-sbt-plugin" % "1.0.0")

// Support for building fat jars, which are excellent for spark.
addSbtPlugin("com.eed3si9n" % "sbt-assembly" % "0.14.5")