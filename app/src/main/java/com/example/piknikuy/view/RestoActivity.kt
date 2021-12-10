package com.example.piknikuy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.piknikuy.R
import com.example.piknikuy.databinding.ActivityRestoBinding
import com.example.piknikuy.view.fragment.ListRestoFragment

class RestoActivity : AppCompatActivity() {


    private lateinit var restoActivityBinding: ActivityRestoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoActivityBinding = ActivityRestoBinding.inflate(layoutInflater)
        setContentView(restoActivityBinding.root)

        supportActionBar?.title = getString(R.string.resto)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mFragmentManager = supportFragmentManager
        val mListRestoFragment = ListRestoFragment()
        val fragment = mFragmentManager.findFragmentByTag(ListRestoFragment::class.java.simpleName)
        if (fragment !is ListRestoFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mListRestoFragment, ListRestoFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}