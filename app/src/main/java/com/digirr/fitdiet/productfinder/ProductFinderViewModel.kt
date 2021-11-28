package com.digirr.fitdiet.productfinder

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.digirr.fitdiet.data.FoodProduct
import com.digirr.fitdiet.repo.FirebaseModel

class ProductFinderViewModel (application: Application) : AndroidViewModel(application){
    private val fbRepo = FirebaseModel()

    val user = fbRepo.getUserData()
    val products = fbRepo.getProducts()

    fun addEatenProduct(product : FoodProduct) {
        fbRepo.addEatenProduct(product)
    }

    fun updateProfileValues(map: Map<String, Any>) {
        fbRepo.updateProfileValues(map)
    }

}