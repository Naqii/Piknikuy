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
import com.example.piknikuy.adapter.RestoAdapter
import com.example.piknikuy.databinding.ActivityFavoriteHotelBinding
import com.example.piknikuy.databinding.ActivityFavoriteRestoBinding
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.view.resto.DetailResto
import com.example.piknikuy.viewModel.HotelViewModel
import com.example.piknikuy.viewModel.RestoViewModel

class FavoriteHotel : AppCompatActivity() {

    private lateinit var favoriteAdapter: HotelAdapter
    private lateinit var binding: ActivityFavoriteHotelBinding
    private lateinit var hotelViewModel: HotelViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Favorite Hotel"
        actionbar.setDisplayHomeAsUpEnabled(true)

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