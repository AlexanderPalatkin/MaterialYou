package com.example.materialyou.ui.note

data class NoteEntity(
    val type: Int = TYPE_NOTE,
    val noteTitle: String = "",
    var noteDescription: String = "",
    var noteDescriptionVisibility: Boolean = false
) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NOTE = 1
    }
}
