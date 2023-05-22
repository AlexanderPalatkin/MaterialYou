package com.example.materialyou.ui.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.materialyou.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = arrayListOf(
            Pair(Data(Data.TYPE_HEADER, "Header"), false),
            Pair(Data(Data.TYPE_MARS, "Mars", ""), false),
            Pair(Data(Data.TYPE_EARTH, "Earth"), false),
            Pair(Data(Data.TYPE_MARS, "Mars", null), false),
        )

        val adapter = RecyclerActivityAdapter(
            {
                Toast.makeText(
                    this@RecyclerActivity, it.someText,
                    Toast.LENGTH_SHORT
                ).show()
            },
            data
        )
        binding.recyclerView.adapter = adapter

        binding.recyclerActivityFAB.setOnClickListener {
            adapter.appendItem()
            binding.recyclerView.smoothScrollToPosition(adapter.itemCount - 1)
        }
        ItemTouchHelper(ItemTouchHelperCallback(adapter))
            .attachToRecyclerView(binding.recyclerView)

    }
}