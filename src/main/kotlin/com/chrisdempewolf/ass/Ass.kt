package main.kotlin.com.chrisdempewolf.ass

import main.kotlin.com.chrisdempewolf.ass.commands.New

fun main(args: Array<String>) {
    Ass().run(args)
}

class Ass {

    private val commands = mapOf(
        Pair("new", New())
    )

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