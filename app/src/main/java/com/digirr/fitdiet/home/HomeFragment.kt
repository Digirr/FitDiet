package com.digirr.fitdiet.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.digirr.fitdiet.R
import com.digirr.fitdiet.activities.AddProductActivity
import com.digirr.fitdiet.data.FoodProduct
import com.digirr.fitdiet.data.User
import com.digirr.fitdiet.profile.ProfileViewModel
import com.digirr.fitdiet.repo.FirebaseModel
import kotlinx.android.synthetic.main.fragment_add_item.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*


class HomeFragment : Fragment(), OnProductItemClick {

    private val homeVm by viewModels<HomeViewModel>()
    private val adapter = ProductAdapter(this)
    private var currentUser : User? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerEatenProducts.layoutManager = LinearLayoutManager(requireContext())
        recyclerEatenProducts.adapter = adapter
        openSearcher()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeVm.user.observe(viewLifecycleOwner, { user ->
            bindUserData(user)
            currentUser = user
        })
        homeVm.eatenProducts.observe(viewLifecycleOwner, { list ->
            adapter.setProducts(list)
        })
    }

    private fun openSearcher() {
        openSearcherFloatingActionButton.setOnClickListener {
            val intent = Intent(requireContext(), AddProductActivity::class.java)
            startActivity(intent)
        }
    }

    private fun bindUserData(user : User) {
        currentKcal.text = user.eatenKcal.toString()
        maxKcal.text = user.maxKcal.toString()
        currentProtein.text = user.eatenProtein.toString()
        maxProtein.text = user.maxProtein.toString()
        currentCarbohydrates.text = user.eatenProtein.toString()
        maxCarbohydrates.text = user.maxCarbohydrates.toString()
        currentFat.text = user.eatenFat.toString()
        maxFat.text = user.maxFat.toString()
    }

    private fun removeKcalFromUserAccount(product: FoodProduct) {
        var tempUser = currentUser

        val tempKcal = tempUser?.eatenKcal?.minus(product.allKcal!!)
        val tempProtein = tempUser?.eatenProtein?.minus(product.protein!!)
        val tempCarbohydrates = tempUser?.eatenCarbohydrates?.minus(product.carbohydrates!!)
        val tempFat = tempUser?.eatenFat?.minus(product.fat!!)

        currentKcal.text = tempKcal.toString()
        currentProtein.text = tempProtein.toString()
        currentCarbohydrates.text = tempCarbohydrates.toString()
        currentFat.text = tempFat.toString()

        val kcalValues = mapOf(
            "eatenKcal" to tempKcal,
            "eatenProtein" to tempProtein,
            "eatenCarbohydrates" to tempCarbohydrates,
            "eatenFat" to tempFat
        ) as Map<String, Any>

        homeVm.updateProfileValues(kcalValues)
    }

    override fun onProductLongClick(product: FoodProduct, position: Int) {
        homeVm.removeEatenProduct(product)
        adapter.removeProduct(product, position)

        removeKcalFromUserAccount(product)

        Toast.makeText(requireContext(), "Usunięto z listy spożytych produktów", Toast.LENGTH_SHORT).show()
    }

    override fun onProductShortClick(product: FoodProduct, position: Int) {
        //Wejscie w szczegoly
    }

}