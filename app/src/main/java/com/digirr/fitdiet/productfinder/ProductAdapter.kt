package com.digirr.fitdiet.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digirr.fitdiet.R
import com.digirr.fitdiet.data.FoodProduct

class ProductAdapter(private val listener: OnProductItemClick) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private val productsList = ArrayList<FoodProduct>()

    fun setProducts(list : List<FoodProduct>) {
        productsList.clear()
        productsList.addAll(list)
        notifyDataSetChanged()
    }

    //Refresh recyclerview after remove building in profile
    fun removeProduct(product : FoodProduct, position: Int) {
        productsList.remove(product)
        notifyItemRemoved(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_row, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        bindData(holder)
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    private fun bindData(holder : ProductViewHolder) {
        val topic = holder.itemView.findViewById<TextView>(R.id.topicItem)
        val weight = holder.itemView.findViewById<TextView>(R.id.weightItem)
        val allKcal = holder.itemView.findViewById<TextView>(R.id.allKcalItem)
        val carbohydrates = holder.itemView.findViewById<TextView>(R.id.carbohydratesItem)
        val protein = holder.itemView.findViewById<TextView>(R.id.proteinItem)
        val fat = holder.itemView.findViewById<TextView>(R.id.fatItem)
        val image = holder.itemView.findViewById<ImageView>(R.id.productImage)

        topic.text = "  " + productsList[holder.adapterPosition].productName
        weight.text = (weight.text.toString() + " " + productsList[holder.adapterPosition].weight) + " g"
        allKcal.text = (allKcal.text.toString() + " " + productsList[holder.adapterPosition].allKcal)
        carbohydrates.text = (carbohydrates.text.toString() + " " + productsList[holder.adapterPosition].carbohydrates)
        protein.text = (protein.text.toString() + " " + productsList[holder.adapterPosition].protein)
        fat.text = (fat.text.toString() + " " + productsList[holder.adapterPosition].fat)

        //HTTP: Glide
        Glide.with(holder.itemView)
            .load(productsList[holder.adapterPosition].image)
            .into(image)

    }


    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener {
                listener.onProductLongClick(productsList[adapterPosition], adapterPosition)
                true
            }
            view.setOnClickListener {
                listener.onProductShortClick(productsList[adapterPosition], adapterPosition)
                true
            }
        }
    }
}

interface OnProductItemClick {
    fun onProductLongClick(product : FoodProduct, position : Int)
    fun onProductShortClick(product : FoodProduct, position: Int)
}