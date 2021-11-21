package com.digirr.fitdiet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import com.digirr.fitdiet.R

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_start)
    }
}