android.Plugin.androidBuild
platformTarget in Android := "android-21"

name := """orm-android-scala"""

scalaVersion := "2.11.6"
javacOptions ++= Seq("-target", "1.7", "-source", "1.7") // so we can build with java8

// a shortcut
run <<= run in Android

resolvers ++= Seq(
  Resolver.sonatypeRepo("releases"),
  "jcenter" at "http://jcenter.bintray.com"
)

// add linter
scalacOptions in (Compile, compile) ++=
  (dependencyClasspath in Compile).value.files.map("-P:wartremover:cp:" + _.toURI.toURL) ++
  Seq("-P:wartremover:traverser:macroid.warts.CheckUi")

libraryDependencies ++= Seq(
  aar("org.macroid" %% "macroid" % "2.0.0-M4"),
  "com.android.support" % "support-v4" % "22.1.1",
  "com.typesafe.slick" %% "slick" % "3.0.0",
  "org.sqldroid" % "sqldroid" % "1.0.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  compilerPlugin("org.brianmckenna" %% "wartremover" % "0.11")
)

proguardScala in Android := true

// Generic ProGuard rules
proguardOptions in Android ++= Seq(
  "-ignorewarnings",
  "-keep class scala.Dynamic"
)
