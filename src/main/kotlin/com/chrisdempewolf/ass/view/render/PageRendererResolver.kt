package com.chrisdempewolf.ass.view.render

import org.apache.commons.io.FilenameUtils
import java.nio.file.Path

class PageRendererResolver {
    private val noOpPageRenderer = NoOpPageRenderer()

    private val renderers = mapOf(
            Pair("html", HtmlPageRenderer()),
            Pair("hbs", HtmlPageRenderer()),
            Pair("handlebars", HtmlPageRenderer()),
            Pair("md", FlexMarkPageRenderer()),
            Pair("markdown", FlexMarkPageRenderer())
    )

    fun resolve(path: Path): PageRenderer {
        val extension = FilenameUtils.getExtension(path.toString()).toLowerCase()
        renderers[extension]?.let { renderer ->
            return renderer
        }
        return noOpPageRenderer
    }

}