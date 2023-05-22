package com.example.materialyou.ui.recycler

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.materialyou.databinding.ActivityRecyclerItemEarthBinding
import com.example.materialyou.databinding.ActivityRecyclerItemHeaderBinding
import com.example.materialyou.databinding.ActivityRecyclerItemMarsBinding
import com.example.materialyou.ui.recycler.Data.Companion.TYPE_MARS

class RecyclerActivityAdapter(
    private val onListItemClickListener: OnListItemClickListener,
    private var dataSet: MutableList<Pair<Data, Boolean>>
) : RecyclerView.Adapter<RecyclerActivityAdapter.BaseViewHolder>(), ItemTouchHelperAdapter {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            Data.TYPE_EARTH -> {
                val itemEarthBinding: ActivityRecyclerItemEarthBinding =
                    ActivityRecyclerItemEarthBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                EarthViewHolder(itemEarthBinding.root)
            }
            TYPE_MARS -> {
                val itemMarsBinding: ActivityRecyclerItemMarsBinding =
                    ActivityRecyclerItemMarsBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                MarsViewHolder(itemMarsBinding.root)
            }

            else -> {
                val itemHeaderBinding: ActivityRecyclerItemHeaderBinding =
                    ActivityRecyclerItemHeaderBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                HeaderViewHolder(itemHeaderBinding.root)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataSet[position].first.type
    }

    fun appendItem() {
        dataSet.add(generateItem())
        notifyItemInserted(itemCount - 1)
    }

    private fun generateItem() = Pair(Data(TYPE_MARS, "New Mars"), false)

    abstract class BaseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(data: Pair<Data, Boolean>)
    }

    inner class EarthViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            ActivityRecyclerItemEarthBinding.bind(itemView).apply {
                descriptionTextView.text = data.first.someDescription
                earthImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
            }
        }
    }

    inner class MarsViewHolder(view: View) : BaseViewHolder(view),
    ItemTouchHelperViewHolder {
        override fun bind(data: Pair<Data, Boolean>) {
            ActivityRecyclerItemMarsBinding.bind(itemView).apply {
                marsImageView.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
                addItemImageView.setOnClickListener {
                    addItem()
                }
                removeItemImageView.setOnClickListener {
                    removeItem()
                }
                moveItemUp.setOnClickListener {
                    moveUp()
                }
                moveItemDown.setOnClickListener {
                    moveDown()
                }
                marsTextView.setOnClickListener {
                    dataSet[layoutPosition] = dataSet[layoutPosition].let {
                        it.first to !it.second
                    }
                    notifyItemChanged(layoutPosition)
                }
                marsDescriptionTextView.visibility =
                    if (data.second) View.VISIBLE else View.GONE
            }
        }

        private fun moveUp(){
            layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                dataSet.removeAt(currentPosition).apply {
                    dataSet.add(currentPosition - 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition - 1)
            }
        }

        private fun moveDown(){
            layoutPosition.takeIf { it < dataSet.size - 1 }?.also { currentPosition ->
                dataSet.removeAt(currentPosition).apply {
                    dataSet.add(currentPosition + 1, this)
                }
                notifyItemMoved(currentPosition, currentPosition + 1)
            }
        }

        private fun addItem() {
            dataSet.add(layoutPosition + 1, generateItem())
            notifyItemInserted(layoutPosition + 1)
        }

        private fun removeItem() {
            dataSet.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {
        override fun bind(data: Pair<Data, Boolean>) {
            ActivityRecyclerItemHeaderBinding.bind(itemView).apply {
                header.setOnClickListener {
                    onListItemClickListener.onItemClick(data.first)
                }
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (toPosition > 0) {
            dataSet.removeAt(fromPosition).apply {
                dataSet.add(if (toPosition > fromPosition) toPosition - 1 else toPosition,
                    this)
            }
            notifyItemMoved(fromPosition, toPosition)
        }
    }

    override fun onItemDismiss(position: Int) {
        dataSet.removeAt(position)
        notifyItemRemoved(position)
    }
}