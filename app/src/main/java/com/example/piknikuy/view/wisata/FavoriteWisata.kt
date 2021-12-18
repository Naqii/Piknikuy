package com.example.piknikuy.view.wisata

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.R
import com.example.piknikuy.adapter.WisataAdapter
import com.example.piknikuy.databinding.ActivityFavoriteWisataBinding
import com.example.piknikuy.model.ModelWisata
import com.example.piknikuy.viewModel.WisataViewModel

class FavoriteWisata : AppCompatActivity() {

    private lateinit var favoriteAdapter: WisataAdapter
    private lateinit var favoriteWisataBinding: ActivityFavoriteWisataBinding
    private lateinit var wisataViewModel: WisataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteWisataBinding = ActivityFavoriteWisataBinding.inflate(layoutInflater)
        setContentView(favoriteWisataBinding.root)

        supportActionBar?.title = getString(R.string.favorite_wisata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        wisataViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[WisataViewModel::class.java]
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            favoriteWisataBinding.rvWisata.layoutManager = GridLayoutManager(this, 2)
        } else {
            favoriteWisataBinding.rvWisata.layoutManager = LinearLayoutManager(this)
        }

        favoriteAdapter = WisataAdapter()
        favoriteWisataBinding.rvWisata.adapter = favoriteAdapter
        favoriteAdapter.setOnItemClickCallback(object : WisataAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ModelWisata) {
                val moveIntent = Intent(this@FavoriteWisata, DetailWisata::class.java)
                moveIntent.putExtra(DetailWisata.EXTRA_WISATA, data.id)
                startActivity(moveIntent)
            }
        })

        wisataViewModel.setSearchWisata()
        progressBarDisplay(true)
        wisataViewModel.favorite.observe(this, { wisataItem ->
            if (wisataItem != null) {
                favoriteAdapter.listWisata = ArrayList(wisataItem)
                progressBarDisplay(false)
            }
        })
    }

    private fun progressBarDisplay(state: Boolean) {
        favoriteWisataBinding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}