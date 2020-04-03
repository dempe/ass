package com.chrisdempewolf.ass.commands

import com.chrisdempewolf.ass.view.DirectoryCopier
import com.chrisdempewolf.ass.view.posts.ContentLoader
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths

class Build : Command {

    private val templates = mutableMapOf<String, String>()

    override fun run(args: Array<String>) {
        Clean().run(args)
        println("Building site")

        // build site dir
        val dir = if (args.size >= 3) args[2] else ""
        val sitePath = Paths.get(dir + "site")

        Files.createDirectories(sitePath)
        DirectoryCopier().copy(dir + "static", dir)
        buildContent(dir)
    }

    private fun loadTemplates(dir: String) {
        val templateDir = File("$dir/templates")

        templateDir.walkTopDown().forEach { file ->
            templates[file.name] = file.readText(Charsets.UTF_8)
        }
    }

    private fun buildContent(dir: String) {
        val pages = ContentLoader().load(dir)

        pages.forEach { page ->
            page.parameters["layout"]
        }
    }
}
