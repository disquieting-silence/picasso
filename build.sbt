enablePlugins(ScalaJSPlugin)

name := "Scala.js tutorial"
scalaVersion := "2.13.1"

libraryDependencies += "org.scala-js" %%% "scalajs-dom" % "1.1.0"

scalaJSUseMainModuleInitializer := true

/* For jsdom to run in node (resolved problem with "window") */
jsEnv := new org.scalajs.jsenv.jsdomnodejs.JSDOMNodeJSEnv()

/* For u-test */
libraryDependencies += "com.lihaoyi" %%% "utest" % "0.7.4" % "test"
testFrameworks += new TestFramework("utest.runner.Framework")
