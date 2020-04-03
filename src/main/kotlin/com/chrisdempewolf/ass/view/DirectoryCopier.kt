package com.chrisdempewolf.ass.view

import org.apache.commons.io.FileUtils
import java.io.File

class DirectoryCopier {

    fun copy(dirToCopy: String = "", destination: String = "") {
        println("└ Preparing to copy directory [${dirToCopy}]")
        val directoryFile = File(dirToCopy)
        if (!directoryFile.exists()) {
            println("    └ Skipping, directory not found.")
            return
        }
        println("    └ Copying directory [${dirToCopy}] as-is")
        FileUtils.copyDirectory(
                directoryFile,
                File(destination + "site/", dirToCopy))
    }
}