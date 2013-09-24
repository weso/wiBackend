name := "wiLodPortalPlay"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache,
  "com.google.code.gson" % "gson" % "2.2.4",
  "org.apache.commons" % "commons-math3" % "3.2",
  "org.apache.jena" % "jena-arq" % "2.10.1",
  "com.googlecode.xmemcached" % "xmemcached" % "1.4.1",
  "log4j" % "log4j" % "1.2.17"
)   

play.Project.playJavaSettings
