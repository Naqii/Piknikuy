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
import com.example.piknikuy.adapter.RestoAdapter
import com.example.piknikuy.databinding.ActivityHotelBinding
import com.example.piknikuy.databinding.ActivityRestoBinding
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.view.resto.DetailResto
import com.example.piknikuy.view.resto.FavoriteResto
import com.example.piknikuy.viewModel.HotelViewModel
import com.example.piknikuy.viewModel.RestoViewModel

class HotelActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHotelBinding
    private lateinit var hotelAdapter: HotelAdapter
    private lateinit var hotelViewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Hotel"
        actionbar.setDisplayHomeAsUpEnabled(true)

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
        if(item.itemId == R.id.favorite){
            val myIntent = Intent(this@HotelActivity, FavoriteHotel::class.java)
            startActivity(myIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun progressBarDisplay(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvHotel.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvHotel.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}