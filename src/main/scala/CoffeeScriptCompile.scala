package org.coffeescript

import sbt._
import scala.io.Source
import java.io.File

trait CoffeeScriptCompile extends BasicScalaProject {

  private val coffeeScriptConfig = config("coffeescript") hide 

  val coffeeScriptCompilerDep = "org.jcoffeescript" % "jcoffeescript" % "1.0" % "coffeescript" from "http://cloud.github.com/downloads/yeungda/jcoffeescript/jcoffeescript-1.0.jar"
 
  protected def coffeeScriptDirectoryPathFinder = {"src" / "main" / "coffee-script"}
  protected def coffeeScriptCompiledOutputPath = {
    if (mainArtifact.extension == "war") {
      "src" / "main" / "webapp" / "javascript"
    } else {
      "src" / "main" / "generated-javascript"
    }
  }
  protected def coffeeScriptCompiledOuputDirectory =
    coffeeScriptCompiledOutputPath.asFile.getPath

  lazy val coffeeScriptPaths = (coffeeScriptDirectoryPathFinder ##) ** "*.coffee"

  override def watchPaths = super.watchPaths +++ coffeeScriptPaths

  private lazy val coffeeScriptCompilerClasspath = fullClasspath(coffeeScriptConfig)

  private lazy val coffeeScriptCompiler =
    Class.forName(
      "org.jcoffeescript.JCoffeeScriptCompiler"
      , true
      , ClasspathUtilities.toLoader(coffeeScriptCompilerClasspath)
    ).getConstructor()
    .newInstance()
    .asInstanceOf[{ def compile(source:String):String }]

  lazy val compileCoffeeScript = dynamic(compileCoffeeTasks) describedAs
    "Compiles CoffeeScript files."
  
  def compileCoffeeTasks = task { None } dependsOn ({
    coffeeScriptPaths.get.map { coffeePath =>
      val coffeeFile = coffeePath.asFile
      val jsDestination = new File(coffeeScriptCompiledOuputDirectory,
        "\\.coffee$".r.replaceFirstIn(coffeePath.relativePath, ".js"))
      log.info(Path.fromFile(jsDestination).toString)
      log.info(coffeePath.toString)
      fileTask(Path.fromFile(jsDestination) from coffeePath) {
        log.info("CoffeeScript compiling %s" format (coffeePath.relativePath))

        val coffeeSource =  Source.fromFile(coffeeFile).mkString

        val compiledJs = coffeeScriptCompiler.compile(coffeeSource)

        new File(jsDestination.getParent).mkdirs()
        FileUtilities.write(jsDestination, compiledJs, log)
      }
    }
  }.toSeq: _*)
}
