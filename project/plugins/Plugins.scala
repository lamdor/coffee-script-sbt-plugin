import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
  val scriptedDep = "org.scala-tools.sbt" % "scripted" % "0.7.4"
  val databinder_repo = Resolver.url("Databinder Repository", new java.net.URL("http://databinder.net/repo"))(Resolver.ivyStylePatterns)
}
