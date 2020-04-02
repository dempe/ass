package com.chrisdempewolf.ass.view.render

data class Page(val content: String,
                val parameters: MutableMap<String, Any> = mutableMapOf())