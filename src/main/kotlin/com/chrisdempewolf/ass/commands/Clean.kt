package com.chrisdempewolf.ass.commands

import java.nio.file.Files
import java.nio.file.Paths

/**
 * Clean/Remove contents of site directory
 */
class Clean : Command {

    override fun run(args: Array<String>) {
        val sitePath = Paths.get("site/")

        if (Files.exists(sitePath)) {
            println("Cleaning site")
            sitePath.toFile().deleteRecursively()

        }
        else {
            println("Site already clean, doing nothing.")
        }
    }
}
