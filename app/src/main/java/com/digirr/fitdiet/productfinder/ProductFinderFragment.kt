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
import com.digirr.fitdiet.data.User
import com.digirr.fitdiet.home.OnProductItemClick
import com.digirr.fitdiet.home.ProductAdapter
import com.digirr.fitdiet.registration.RegistrationViewModel
import com.digirr.fitdiet.repo.FirebaseModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_product_finder.*
import kotlinx.android.synthetic.main.fragment_product_finder.recyclerEatenProducts
import kotlin.math.ceil


class ProductFinderFragment : AbstractFragment(), OnProductItemClick {

    private val prodVM by viewModels<ProductFinderViewModel>()
    private val adapter = ProductAdapter(this)
    private var currentUser : User? = null

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
        prodVM.user.observe(viewLifecycleOwner, { user ->
            currentUser = user
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

    private fun addKcalToUserAccount(product: FoodProduct) {
        var tempUser = currentUser

        val tempKcal = tempUser?.eatenKcal?.plus(product.allKcal!!)
        val tempProtein = tempUser?.eatenProtein?.plus(product.protein!!)
        val tempCarbohydrates = tempUser?.eatenCarbohydrates?.plus(product.carbohydrates!!)
        val tempFat = tempUser?.eatenFat?.plus(product.fat!!)

        val kcalValues = mapOf(
            "eatenKcal" to tempKcal,
            "eatenProtein" to tempProtein,
            "eatenCarbohydrates" to tempCarbohydrates,
            "eatenFat" to tempFat
        ) as Map<String, Any>

        prodVM.updateProfileValues(kcalValues)
    }

    override fun onProductLongClick(product: FoodProduct, position: Int) {
        prodVM.addEatenProduct(product)
        addKcalToUserAccount(product)

        Toast.makeText(requireContext(), "Dodano spożyty produkt", Toast.LENGTH_SHORT).show()
    }

    override fun onProductShortClick(product: FoodProduct, position: Int) {
        Toast.makeText(requireContext(), "Tu beda otwierac sie szczegoly", Toast.LENGTH_SHORT).show()
    }

}