package com.chrisdempewolf.ass.view

import org.apache.commons.io.FileUtils
import java.io.File

class DirectoryCopier {

    fun copy(name: String = "") {
        println("└ Preparing to copy directory [${name}]")
        val directoryFile = File(name)
        if (!directoryFile.exists()) {
            println("    └ Skipping, directory not found.")
            return
        }
        println("    └ Copying directory [${name}] as-is")
        FileUtils.copyDirectory(
                directoryFile,
                File("site/", name))
    }
}