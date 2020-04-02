package com.chrisdempewolf.ass.view.render

/**
 * HtmlRender doesn't need to do anything except return the content as-is
 */
class HtmlPageRenderer : PageRenderer {

    override fun render(content: String) = Page(content)

}