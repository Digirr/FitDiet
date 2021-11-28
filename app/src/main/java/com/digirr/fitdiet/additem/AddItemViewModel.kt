package com.digirr.fitdiet.additem

import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.AndroidViewModel
import com.digirr.fitdiet.R
import com.digirr.fitdiet.data.FoodProduct
import com.digirr.fitdiet.repo.FirebaseModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_add_item.*
import java.io.ByteArrayOutputStream
import java.lang.NumberFormatException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AddItemViewModel(application: Application) : AndroidViewModel(application) {

    private val fbRepo = FirebaseModel()
    val id = key_generator()
    var byteArray : ByteArray? = null

    private fun addProduct(product : FoodProduct) {
        fbRepo.addFoodProduct(product)
    }

    private fun uploadProductImage(bytes: ByteArray){
        fbRepo.uploadFoodImage(bytes)
    }

    fun addProductUserClick(
        titleAddET : EditText,
        addDescriptionET: EditText,
        allKcalET : EditText,
        weightET : EditText,
        proteinET : EditText,
        carbohydratesET : EditText,
        fatET : EditText,
        productSingleImage : ImageView
    ) : Boolean {
        val title = titleAddET.text.toString()
        val description = addDescriptionET.text.toString()

            try {
            val allKcal = Integer.parseInt(allKcalET.text.toString())
            val protein = Integer.parseInt(proteinET.text.toString())
            val carbohydrates = Integer.parseInt(carbohydratesET.text.toString())
            val fat = Integer.parseInt(fatET.text.toString())
            val weight = Integer.parseInt(weightET.text.toString())

            if(validData(title, description, allKcal, weight, protein, carbohydrates, fat, productSingleImage)) {
                val foodProduct = FoodProduct(
                    byteArray.toString(), "", title, description, allKcal, weight, protein, carbohydrates, fat
                )
                addProduct(foodProduct)
                uploadProductImage(byteArray!!)
                showToast(getStringFromResources(R.string.product_or_incorrect))
                return true
            }
        } catch (e : NumberFormatException) {
            showToast(getStringFromResources(R.string.values_format_incorrect))
        }
        return false
    }

    private fun validData(title: String, description: String, allKcal : Int, weight : Int, protein : Int, carbohydrates : Int, fat : Int, productSingleImage: ImageView) : Boolean {
        if(productSingleImage.drawable == null){
            showToast(getStringFromResources(R.string.add_image_validator))
            return false
        } else if(title.isEmpty() || title.length>30) {
            showToast(getStringFromResources(R.string.title_too_long))
            return false
        } else if(description.isEmpty() || description.length > 300) {
            showToast(getStringFromResources(R.string.text_too_long))
            return false
        } else if(weight.toString().isEmpty() || weight > 999999){
            showToast(getStringFromResources(R.string.weight_empty_or_incorrect))
            return false
        } else if(allKcal.toString().isEmpty() || allKcal > 9999){
            showToast(getStringFromResources(R.string.calories_empty_or_incorrect))
            return false
        } else if(protein.toString().isEmpty() || protein > 9999) {
            showToast(getStringFromResources(R.string.proteins_empty_or_incorrect))
            return false
        } else if(carbohydrates.toString().isEmpty() || carbohydrates > 9999) {
            showToast(getStringFromResources(R.string.carbohydrates_empty_or_incorrect))
            return false
        } else if(fat.toString().isEmpty() || allKcal > 9999){
            showToast(getStringFromResources(R.string.fat_empty_or_incorrect))
            return false
        } else {
            return true
        }
    }

    private var imageUri: Uri? = null

    fun handleCreatingImage(productSingleImage: ImageView, data: Intent?) {
        productSingleImage.visibility = View.VISIBLE
        imageUri = data?.data
        productSingleImage.setImageURI(imageUri)

        val imageBitmap = productSingleImage.drawable.toBitmap()

        val stream = ByteArrayOutputStream()
        val result = imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)  //Kodowanie w tablicy byteow stream
        val byteArr = stream.toByteArray()    //Zamiana na format bytestream

        if(result){
            byteArray = byteArr
        }
    }

    private fun getStringFromResources(incomingId : Int) : String {
        return getApplication<Application>().resources.getString(incomingId)
    }

    fun showToast(message : String) {
        Toast.makeText(getApplication(), message, Toast.LENGTH_SHORT).show()
    }

    private fun key_generator() : String {
        val array = ByteArray(20) // length is bounded by 50
        Random.nextBytes(array)
        return String(array, Charset.forName("UTF-8"))
    }
}