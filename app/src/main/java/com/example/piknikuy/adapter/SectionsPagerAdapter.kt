package com.example.piknikuy.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.piknikuy.view.fragment.*

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = RestoFragment()
            1 -> fragment = HotelFragment()
            2 -> fragment = WisataFragment()
        }
        return fragment as Fragment
    }
}