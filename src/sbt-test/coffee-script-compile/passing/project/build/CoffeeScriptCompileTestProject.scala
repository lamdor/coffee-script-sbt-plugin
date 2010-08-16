import sbt._
import org.coffeescript.CoffeeScriptCompile

import scala.io.Source
import java.io.File

class FailingProject(info: ProjectInfo) extends DefaultProject(info) with CoffeeScriptCompile {

  lazy val checkContains = task { args =>
    val file = Source.fromFile(new File(args(0)))
    val expectedLine = args(1) // .slice(1, args.length).mkString(" ")
    findLine(file, expectedLine)
  }

  private def findLine(file: Source, expectedLine: String) = task {
    println(expectedLine)
    val matchedLines = file.getLines.filter { line => line.contains(expectedLine) }
    if (matchedLines.hasNext) {
      None
    } else {
      Some("Expected '%s' to contain '%s' but didn't" format (file.mkString, expectedLine) )
    }
  }
}
