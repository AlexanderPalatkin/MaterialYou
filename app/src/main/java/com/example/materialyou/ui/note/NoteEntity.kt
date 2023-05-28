package com.example.materialyou.ui.note

data class NoteEntity(
    var id: Int = 0,
    val type: Int = TYPE_NOTE,
    val noteTitle: String,
    var noteDescription: String
) {
    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_NOTE = 1
    }
}
