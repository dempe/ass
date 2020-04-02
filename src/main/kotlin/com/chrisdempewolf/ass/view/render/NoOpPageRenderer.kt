package com.chrisdempewolf.ass.view.render

class NoOpPageRenderer : PageRenderer {

    override fun render(content: String): Page {
        return Page(content, mutableMapOf())
    }
}