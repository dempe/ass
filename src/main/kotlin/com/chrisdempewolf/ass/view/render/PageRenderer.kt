package com.chrisdempewolf.ass.view.render

interface PageRenderer {
    fun render(content: String): Page
}