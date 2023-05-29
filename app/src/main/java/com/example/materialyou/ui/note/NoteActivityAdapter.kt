package com.example.materialyou.ui.note

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialyou.R
import com.example.materialyou.databinding.ActivityNoteRecyclerItemBinding
import com.example.materialyou.databinding.ActivityNoteRecyclerItemHeaderBinding

class NoteActivityAdapter(
    private var noteList: MutableList<NoteEntity>
) : RecyclerView.Adapter<NoteActivityAdapter.BaseNoteViewHolder>(), NoteItemTouchHelperAdapter {


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

    inner class NoteViewHolder(view: View) : NoteActivityAdapter.BaseNoteViewHolder(view), NoteItemTouchHelperViewHolder {
        override fun bind(noteEntity: NoteEntity) {
            ActivityNoteRecyclerItemBinding.bind(itemView).apply {

                ivNoteItemRemove.setOnClickListener {
                    removeNoteItem()
                }

                ivNoteItemDescriptionOpenClose.setOnClickListener {
                    noteList[layoutPosition] = noteList[layoutPosition].let {
                        NoteEntity(
                            type = it.type,
                            noteTitle = it.noteTitle,
                            noteDescription = it.noteDescription,
                            noteDescriptionVisibility = !it.noteDescriptionVisibility
                        )
                    }

                    notifyItemChanged(layoutPosition)
                }
                if (noteEntity.noteDescriptionVisibility) {
                    ivNoteItemDescriptionOpenClose.setImageResource(R.drawable.ic_baseline_keyboard_double_arrow_up_32)
                    tvNoteItemDescription.visibility = View.VISIBLE
                } else {
                    ivNoteItemDescriptionOpenClose.setImageResource(R.drawable.ic_baseline_keyboard_double_arrow_down_32)
                    tvNoteItemDescription.visibility = View.GONE
                }


            }
        }

        private fun removeNoteItem() {
            noteList.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    fun appendNoteItem() {
        noteList.add(generateNoteItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateNoteItem() = NoteEntity(type = NoteEntity.TYPE_NOTE)

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (toPosition > 0) {
            noteList.removeAt(fromPosition).apply {
                noteList.add(
                    if (toPosition > fromPosition) toPosition - 1 else toPosition,
                    this
                )
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
        noteList.removeAt(position)
        notifyItemRemoved(position)
    }


}


