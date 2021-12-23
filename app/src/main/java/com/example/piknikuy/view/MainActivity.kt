package com.example.piknikuy.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
import com.example.piknikuy.setting.SettingActivity
import com.example.piknikuy.setting.SettingPreferences
import com.example.piknikuy.viewModel.SettingsViewModel
import com.example.piknikuy.viewModel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        supportActionBar?.title = getString(R.string.piknikuy)

        tabLayout()

        val pref = SettingPreferences.getInstance(dataStore)
        val settingViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(
            SettingsViewModel::class.java
        )
        //perkiraan bugnya disini yg darkmode
        settingViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
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
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                val s = Intent(this, SettingActivity::class.java)
                startActivity(s)
                return true
            }
            R.id.favorite -> {
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