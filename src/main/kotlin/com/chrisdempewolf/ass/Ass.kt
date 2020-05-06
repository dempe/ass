package com.chrisdempewolf.ass

import com.chrisdempewolf.ass.commands.Build
import com.chrisdempewolf.ass.commands.Clean

fun main(args: Array<String>) {
    Ass().run(args)
}

class Ass {

    private val commands = mapOf(
            Pair("build", Build()),
            Pair("clean", Clean()))

    fun run(args: Array<String>) {
        if (args.isEmpty()) {
            println("Must provide command. Valid commands are: ${commands.keys}")
            return
        }

        val command = args[0].toLowerCase()

        if (!commands.containsKey(command)) {
            println("Unknown command [$command]. Valid commands are: ${commands.keys}")
            return
        }

        commands[command]?.run(args)
    }
}