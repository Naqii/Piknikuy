package com.example.piknikuy.view.hotel

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.R
import com.example.piknikuy.adapter.HotelAdapter
import com.example.piknikuy.databinding.ActivityFavoriteHotelBinding
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.viewModel.HotelViewModel

class FavoriteHotel : AppCompatActivity() {

    private lateinit var favoriteAdapter: HotelAdapter
    private lateinit var binding: ActivityFavoriteHotelBinding
    private lateinit var hotelViewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.favorite_hotel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        hotelViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HotelViewModel::class.java]
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvHotel.layoutManager = GridLayoutManager(this, 2)
        } else  {
            binding.rvHotel.layoutManager = LinearLayoutManager(this)
        }

        favoriteAdapter = HotelAdapter()
        binding.rvHotel.adapter = favoriteAdapter
        favoriteAdapter.setOnItemClickCallback(object : HotelAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ModelHotel) {
                val moveIntent = Intent(this@FavoriteHotel, DetailHotel::class.java)
                moveIntent.putExtra(DetailHotel.EXTRA_HOTEL, data.id)
                startActivity(moveIntent)
            }
        })

        hotelViewModel.setSearchHotel()
        progressBarDisplay(true)
        hotelViewModel.favorite.observe(this, { hotelItem->
            if (hotelItem != null) {
                favoriteAdapter.listHotel = ArrayList(hotelItem)
                progressBarDisplay(false)
            }
        })
    }

    private fun progressBarDisplay(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}