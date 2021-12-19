package com.example.piknikuy.view.wisata

import android.app.SearchManager
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.piknikuy.R
import com.example.piknikuy.adapter.WisataAdapter
import com.example.piknikuy.databinding.ActivityWisataBinding
import com.example.piknikuy.model.ModelWisata
import com.example.piknikuy.setting.SettingActivity
import com.example.piknikuy.viewModel.WisataViewModel

class WisataActivity : AppCompatActivity() {

    private lateinit var wisataActivityBinding: ActivityWisataBinding
    private lateinit var wisataAdapter: WisataAdapter
    private lateinit var wisataViewModel: WisataViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wisataActivityBinding = ActivityWisataBinding.inflate(layoutInflater)
        setContentView(wisataActivityBinding.root)

        supportActionBar?.title = getString(R.string.wisata)

        wisataViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[WisataViewModel::class.java]
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            wisataActivityBinding.rvWisata.layoutManager = GridLayoutManager(this, 2)
        } else {
            wisataActivityBinding.rvWisata.layoutManager = LinearLayoutManager(this)
        }

        wisataAdapter = WisataAdapter()
        wisataActivityBinding.rvWisata.adapter = wisataAdapter
        wisataAdapter.setOnItemClickCallback(object : WisataAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ModelWisata) {
                val moveIntent = Intent(this@WisataActivity, DetailWisata::class.java)
                moveIntent.putExtra(DetailWisata.EXTRA_WISATA, data.id)
                startActivity(moveIntent)
            }
        })

        wisataViewModel.setSearchWisata()
        progressBarDisplay(true)
        wisataViewModel.listWisata.observe(this, { wisataItem ->
            if (wisataItem != null) {
                wisataAdapter.listWisata = wisataItem
                progressBarDisplay(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search) + " Wisata"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                wisataViewModel.setSearchWisata(query)
                progressBarDisplay(true)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    progressBarDisplay(false)
                } else {
                    wisataViewModel.setSearchWisata(newText)
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
                val f = Intent(this, FavoriteWisata::class.java)
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
        wisataActivityBinding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }
}