package com.digirr.fitdiet.productfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.digirr.fitdiet.R
import kotlinx.android.synthetic.main.fragment_product_finder.*


class ProductFinderFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_finder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addItemButton.setOnClickListener {
            Toast.makeText(context, "Siema", Toast.LENGTH_LONG).show()
            addItemButton.text = "Zmienia sie"
            findNavController().navigate(ProductFinderFragmentDirections.actionProductFinderFragmentToAddItemFragment().actionId)
        }
    }

}