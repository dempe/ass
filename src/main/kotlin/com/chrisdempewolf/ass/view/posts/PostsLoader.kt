package com.chrisdempewolf.ass.view.posts

import com.chrisdempewolf.ass.view.render.Page
import com.chrisdempewolf.ass.view.render.PageRendererResolver
import java.io.File

/**
 * load all post data into structure for downstream processing
 */
class PostsLoader {
    private val rendererResolver = PageRendererResolver()

    fun load(): List<Page> {
        val pages = mutableListOf<Page>()
        val postsDir = File("content/posts")

        postsDir.walkTopDown().forEach { file ->
            if (file.isDirectory || shouldSkip(file.absolutePath)) { return@forEach }

            try {
                // load file raw text
                //val encoding = Charset.forName(config.encoding)
                val content = file.readText(Charsets.UTF_8)

                // render markdown, html, hbs, to html
                val renderer = rendererResolver.resolve(file.toPath())
                val page: Page = renderer.render(content)

                // set some basic parameters for template
                val relativeUrl = "/content/posts/" + file.nameWithoutExtension + ".html"

                page.parameters["original_file"] = file.name
                page.parameters["file"] = file.nameWithoutExtension + ".html"
                page.parameters["url"] = relativeUrl
                //page.parameters["absolute_url"] = config.baseUrl + relativeUrl
                page.parameters["content"] = page.content
//                page.parameters["date"] = dateParser.prettyParse(file.nameWithoutExtension)
//                page.parameters["timestamp"] = dateParser.parse(file.nameWithoutExtension).millis

                pages.add(page)

            } catch (e: RuntimeException) {
                println("Failed to render page: [${file.name}] due to [${e.message}]")
            }
        }
        return pages.sortedByDescending { page -> page.parameters["timestamp"] as Long }
    }

    // handle this more generically
    private fun shouldSkip(path: String) = path.contains(".DS_Store", true)

}