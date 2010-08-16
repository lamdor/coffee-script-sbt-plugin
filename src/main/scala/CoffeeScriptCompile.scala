package org.coffeescript

import sbt._
import scala.io.Source
import org.jcoffeescript.JCoffeeScriptCompiler
import java.io.File

trait CoffeeScriptCompile extends DefaultProject {
  lazy val compileCoffeeScript = task {
    log.info("Compiling CoffeeScript")

    val coffeeCompiler = new JCoffeeScriptCompiler()

    var coffeeScripts = ("src" / "main" / "coffee-script" ##) ** "*.coffee"
    coffeeScripts.get.foreach {
      coffeePath =>
      val coffeeFile = coffeePath.asFile
      val jsDestination = new File("src/main/generated-javascript/" + coffeePath.base + ".js")

      log.info("Compiling %s" format (coffeePath.relativePath))

      val coffeeSource =  Source.fromFile(coffeeFile).mkString
      val compiledJs = coffeeCompiler.compile(coffeeSource)

      new File(jsDestination.getParent).mkdirs

      FileUtilities.write(jsDestination, compiledJs, log)
    }

    None
  }
}
