package com.example.materialyou.ui.note

interface NoteItemTouchHelperAdapter {

    fun onItemMove(fromPosition: Int, toPosition: Int)

    fun onItemDismiss(position: Int)

}