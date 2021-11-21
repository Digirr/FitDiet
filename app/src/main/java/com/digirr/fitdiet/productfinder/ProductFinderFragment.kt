package com.digirr.fitdiet.productfinder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.digirr.fitdiet.R
import com.digirr.fitdiet.abstraction.AbstractFragment
import com.digirr.fitdiet.data.FoodProduct
import com.digirr.fitdiet.home.OnProductItemClick
import com.digirr.fitdiet.home.ProductAdapter
import com.digirr.fitdiet.registration.RegistrationViewModel
import kotlinx.android.synthetic.main.fragment_product_finder.*


class ProductFinderFragment : AbstractFragment(), OnProductItemClick {

    private val prodVM by viewModels<ProductFinderViewModel>()
    private val adapter = ProductAdapter(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val callback = requireActivity().onBackPressedDispatcher.addCallback(this) {
            startMainViewApp()
        }
        callback.isEnabled = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_finder, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        prodVM.products.observe(viewLifecycleOwner, { list ->
            adapter.setProducts(list)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerEatenProducts.layoutManager = LinearLayoutManager(requireContext())
        recyclerEatenProducts.adapter = adapter
        addItemButton.setOnClickListener {
            findNavController().navigate(ProductFinderFragmentDirections.actionProductFinderFragmentToAddItemFragment().actionId)
        }
    }

    override fun onProductLongClick(product: FoodProduct, position: Int) {
        prodVM.addEatenProduct(product)
        Toast.makeText(requireContext(), "Dodano spożyty produkt", Toast.LENGTH_SHORT).show()
    }

    override fun onProductShortClick(product: FoodProduct, position: Int) {
        Toast.makeText(requireContext(), "Tu beda otwierac sie szczegoly", Toast.LENGTH_SHORT).show()
    }

}