package com.example.piknikuy.view.hotel

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.piknikuy.R
import com.example.piknikuy.databinding.ActivityDetailHotelBinding
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.viewModel.HotelViewModel

class DetailHotel : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailHotelBinding
    private lateinit var hotel: ModelHotel
    private lateinit var hotelViewModel: HotelViewModel

    companion object {

        const val EXTRA_HOTEL = "extra_hotel"

    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar


        hotelViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HotelViewModel::class.java]
        val id = intent.getStringExtra(EXTRA_HOTEL) as String

        hotelViewModel.setDetailHotel(id)
        hotelViewModel.hotel.observe(this, {dataHotel ->
            if(dataHotel != null) {
                Glide.with(this)
                    .load(dataHotel.picture)
                    .apply(RequestOptions())
                    .into(binding.tvPicture)
                binding.tvNamaHotel.text = dataHotel.name
                binding.tvLokasiHotel.text = dataHotel.city
                binding.tvRating.text = "Rating: ${dataHotel.rating}"
                binding.tvDetailHotel.text = dataHotel.description
                actionbar!!.title = "Detail ${dataHotel.name}"
                actionbar.setDisplayHomeAsUpEnabled(true)
                hotel = dataHotel
                hotelViewModel.updateFavorite(hotel)
            }
        })

        hotelViewModel.isFavorite.observe(this, {
            if(hotelViewModel.isFavorite.value == true){
                binding.btnFav.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.btnFav.setImageResource(R.drawable.ic_favorite_border)
            }
        })

        binding.btnFav.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_fav) {
            if (hotelViewModel.isFavorite.value == true) {
                hotelViewModel.deleteFavorite(hotel)
            } else {
                hotelViewModel.insertFavorite(hotel)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}