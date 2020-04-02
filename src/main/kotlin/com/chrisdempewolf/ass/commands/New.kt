package com.chrisdempewolf.ass.commands

import java.nio.file.Files
import java.nio.file.Paths
import org.apache.commons.io.FileUtils

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
            println("Unable to make new site. Directory, [$directory], already exists.")
            return
        }

        Files.createDirectories(directory)
        Files.createDirectories(Paths.get(directory.toString(), "content"))
        Files.createDirectories(Paths.get(directory.toString(), "templates"))
        Files.createDirectories(Paths.get(directory.toString(), "partials"))
        Files.createDirectories(Paths.get(directory.toString(), "site"))
        Files.createDirectories(Paths.get(directory.toString(), "static"))
        Files.createDirectories(Paths.get(directory.toString(), "static/js"))
        Files.createDirectories(Paths.get(directory.toString(), "static/img"))
        Files.createDirectories(Paths.get(directory.toString(), "static/css"))

        val configFile = Files.createFile(Paths.get("$directory/config.yml"))
        val configResourcePath = "/com/chrisdempewolf/ass/config.yml"
        val configUrl = javaClass.getResource(configResourcePath)
        FileUtils.copyURLToFile(configUrl, configFile.toFile())

        Files.createDirectories(Paths.get(directory.toString(), "content/posts"))
        val testPost = Files.createFile(Paths.get("$directory/content/posts/post.md"))
        val postResourcePath = "/com/chrisdempewolf/ass/post.md"
        val postUrl = javaClass.getResource(postResourcePath)
        FileUtils.copyURLToFile(postUrl, testPost.toFile())

        println("Successfully created new site!")
    }

    private fun makeNewPost(title: String) {
        TODO("Not yet implemented")
    }
}