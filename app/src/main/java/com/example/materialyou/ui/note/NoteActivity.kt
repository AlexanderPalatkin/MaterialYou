package com.example.materialyou.ui.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.materialyou.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    lateinit var noteItemTouchHelper: ItemTouchHelper
    private lateinit var adapter: NoteActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val noteList = arrayListOf(
            NoteEntity(type = NoteEntity.TYPE_HEADER)
        )

        adapter = NoteActivityAdapter(

            noteList
        )

        binding.recyclerNoteView.adapter = adapter

        binding.noteActivityFAB.setOnClickListener {
            adapter.appendNoteItem()
            binding.recyclerNoteView.smoothScrollToPosition(adapter.itemCount - 1)
        }

        noteItemTouchHelper = ItemTouchHelper(NoteItemTouchHelperCallback(adapter))
        noteItemTouchHelper.attachToRecyclerView(binding.recyclerNoteView)
    }
}