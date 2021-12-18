package com.example.piknikuy.view.wisata

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.piknikuy.R
import com.example.piknikuy.api.ApiConfig
import com.example.piknikuy.databinding.ActivityDetailWisataBinding
import com.example.piknikuy.model.ModelWisata
import com.example.piknikuy.view.resto.DetailResto
import com.example.piknikuy.viewModel.WisataViewModel

class DetailWisata : AppCompatActivity(), View.OnClickListener {

    private lateinit var detailWisataBinding: ActivityDetailWisataBinding
    private lateinit var wisata: ModelWisata
    private lateinit var wisataViewModel: WisataViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailWisataBinding = ActivityDetailWisataBinding.inflate(layoutInflater)
        setContentView(detailWisataBinding.root)

        val actionbar = supportActionBar

        wisataViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[WisataViewModel::class.java]
        val id = intent.getStringExtra(EXTRA_WISATA) as String

        wisataViewModel.setDetailWisata(id)
        wisataViewModel.wisata.observe(this, {dataWisata ->
            if (dataWisata != null) {
                Glide.with(this)
                    .load(ApiConfig.BASE_IMG_URL_RESTO + dataWisata.city)
                    .apply(RequestOptions())
                    .into(detailWisataBinding.tvPicture)
                detailWisataBinding.tvNamaWisata.text = dataWisata.name
                detailWisataBinding.tvLokasiWisata.text = dataWisata.address + ", " + dataWisata.city
                detailWisataBinding.tvRating.text = "Rating: ${dataWisata.rating}"
                detailWisataBinding.tvDetailWisata.text = dataWisata.description
                actionbar!!.title = "Detail ${dataWisata.name}"
                actionbar.setDisplayHomeAsUpEnabled(true)
                wisata = dataWisata
                wisataViewModel.updateFavorite(wisata)
                showLoading(false)
            }
        })

        wisataViewModel.isFavorite.observe(this, {
            if(wisataViewModel.isFavorite.value == true){
                detailWisataBinding.btnFav.setImageResource(R.drawable.ic_favorite)
            } else {
                detailWisataBinding.btnFav.setImageResource(R.drawable.ic_favorite_border)
            }
        })
        detailWisataBinding.btnFav.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        if (view.id == R.id.btn_fav) {
            if (wisataViewModel.isFavorite.value == true) {
                showAlertDialog(DetailResto.ALERT_DIALOG_CLOSE)
            } else {
                wisataViewModel.insertFavorite(wisata)
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
                    wisataViewModel.deleteFavorite(wisata)
                    showToast(getString(R.string.deleted))
                }
                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel()
            }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun showLoading(state: Boolean) {
        detailWisataBinding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_WISATA = "extra_wisata"
        const val ALERT_DIALOG_CLOSE = 30
    }
}