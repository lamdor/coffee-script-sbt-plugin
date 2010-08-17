import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val coffeeScriptCompile = "org.coffeescript" % "coffee-script-sbt-plugin" % "0.9.0"
}
