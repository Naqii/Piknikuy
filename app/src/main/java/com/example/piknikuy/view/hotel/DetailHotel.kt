package com.example.piknikuy.view.hotel

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.piknikuy.R
import com.example.piknikuy.databinding.ActivityDetailHotelBinding
import com.example.piknikuy.model.ModelHotel
import com.example.piknikuy.setting.SettingActivity
import com.example.piknikuy.viewModel.HotelViewModel

class DetailHotel : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityDetailHotelBinding
    private lateinit var hotel: ModelHotel
    private lateinit var hotelViewModel: HotelViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHotelBinding.inflate(layoutInflater)
        setContentView(binding.root)

        hotelViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HotelViewModel::class.java]
        val id = intent.getStringExtra(EXTRA_HOTEL) as String

        hotelViewModel.setDetailHotel(id)
        hotelViewModel.hotel.observe(this, { dataHotel ->
            if (dataHotel != null) {
                Glide.with(this)
                    .load(dataHotel.picture)
                    .apply(RequestOptions())
                    .into(binding.tvPicture)
                Glide.with(this)
                    .load(dataHotel.status)
                    .apply(RequestOptions())
                    .into(binding.statusItem)
                binding.tvNamaHotel.text = dataHotel.name
                binding.tvLokasiHotel.text = dataHotel.city
                binding.tvRating.text = "Rating: ${dataHotel.rating}"
                binding.tvDetailHotel.text = dataHotel.description
                supportActionBar?.title = "Detail ${dataHotel.name}"
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                hotel = dataHotel
                hotelViewModel.updateFavorite(hotel)
                showLoading(false)
            }
        })

        hotelViewModel.isFavorite.observe(this, {
            if (hotelViewModel.isFavorite.value == true) {
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
                showAlertDialog(ALERT_DIALOG_CLOSE)
            } else {
                hotelViewModel.insertFavorite(hotel)
            }
        }
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle = getString(R.string.delete)
        val dialogMessage = getString(R.string.dialog_close)
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(R.string.Yes) { _, _ ->
                if (isDialogClose) {
                    hotelViewModel.deleteFavorite(hotel)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.cancel()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_HOTEL = "extra_hotel"
        const val ALERT_DIALOG_CLOSE = 10
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.favorite -> {
                val f = Intent(this, FavoriteHotel::class.java)
                startActivity(f)
                true
            }
            R.id.setting -> {
                val s = Intent(this, SettingActivity::class.java)
                startActivity(s)
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> true
        }
    }


}