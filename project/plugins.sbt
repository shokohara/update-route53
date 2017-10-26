addSbtPlugin("com.lucidchart" % "sbt-scalafmt" % "1.10")
addSbtPlugin("com.typesafe.sbt" % "sbt-git" % "0.9.3")
addSbtPlugin("com.eed3si9n" % "sbt-buildinfo" % "0.7.0")
resolvers += Resolver.url("bintray-kipsigman-sbt-plugins", url("http://dl.bintray.com/kipsigman/sbt-plugins"))(
  Resolver.ivyStylePatterns)
addSbtPlugin("io.get-coursier" % "sbt-coursier" % "1.0.0-RC11")
addSbtPlugin("com.typesafe.sbt" % "sbt-native-packager" % "1.3.1")
