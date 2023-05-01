package com.example.materialyou.ui.drawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.materialyou.R
import com.example.materialyou.databinding.ActivityBottomNavigationViewBinding
import com.example.materialyou.ui.viewpager.EarthFragment
import com.example.materialyou.ui.viewpager.MarsFragment
import com.example.materialyou.ui.viewpager.WeatherFragment

class BottomNavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBottomNavigationViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBottomNavigationViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_navigation_container, EarthFragment())
                        .commit()
                    true
                }
                R.id.bottom_view_mars -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_navigation_container, MarsFragment())
                        .commit()
                    true
                }
                R.id.bottom_view_weather -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.bottom_navigation_container, WeatherFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }

        binding.bottomNavigationView.setOnItemReselectedListener { item ->
            when (item.itemId) {
                R.id.bottom_view_earth -> {
                    //Item tapped
                }
                R.id.bottom_view_mars -> {
                    //Item tapped
                }
                R.id.bottom_view_weather -> {
                    //Item tapped
                }
            }
        }

        binding.bottomNavigationView.selectedItemId = R.id.bottom_view_mars
    }
}