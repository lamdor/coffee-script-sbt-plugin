coffee-script-sbt-plugin
==========

This is a [simple-build-tool](http://simple-build-tool.googlecode.com/) plugin for compiling [CoffeeScript](http://coffeescript.org) files into their javascript conterparts. It uses the excellent [JCoffeeScript](http://github.com/yeungda/jcoffeescript/).

## Usage ##
Run the`compile-coffee-script` task to compile all your CoffeeScript files under `src/main/cofffee-script/` into JavaScript. For web projects the files are compiled to under `src/main/webapp/javascript`, for other projects they will compiled to `src/main/generated-javascript/`. The file structure under the source directory will be maintained.

The source and output paths can be configured via coffeeScriptDirectoryPathFinder and coffeeScriptCompiledOuputDirectory properties. i.e.

    class Project(info: ProjectInfo) extends DefaultProject(info) {
      override def coffeeScriptDirectoryPathFinder = "src" / "main" / "custom-coffee-script"
      override def coffeeScriptCompiledOuputDirectory = "src/main/custom-generated-javascript"
    ...
    }

See sbt's documentation on [Paths](http://code.google.com/p/simple-build-tool/wiki/Paths) for more information on the coffeeScriptDirectoryPathFinder property.

CoffeeScript files are also added the watchPaths so that triggered actions will also be invoked when any CoffeeScript file changes.

## Setup ##

1. In your plugin definition file, add a dependency on coffee-script-sbt-plugin

    i.e. in `project/plugins/Plugins.scala`

        import sbt._

        class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
          val coffeeScriptSbtRepo = "coffeeScript sbt repo" at "http://repo.coderlukes.com"
          val coffeeScript = "org.coffeescript" % "coffee-script-sbt-plugin" % "1.0a"
        }

2. In your project file, mixin the CoffeeScriptCompile trait

    i.e., in `project/build/Project.scala`

         import sbt._
         import org.coffeescript.CoffeeScriptCompile

        class YourProject(info: ProjectInfo) extends DefaultProject(info) with CoffeeScriptCompile {
           // ....
         }

3. Optionally change the compile action to also include `coffee-script-compile`

    override def compileAction = super.compileAction dependsOn(compileCoffeeScript)

## Roadmap ##

See [TODO.org](TODO.org) for more information.

## LICENSE ##

 Copyright (c) 2010 Luke Amdor

 Permission is hereby granted, free of charge, to any person obtaining a copy
 of this software and associated documentation files (the "Software"), to deal
 in the Software without restriction, including without limitation the rights
 to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 copies of the Software, and to permit persons to whom the Software is
 furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in
 all copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 THE SOFTWARE.
