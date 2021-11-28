package com.digirr.fitdiet.additem

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.digirr.fitdiet.R
import com.digirr.fitdiet.abstraction.AbstractFragment
import com.digirr.fitdiet.loadingscreen.ScreenLoadFragmentDirections
import com.digirr.fitdiet.productfinder.ProductFinderFragmentDirections
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.fragment_product_finder.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.lang.NumberFormatException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import kotlin.random.Random

class AddItemFragment : AbstractFragment() {

    private val addVm by viewModels<AddItemViewModel>()
    private val RESULT_LOAD_IMAGE = 100

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_item, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        addImageButton.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, RESULT_LOAD_IMAGE)
        }
        addProductButton.setOnClickListener {
            var returnValue = addVm.addProductUserClick(titleAddET, addDescriptionET, allKcalET, weightET, proteinET, carbohydratesET, fatET, productSingleImage)
            if(returnValue) {
                startMainViewApp()
            }
        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == RESULT_LOAD_IMAGE) {
            addVm.handleCreatingImage(productSingleImage, data)
        }
    }

}