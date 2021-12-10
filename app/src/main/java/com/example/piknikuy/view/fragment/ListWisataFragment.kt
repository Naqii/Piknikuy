package com.example.piknikuy.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.piknikuy.R

class ListWisataFragment : Fragment(),  View.OnClickListener {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_wisata, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnToDetail: Button = view.findViewById(R.id.btn_toDetail)
        btnToDetail.setOnClickListener(this)
        (activity as AppCompatActivity).supportActionBar?.title = "Wisata"
    }
    override fun onClick(v: View) {
        val mDetailWisataFragment = DetailWisataFragment()
        val mFragmentManager = parentFragmentManager
        mFragmentManager.beginTransaction().apply {
            replace(R.id.frame_container, mDetailWisataFragment, DetailWisataFragment::class.java.simpleName)
            addToBackStack(null)
            commit()
        }
    }
}