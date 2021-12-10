package com.example.piknikuy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.piknikuy.R
import com.example.piknikuy.databinding.ActivityWisataBinding
import com.example.piknikuy.view.fragment.ListWisataFragment

class WisataActivity : AppCompatActivity() {
    private lateinit var wisataActivityBinding: ActivityWisataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        wisataActivityBinding = ActivityWisataBinding.inflate(layoutInflater)
        setContentView(wisataActivityBinding.root)

        supportActionBar?.title = getString(R.string.wisata)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mFragmentManager = supportFragmentManager
        val mListWisataFragment = ListWisataFragment()
        val fragment = mFragmentManager.findFragmentByTag(ListWisataFragment::class.java.simpleName)
        if (fragment !is ListWisataFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mListWisataFragment, ListWisataFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}