package com.digirr.fitdiet.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.digirr.fitdiet.data.FoodProduct
import com.digirr.fitdiet.repo.FirebaseModel

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val fbRepo = FirebaseModel()

    val user = fbRepo.getUserData()

    val products = fbRepo.getProducts()

    fun addEatenProduct(product : FoodProduct) {
        fbRepo.addEatenProduct(product)
    }

}