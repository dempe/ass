package main.kotlin.com.chrisdempewolf.ass.commands

import java.nio.file.Files
import java.nio.file.Paths

class New : Command {

    private val options = setOf("site", "post")
    private val expectedArgsSize = 4

    override fun run(args: Array<String>) {
        val errorMessage = "Command should be of the form \"ass new site [site directory]\" or \"ass new post [post title]\""

        if (args.size != expectedArgsSize || !options.contains(args[2])) {
            println(errorMessage)
            return
        }

        when (args[2]) {
            "site" -> makeNewSite(args[3])
            "post" -> makeNewPost(args[3])
            else   -> {
                println(errorMessage)
            }
        }
    }

    private fun makeNewSite(path: String) {
        val directory = Paths.get(path)

        println("Attempting to create new site in [$directory].")
        if (Files.exists(directory)) {
            println("Unable to make new site. Directory [$directory] already exists.")
            return
        }

        Files.createDirectories(directory)

        Files.createDirectories(Paths.get(directory.toString(), "content"))
        Files.createDirectories(Paths.get(directory.toString(), "templates"))
        Files.createDirectories(Paths.get(directory.toString(), "partials"))
        Files.createDirectories(Paths.get(directory.toString(), "static"))
        Files.createDirectories(Paths.get(directory.toString(), "static/js"))
        Files.createDirectories(Paths.get(directory.toString(), "static/img"))
        Files.createDirectories(Paths.get(directory.toString(), "static/css"))

        //Files.createFile(Paths.get(directory.toString()), "config.yaml")
    }

    private fun makeNewPost(title: String) {

    }
}