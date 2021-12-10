package com.example.piknikuy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.piknikuy.R
import com.example.piknikuy.databinding.ActivityHotelBinding
import com.example.piknikuy.view.fragment.ListHotelFragment

class HotelActivity : AppCompatActivity() {

    private lateinit var hotelActivityBinding: ActivityHotelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hotelActivityBinding = ActivityHotelBinding.inflate(layoutInflater)
        setContentView(hotelActivityBinding.root)

        supportActionBar?.title = getString(R.string.hotel)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val mFragmentManager = supportFragmentManager
        val mListHotelFragment = ListHotelFragment()
        val fragment = mFragmentManager.findFragmentByTag(ListHotelFragment::class.java.simpleName)
        if (fragment !is ListHotelFragment) {
            mFragmentManager
                .beginTransaction()
                .add(R.id.frame_container, mListHotelFragment, ListHotelFragment::class.java.simpleName)
                .commit()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}