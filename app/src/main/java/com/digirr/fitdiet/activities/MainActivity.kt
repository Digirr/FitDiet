package com.digirr.fitdiet.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.digirr.fitdiet.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_main)

        //Lapie widok bottomNavigationView
        val navView: BottomNavigationView = findViewById(R.id.bottomNavView)

        //Potem nav controller
        val navController = findNavController(R.id.mainNavHost)

        //Polaczenie z paskiem na gorze, aby tytuly sie zgadzaly na gorze
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.homeFragment, R.id.statsFragment, R.id.profileFragment))

        setupActionBarWithNavController(navController)
        navView.setupWithNavController(navController)
    }
}