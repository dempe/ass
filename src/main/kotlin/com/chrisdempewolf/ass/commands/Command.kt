package main.kotlin.com.chrisdempewolf.ass.commands

interface Command {
    fun run(args: Array<String>)
}