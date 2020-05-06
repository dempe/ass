package com.chrisdempewolf.ass.commands

import java.io.File
import java.nio.file.Files
import java.nio.file.Path

/** FutureFile simply stores a file along with its type - regular file or directory.
 *  This is needed because when we create _site directory, we need to create the subdirectories first.
 *  However, java.io.File.isFile and java.io.File.isDirectory do not work on non-existent files.
 *  I chose this over a map, because meh, I like custom data structures.
 */
data class FutureFile(val file: File, val type: String)

const val SITE_DIRECTORY = "_site"
const val CONTENT_DIRECTORY = "content"

class Build : Command {

    override fun run(args: Array<String>) {
        println("Building site!")

        Clean().run(args)
        createSiteDirectory()
        copyContentDirectory()
    }

    private fun copyContentDirectory() {
        val contentDirectory = File(CONTENT_DIRECTORY)

        if (!contentDirectory.exists()) {
            println("Content directory not found. Aborting build.")
            return
        }

        val filesToCreate = copyContentDirectoryHelper(contentDirectory)

        // create directories first
        filesToCreate
                .filter { it.type == "d" }
                .forEach { Files.createDirectory(it.file.toPath()) }
        filesToCreate
                .filter { it.type == "f" }
                .forEach { Files.createFile(it.file.toPath()) }
    }

    private fun copyContentDirectoryHelper(directory : File): List<FutureFile> {
        val files = directory.listFiles().toList()
        val newFiles = mutableListOf<FutureFile>()

        for (file in files) {
            val newFile = File(file.path.replace(CONTENT_DIRECTORY, SITE_DIRECTORY))

            if (file.isDirectory) {
                newFiles += copyContentDirectoryHelper(file)
                newFiles += FutureFile(newFile, "d")
                continue
            }

            newFiles += FutureFile(newFile, "f")
        }

        return newFiles
    }

    private fun createSiteDirectory() {
        val sitePath = Path.of(SITE_DIRECTORY)
        println("Okay, building site in $sitePath")
        Files.createDirectories(sitePath)
    }
}
