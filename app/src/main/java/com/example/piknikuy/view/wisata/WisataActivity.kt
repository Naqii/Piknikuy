package com.example.piknikuy.view.wisata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.piknikuy.R
import com.example.piknikuy.adapter.WisataAdapter
import com.example.piknikuy.databinding.ActivityWisataBinding
import com.example.piknikuy.viewModel.WisataViewModel

class WisataActivity : AppCompatActivity() {

    private lateinit var wisataActivityBinding: ActivityWisataBinding
    private lateinit var wisataAdapter: WisataAdapter
    private lateinit var wisataViewModel: WisataViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wisata)
    }
}