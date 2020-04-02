package com.chrisdempewolf.ass.commands

import com.chrisdempewolf.ass.view.DirectoryCopier
import java.nio.file.Files
import java.nio.file.Paths

class Build : Command {

    override fun run(args: Array<String>) {
        println("Building site")

        Clean().run(args)

        // build site dir
        val sitePath = Paths.get("site")
        Files.createDirectories(sitePath)
        DirectoryCopier().copy("static")
        DirectoryCopier().copy("content")
    }
}
