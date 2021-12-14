package com.example.piknikuy.view

import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.piknikuy.R
import com.example.piknikuy.adapter.SectionsPagerAdapter
import com.example.piknikuy.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.trend,
        R.string.top_visitor
    )

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        btnMenu()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){tab, position -> tab.text = resources.getString(
            TAB_TITLES[position])}.attach()

        supportActionBar?.elevation = 0f
    }

    private fun btnMenu() {
        activityMainBinding.btnResto.setOnClickListener {
            val intent = Intent(this, RestoActivity::class.java)
            startActivity(intent)
        }
    }
}