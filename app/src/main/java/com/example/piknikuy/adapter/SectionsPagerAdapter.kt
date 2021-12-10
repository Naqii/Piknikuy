package com.example.piknikuy.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.piknikuy.view.fragment.TopVisitorFragment
import com.example.piknikuy.view.fragment.TrendFragment

class SectionsPagerAdapter (activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position){
            0 -> fragment = TrendFragment()
            1 -> fragment = TopVisitorFragment()
        }
        return  fragment as Fragment
    }

}