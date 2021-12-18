package com.example.piknikuy.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.piknikuy.R
import com.example.piknikuy.adapter.SectionsPagerAdapter
import com.example.piknikuy.databinding.ActivityMainBinding
import com.example.piknikuy.setting.SettingPreferences
import com.example.piknikuy.view.hotel.HotelActivity
import com.example.piknikuy.view.resto.RestoActivity
import com.example.piknikuy.view.wisata.WisataActivity
import com.example.piknikuy.viewModel.SettingsViewModel
import com.example.piknikuy.viewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.trend,
        R.string.top_visitor
    )

    private lateinit var activityMainBinding: ActivityMainBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = getString(R.string.piknikuy)

        btnMenu()
        darkMode()

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){tab, position -> tab.text = resources.getString(
            TAB_TITLES[position])}.attach()

        supportActionBar?.elevation = 0f
    }

    private fun darkMode() {
        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )
        settingViewModel.getThemeSettings().observe(
            this, { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    return@observe
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    return@observe
                }
            }
        )
    }

    private fun btnMenu() {
        activityMainBinding.btnResto.setOnClickListener {
            val intent = Intent(this, RestoActivity::class.java)
            startActivity(intent)
        }
        activityMainBinding.btnHotel.setOnClickListener {
            val intent = Intent(this, HotelActivity::class.java)
            startActivity(intent)
        }
        activityMainBinding.btnWisata.setOnClickListener {
            val intent = Intent(this, WisataActivity::class.java)
            startActivity(intent)
        }
    }
}