package com.example.materialyou.ui.recycler

data class Data(
    val type: Int = TYPE_EARTH,
    val someText: String = "Text",
    val someDescription: String? = "Description"
) {
    companion object {
        const val TYPE_EARTH = 0
        const val TYPE_MARS = 1
        const val TYPE_HEADER = 2
        const val ITEM_CLOSE = 0
        const val ITEM_OPEN = 1
    }
}