name := "bigdata-ios"
version := "0.1"
resolvers += Resolver.sonatypeRepo("releases")

scalaVersion := "2.11.12"

sbtVersion := "1.2.8"

val sparkVersion = "2.3.3"
val beamVersion = "2.12.0"
val scioVersion = "0.8.0-alpha1"
val paradiseVersion = "2.1.0"

initialize in Compile ~= { _ => System.setProperty(
  "override.type.provider",
  "fr.marouni.bigdata.io.scio.bigquery.MyOverrideTypeProvider")
}

// scalacOptions += "-Ymacro-debug-lite"

libraryDependencies += "org.apache.spark" %% "spark-core" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-sql" % sparkVersion
libraryDependencies += "org.apache.spark" %% "spark-streaming" % sparkVersion


libraryDependencies += "org.apache.beam" % "beam-sdks-java-core" % beamVersion
libraryDependencies += "org.apache.beam" % "beam-runners-google-cloud-dataflow-java" % beamVersion
libraryDependencies += "org.apache.beam" % "beam-runners-direct-java" % beamVersion
libraryDependencies += "org.apache.beam" % "beam-runners-spark" % beamVersion

libraryDependencies += "com.spotify" %% "scio-core" % scioVersion
libraryDependencies += "com.spotify" %% "scio-bigquery" % scioVersion
libraryDependencies += "com.spotify" %% "scio-avro" % scioVersion
libraryDependencies += "com.spotify" %% "scio-spanner" % scioVersion
libraryDependencies += "com.spotify" %% "scio-parquet" % scioVersion
libraryDependencies += "com.spotify" %% "scio-jdbc" % scioVersion
libraryDependencies += "com.spotify" %% "scio-cassandra2" % scioVersion
libraryDependencies += "com.spotify" %% "scio-bigtable" % scioVersion
libraryDependencies += "com.spotify" %% "scio-elasticsearch6" % scioVersion

libraryDependencies += "org.apache.avro" % "avro-ipc" % "1.8.2"
libraryDependencies += "org.apache.avro" % "avro-ipc" % "1.8.2" % "test"
libraryDependencies += "org.apache.avro" % "avro" % "1.8.2" % "test"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

