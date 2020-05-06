package com.chrisdempewolf.ass.commands

import java.nio.file.Files
import java.nio.file.Path

class Clean : Command {

    override fun run(args: Array<String>) {
        val sitePath = Path.of("_site")

        if (Files.exists(sitePath)) {
            println("Cleaning site!")
            sitePath.toFile().deleteRecursively()

        }
        else {
            println("Site already clean, doing nothing.")
        }
    }
}