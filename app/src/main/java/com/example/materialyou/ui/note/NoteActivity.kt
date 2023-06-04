package com.example.materialyou.ui.note

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.materialyou.databinding.ActivityNoteBinding

class NoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityNoteBinding
    private lateinit var noteItemTouchHelper: ItemTouchHelper
    private lateinit var adapter: NoteActivityAdapter
    private var noteList: MutableList<NoteEntity> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noteList.add(NoteEntity(id = 0, type = NoteEntity.TYPE_HEADER))

        adapter = NoteActivityAdapter(
            {
                if (adapter.itemCount > 1) {
                    val newNoteList: MutableList<NoteEntity> = mutableListOf()
                    newNoteList.add(it.first())
                    if (it.first().favourite) {
                        it.remove(it.first())
                        it.sortWith{l, r ->
                            if (l.id > r.id) {
                                -1
                            } else {
                                1
                            }
                        }
                    } else {
                        it.remove(it.first())
                        it.sortWith{l, r ->
                            if (l.id > r.id) {
                                1
                            } else {
                                -1
                            }
                        }
                    }
                    newNoteList.addAll(it)
                    adapter.setNoteItems(newNoteList)
                }

            }
        )
        binding.recyclerNoteView.adapter = adapter
        adapter.setNoteItems(noteList)

        binding.noteActivityFAB.setOnClickListener {
            adapter.appendNoteItem()
            binding.recyclerNoteView.smoothScrollToPosition(adapter.itemCount - 1)
        }

        noteItemTouchHelper = ItemTouchHelper(NoteItemTouchHelperCallback(adapter))
        noteItemTouchHelper.attachToRecyclerView(binding.recyclerNoteView)
    }

}