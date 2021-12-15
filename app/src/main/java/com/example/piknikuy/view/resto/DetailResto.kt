package com.example.piknikuy.view.resto

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.piknikuy.R
import com.example.piknikuy.api.ApiConfig
import com.example.piknikuy.databinding.ActivityDetailRestoBinding
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.viewModel.RestoViewModel

class DetailResto : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding: ActivityDetailRestoBinding
    private lateinit var resto: ModelResto
    private lateinit var restoViewModel: RestoViewModel

    companion object {

        const val EXTRA_RESTO = "extra_resto"

    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailRestoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar

        restoViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RestoViewModel::class.java]
        val id = intent.getStringExtra(EXTRA_RESTO) as String

        restoViewModel.setDetailResto(id)
        restoViewModel.resto.observe(this, {dataResto ->
            if(dataResto != null) {
                Glide.with(this)
                    .load(ApiConfig.BASE_IMG_URL_RESTO + dataResto.pictureId)
                    .apply(RequestOptions())
                    .into(binding.tvPicture)
                binding.tvNamaResto.text = dataResto.name
                binding.tvLokasiResto.text = dataResto.address + ", " + dataResto.city
                binding.tvRating.text = "Rating: ${dataResto.rating}"
                binding.tvDetailResto.text = dataResto.description
                actionbar!!.title = "Detail ${dataResto.name}"
                actionbar.setDisplayHomeAsUpEnabled(true)
                resto = dataResto
                restoViewModel.updateFavorite(resto)
                showLoading(false)
            }
        })

        restoViewModel.isFavorite.observe(this, {
            if(restoViewModel.isFavorite.value == true){
                binding.btnFav.setImageResource(R.drawable.ic_favorite)
            } else {
                binding.btnFav.setImageResource(R.drawable.ic_favorite_border)
            }
        })
        binding.btnFav.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_fav) {
            if (restoViewModel.isFavorite.value == true) {
                restoViewModel.deleteFavorite(resto)
            } else {
                restoViewModel.insertFavorite(resto)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}