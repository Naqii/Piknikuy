package com.example.piknikuy.view.hotel

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.R
import com.example.piknikuy.adapter.HotelAdapter
import com.example.piknikuy.databinding.ActivityHotelBinding
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.setting.SettingActivity
import com.example.piknikuy.viewModel.HotelViewModel

class HotelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelBinding
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var hotelViewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.hotel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        hotelViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HotelViewModel::class.java]
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvHotel.layoutManager = GridLayoutManager(this, 2)
        } else  {
            binding.rvHotel.layoutManager = LinearLayoutManager(this)
        }

        hotelAdapter = HotelAdapter()
        binding.rvHotel.adapter = hotelAdapter
        hotelAdapter.setOnItemClickCallback(object : HotelAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ModelHotel) {
                val moveIntent = Intent(this@HotelActivity, DetailHotel::class.java)
                moveIntent.putExtra(DetailHotel.EXTRA_HOTEL, data.id)
                startActivity(moveIntent)
            }
        })

        hotelViewModel.setSearchHotel()
        progressBarDisplay(true)
        hotelViewModel.listHotel.observe(this, {hotelItem ->
            if (hotelItem != null) {
                hotelAdapter.listHotel = hotelItem
                progressBarDisplay(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search) + " hotel"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                hotelViewModel.setSearchHotel(query)
                progressBarDisplay(true)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    progressBarDisplay(false)
                } else {
                    hotelViewModel.setSearchHotel(newText)
                    progressBarDisplay(true)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val f = Intent(this, FavoriteHotel::class.java)
                startActivity(f)
                return true
            }
            R.id.setting -> {
                val s = Intent(this, SettingActivity::class.java)
                startActivity(s)
                return true
            }
            else -> return true
        }
    }

    private fun progressBarDisplay(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}