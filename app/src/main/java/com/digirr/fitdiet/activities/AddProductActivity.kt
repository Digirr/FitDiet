package com.digirr.fitdiet.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.digirr.fitdiet.R
import com.digirr.fitdiet.additem.AddItemFragmentDirections
import com.digirr.fitdiet.productfinder.ProductFinderFragmentDirections
import kotlinx.android.synthetic.main.fragment_product_finder.*

class AddProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_add_product)

//        val navController = findNavController(R.id.addProductNavHost)
//
//        AppBarConfiguration(setOf(R.id.productFinderFragment, R.id.addItemFragment))
//
//        //setupActionBarWithNavController(navController)
//
//        //Przejscie, mozna dodac do onClick
//        navController.navigate(AddItemFragmentDirections.actionAddItemFragmentToProductFinderFragment().actionId)

//        addItemButton.setOnClickListener {
//            Toast.makeText(applicationContext, "Siema", Toast.LENGTH_LONG).show()
//            addItemButton.text = "Zmienia sie"
//            findNavController().navigate(ProductFinderFragmentDirections.actionProductFinderFragmentToAddItemFragment().actionId)
//        }

    }
}