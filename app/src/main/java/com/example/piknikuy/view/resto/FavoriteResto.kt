package com.example.piknikuy.view.resto

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.adapter.RestoAdapter
import com.example.piknikuy.databinding.ActivityFavoriteRestoBinding
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.viewModel.RestoViewModel

class FavoriteResto : AppCompatActivity() {

    private lateinit var favoriteAdapter: RestoAdapter
    private lateinit var binding: ActivityFavoriteRestoBinding
    private lateinit var restoViewModel: RestoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteRestoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionbar = supportActionBar
        actionbar!!.title = "Favorite Resto"
        actionbar.setDisplayHomeAsUpEnabled(true)

        restoViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RestoViewModel::class.java]
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvResto.layoutManager = GridLayoutManager(this, 2)
        } else  {
            binding.rvResto.layoutManager = LinearLayoutManager(this)
        }

        favoriteAdapter = RestoAdapter()
        binding.rvResto.adapter = favoriteAdapter
        favoriteAdapter.setOnItemClickCallback(object : RestoAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ModelResto) {
                val moveIntent = Intent(this@FavoriteResto, DetailResto::class.java)
                moveIntent.putExtra(DetailResto.EXTRA_RESTO, data.id)
                startActivity(moveIntent)
            }
        })

        restoViewModel.setSearchResto()
        progressBarDisplay(true)
        restoViewModel.favorite.observe(this, { userItem->
            if (userItem != null) {
                favoriteAdapter.listResto = ArrayList(userItem)
                progressBarDisplay(false)
            }
        })
    }

    private fun progressBarDisplay(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
            binding.rvResto.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
            binding.rvResto.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}