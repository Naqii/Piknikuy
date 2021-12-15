package com.example.piknikuy.view.resto

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
import com.example.piknikuy.adapter.RestoAdapter
import com.example.piknikuy.databinding.ActivityRestoBinding
import com.example.piknikuy.model.ModelResto
import com.example.piknikuy.viewModel.RestoViewModel

class RestoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRestoBinding
    private lateinit var restoAdapter: RestoAdapter
    private lateinit var restoViewModel: RestoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.restaurant)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        restoViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[RestoViewModel::class.java]
        if(applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            binding.rvResto.layoutManager = GridLayoutManager(this, 2)
        } else  {
            binding.rvResto.layoutManager = LinearLayoutManager(this)
        }

        restoAdapter = RestoAdapter()
        binding.rvResto.adapter = restoAdapter
        restoAdapter.setOnItemClickCallback(object : RestoAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ModelResto) {
                val moveIntent = Intent(this@RestoActivity, DetailResto::class.java)
                moveIntent.putExtra(DetailResto.EXTRA_RESTO, data.id)
                startActivity(moveIntent)
            }
        })

        restoViewModel.setSearchResto()
        progressBarDisplay(true)
        restoViewModel.listResto.observe(this, {restoItem ->
            if (restoItem != null) {
                restoAdapter.listResto = restoItem
                progressBarDisplay(false)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search) + " restaurant"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                restoViewModel.setSearchResto(query)
                progressBarDisplay(true)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    progressBarDisplay(false)
                } else {
                    restoViewModel.setSearchResto(newText)
                    progressBarDisplay(true)
                }
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.favorite){
            val myIntent = Intent(this@RestoActivity, FavoriteResto::class.java)
            startActivity(myIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun progressBarDisplay(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.INVISIBLE
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}