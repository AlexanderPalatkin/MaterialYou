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
    private val onNoteListItemClickListener: OnNoteListItemClickListener,
    private var noteList: MutableList<NoteEntity> = mutableListOf()
) : RecyclerView.Adapter<NoteActivityAdapter.BaseNoteViewHolder>(), NoteItemTouchHelperAdapter {

    fun setNoteItems(newNoteList: List<NoteEntity>) {
        noteList.clear()
        noteList.addAll(newNoteList)
        notifyDataSetChanged()
    }

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
                ivNoteItemHeaderFavourite.setOnClickListener {
                    noteList[layoutPosition] = noteList[layoutPosition].let {
                        it.copy(favourite = !it.favourite)
                    }
                    notifyItemChanged(layoutPosition)
                    onNoteListItemClickListener.onItemClick(noteList)
                }
                if (noteEntity.favourite) {
                    ivNoteItemHeaderFavourite.setImageResource(R.drawable.ic_baseline_favorite_filled_24)
                } else {
                    ivNoteItemHeaderFavourite.setImageResource(R.drawable.ic_baseline_favorite_outlined_24)
                }

            }
        }
    }

    inner class NoteViewHolder(view: View) : NoteActivityAdapter.BaseNoteViewHolder(view),
        NoteItemTouchHelperViewHolder {
        override fun bind(noteEntity: NoteEntity) {
            ActivityNoteRecyclerItemBinding.bind(itemView).apply {

                etNoteItemTitle.setText(noteEntity.noteTitle)
                etNoteItemDescription.setText(noteEntity.noteDescription)

                ivNoteItemRemove.setOnClickListener {
                    removeNoteItem()
                }

                ivNoteItemFavourite.setOnClickListener {
                    noteList[layoutPosition] = noteList[layoutPosition].let {
                        it.copy(
                            noteTitle = etNoteItemTitle.text.toString(),
                            noteDescription = etNoteItemDescription.text.toString(),
                            favourite = !it.favourite,
                            id = if (it.id == 0) 1 else 0
                        )
                    }
                    notifyItemChanged(layoutPosition)
                }
                if (noteEntity.favourite) {
                    ivNoteItemFavourite.setImageResource(R.drawable.ic_baseline_favorite_filled_24)
                } else {
                    ivNoteItemFavourite.setImageResource(R.drawable.ic_baseline_favorite_outlined_24)
                }

                ivNoteItemDescriptionOpenClose.setOnClickListener {
                    noteEntity.noteTitle = etNoteItemTitle.text.toString()
                    noteList[layoutPosition] = noteList[layoutPosition].let {
                        it.copy(
                            noteTitle = etNoteItemTitle.text.toString(),
                            noteDescription = etNoteItemDescription.text.toString(),
                            noteDescriptionVisibility = !it.noteDescriptionVisibility
                        )
                    }

                    notifyItemChanged(layoutPosition)
                }
                if (noteEntity.noteDescriptionVisibility) {
                    ivNoteItemDescriptionOpenClose.setImageResource(R.drawable.ic_baseline_keyboard_double_arrow_up_32)
                    etNoteItemDescription.visibility = View.VISIBLE
                } else {
                    ivNoteItemDescriptionOpenClose.setImageResource(R.drawable.ic_baseline_keyboard_double_arrow_down_32)
                    etNoteItemDescription.visibility = View.GONE
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


