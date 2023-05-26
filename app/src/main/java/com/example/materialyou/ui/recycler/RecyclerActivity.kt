package com.example.materialyou.ui.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.materialyou.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerBinding
    lateinit var itemTouchHelper: ItemTouchHelper
    private var isNewList = false
    private lateinit var adapter: RecyclerActivityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = arrayListOf(
            Pair(Data(type = Data.TYPE_HEADER, someText = "Header"), false),
            Pair(Data(type = Data.TYPE_MARS, someText = "Mars"), false),
            Pair(Data(type = Data.TYPE_EARTH, someText = "Earth"), false),
            Pair(Data(type = Data.TYPE_MARS, someText = "Mars"), false),
        )

        adapter = RecyclerActivityAdapter(
            {
                Toast.makeText(
                    this@RecyclerActivity, it.someText,
                    Toast.LENGTH_SHORT
                ).show()
            },
            data,
            object : OnStartDragListener {
                override fun onStartDrag(viewHolder: RecyclerView.ViewHolder) {
                    itemTouchHelper.startDrag(viewHolder)
                }
            }

        )
        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }

        itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerActivityDiffUtilFAB.setOnClickListener {
            adapter.setItems(createItemList(isNewList).map { it })
            isNewList = !isNewList

        }
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<Data, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(Data(0, Data.TYPE_HEADER, "Header"), false),
                Pair(Data(1, Data.TYPE_MARS, "Mars", ""), false),
                Pair(Data(2, Data.TYPE_MARS, "Mars", ""), false),
                Pair(Data(3, Data.TYPE_MARS, "Mars", ""), false),
                Pair(Data(4, Data.TYPE_MARS, "Mars", ""), false),
                Pair(Data(5, Data.TYPE_MARS, "Mars", ""), false),
                Pair(Data(6, Data.TYPE_MARS, "Mars", ""), false)
            )
            true -> listOf(
                Pair(Data(0, Data.TYPE_HEADER, "Header"), false),
                Pair(Data(1, Data.TYPE_MARS, "Mars", ""), false),
                Pair(Data(2, Data.TYPE_MARS, "Jupiter", ""), false),
                Pair(Data(3, Data.TYPE_MARS, "Mars", ""), false),
                Pair(Data(4, Data.TYPE_MARS, "Neptune", ""), false),
                Pair(Data(5, Data.TYPE_MARS, "Saturn", ""), false),
                Pair(Data(6, Data.TYPE_MARS, "Mars", ""), false)
            )
        }
    }

}