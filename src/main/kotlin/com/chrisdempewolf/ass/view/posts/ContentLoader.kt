package com.chrisdempewolf.ass.view.posts

import com.vladsch.flexmark.ext.yaml.front.matter.AbstractYamlFrontMatterVisitor
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterExtension
import com.vladsch.flexmark.html.HtmlRenderer
import com.vladsch.flexmark.parser.Parser
import com.vladsch.flexmark.util.options.MutableDataSet
import java.io.File

/**
 * load all pages into data structure for downstream processing
 */
data class Page(val content: String,
                val parameters: MutableMap<String, Any> = mutableMapOf())

class ContentLoader {

    fun load(dir: String): List<Page> {
        val pages = mutableListOf<Page>()
        val contentDir = File(dir + "content")

        contentDir.walkTopDown().forEach { file ->
            if (shouldSkip(file)) { return@forEach }

            try {
                // load file raw text
                val content = file.readText(Charsets.UTF_8)
                val page = parse(content)

                page.parameters["original_file"] = file.name
                page.parameters["file"] = file.nameWithoutExtension + ".html"
                page.parameters["url"] = file.path + file.nameWithoutExtension + ".html"
                //page.parameters["absolute_url"] = config.baseUrl + relativeUrl
                page.parameters["content"] = page.content

                pages.add(page)

            } catch (e: RuntimeException) {
                println("Failed to render page: [${file.name}] due to [${e.message}]")
            }
        }
        return pages.sortedByDescending { page -> page.parameters["timestamp"] as Long }
    }

    private fun shouldSkip(file: File): Boolean {
        return file.isDirectory || file.absolutePath.contains(".DS_Store", true)
    }

    private fun parse(content: String): Page {
        val options = MutableDataSet()
                .set(Parser.EXTENSIONS, listOf(
                        YamlFrontMatterExtension.create()))

        // first parse templates
        val parser = Parser.builder(options).build()
        val document = parser.parse(content)
        // render to content
        val renderer = HtmlRenderer.builder(options).build()
        val html = renderer.render(document)

        val metadataParser = AbstractYamlFrontMatterVisitor()
        metadataParser.visit(document)

        return Page(html, transform(metadataParser.data))
    }

    private fun transform(data: Map<String, MutableList<String>>): MutableMap<String, Any> {
        val transformed = mutableMapOf<String, Any>()

        data.entries.forEach { entry ->
            if (entry.value.size == 1) {
                transformed[entry.key] = maybeTransformToList(entry.key, entry.value.first())
            } else {
                transformed[entry.key] = maybeTransformToList(entry.key, entry.value)
            }
        }

        return transformed
    }

    private fun maybeTransformToList(key: String, parameter: Any): Any {
        if (parameter !is String) { return parameter }

        if (key == "tags") {
            return parameter
                    .split(" ")
                    .map(String::trim)
                    .toList()
        }

        return parameter
    }

}