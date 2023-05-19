package com.example.materialyou.ui.recycler

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.materialyou.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {
    lateinit var binding: ActivityRecyclerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = arrayListOf(
            Data(Data.TYPE_HEADER, "Header"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth", "Описание"),
            Data(Data.TYPE_MARS, "Mars", ""),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_EARTH, "Earth"),
            Data(Data.TYPE_MARS, "Mars", null)
        )
        binding.recyclerView.adapter = RecyclerActivityAdapter(
            { data ->
                Toast.makeText(
                    this@RecyclerActivity, data.someText,
                    Toast.LENGTH_SHORT
                ).show()
            },
            data
        )

    }
}