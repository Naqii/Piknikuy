package com.example.piknikuy.view

import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.piknikuy.R
import com.example.piknikuy.adapter.SectionsPagerAdapter
import com.example.piknikuy.databinding.ActivityMainBinding
import com.example.piknikuy.setting.SettingActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = getString(R.string.piknikuy)

        tabLayout()
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                activityMainBinding.container.setBackgroundColor(Color.TRANSPARENT)
                activityMainBinding.tabs.setBackgroundColor(Color.TRANSPARENT)
                activityMainBinding.tabs.setSelectedTabIndicatorColor(Color.YELLOW)
                activityMainBinding.tabs.setTabTextColors(Color.WHITE, Color.YELLOW)

            }
        }

    }

    private fun tabLayout() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(
                TAB_TITLES[position]
            )
        }.attach()
        supportActionBar?.elevation = 0f
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        menu?.findItem(R.id.favorite)?.isVisible = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val s = Intent(this, SettingActivity::class.java)
                startActivity(s)
                return true
            }
            else -> true
        }
    }

    companion object {

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.restaurant,
            R.string.hotel,
            R.string.wisata
        )
    }


}