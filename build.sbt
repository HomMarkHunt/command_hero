name := """command_hero"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.6"

resolvers += Resolver.url("Edulify Repository", url("http://edulify.github.io/modules/releases/"))(Resolver.ivyStylePatterns)

libraryDependencies ++= Seq(
  filters,
  javaJdbc,
  cache,
  javaWs,
  "org.mybatis" % "mybatis" % "3.2.8",
  "org.postgresql" % "postgresql" % "9.4-1201-jdbc41",
  "com.edulify" %% "play-hikaricp" % "2.0.2",
  "org.projectlombok" % "lombok" % "1.16.2",
  "commons-io" % "commons-io" % "2.2",
  "commons-lang" % "commons-lang" % "2.2",
  "commons-codec" % "commons-codec" % "1.4",
  "log4j" % "log4j" % "1.2.17",
  "org.webjars" %% "webjars-play" % "2.3.0",
  "org.webjars" % "bootstrap" % "3.2.0",
  "commons-codec" % "commons-codec" % "1.4"
)

// Play provides two styles of routers, one expects its actions to be injected, the
// other, legacy style, accesses its actions statically.
routesGenerator := InjectedRoutesGenerator
