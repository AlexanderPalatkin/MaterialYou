package com.example.materialyou.ui.recycler

data class Data(
    var id: Int = 0,
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

data class Change<out T>(
    val oldData: T,
    val newData: T
)

fun <T> createCombinedPayload(payloads: List<Change<T>>): Change<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()
    return Change(firstChange.oldData, lastChange.newData)
}


