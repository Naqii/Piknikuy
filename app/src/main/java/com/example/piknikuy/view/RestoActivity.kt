package com.example.piknikuy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.piknikuy.databinding.ActivityRestoBinding

class RestoActivity : AppCompatActivity() {

    private lateinit var restoActivityBinding: ActivityRestoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoActivityBinding = ActivityRestoBinding.inflate(layoutInflater)
        setContentView(restoActivityBinding.root)


    }
}