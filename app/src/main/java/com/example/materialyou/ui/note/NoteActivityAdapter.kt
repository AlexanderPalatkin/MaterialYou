package com.example.materialyou.ui.note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialyou.databinding.ActivityNoteRecyclerItemBinding
import com.example.materialyou.databinding.ActivityNoteRecyclerItemHeaderBinding

class NoteActivityAdapter(
    private var noteList: MutableList<NoteEntity>
) : RecyclerView.Adapter<NoteActivityAdapter.BaseNoteViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteActivityAdapter.BaseNoteViewHolder {
        return when (viewType) {
            NoteEntity.TYPE_NOTE -> {
                val itemNoteBinding: ActivityNoteRecyclerItemBinding =
                    ActivityNoteRecyclerItemBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                NoteViewHolder(itemNoteBinding.root)
            }
            else -> {
                val itemNoteHeaderBinding: ActivityNoteRecyclerItemHeaderBinding =
                    ActivityNoteRecyclerItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                HeaderNoteViewHolder(itemNoteHeaderBinding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: NoteActivityAdapter.BaseNoteViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun getItemViewType(position: Int): Int {
        return noteList[position].type
    }

    abstract class BaseNoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(noteEntity: NoteEntity)
    }

    inner class HeaderNoteViewHolder(view: View) : NoteActivityAdapter.BaseNoteViewHolder(view) {
        override fun bind(noteEntity: NoteEntity) {
            ActivityNoteRecyclerItemHeaderBinding.bind(itemView).apply {

            }
        }
    }

    inner class NoteViewHolder(view: View) : NoteActivityAdapter.BaseNoteViewHolder(view) {
        override fun bind(noteEntity: NoteEntity) {
            ActivityNoteRecyclerItemBinding.bind(itemView).apply {

            }
        }
    }

    fun appendNoteItem() {
        noteList.add(generateNoteItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateNoteItem() = NoteEntity(type = NoteEntity.TYPE_NOTE)
}


