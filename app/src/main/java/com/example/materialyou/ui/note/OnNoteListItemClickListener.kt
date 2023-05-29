package com.example.materialyou.ui.note

import com.example.materialyou.ui.recycler.Data

fun interface OnNoteListItemClickListener {
    fun onItemClick(noteEntity: MutableList<NoteEntity>)
}