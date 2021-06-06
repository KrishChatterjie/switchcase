package com.example.switchcase

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.switchcase.databinding.ActivityMainBinding
import com.example.switchcase.utils.Constants.CASES
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPrefs: SharedPreferences
    private lateinit var pageChangeCallback : ViewPager2.OnPageChangeCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        sharedPrefs = getSharedPreferences("page", Context.MODE_PRIVATE)

        val pagerAdapter = CaseAdapter(this)
        binding.pager.adapter = pagerAdapter
        TabLayoutMediator(
            binding.tabLayout, binding.pager
        ) { tab, position -> tab.text = CASES[position] }
            .attach()

        binding.pager.currentItem = sharedPrefs.getInt("currentPage", 0)
        pageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                sharedPrefs.edit().putInt("currentPage", position).apply()
            }
        }
        binding.pager.registerOnPageChangeCallback(pageChangeCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.pager.unregisterOnPageChangeCallback(pageChangeCallback)

    }
}

