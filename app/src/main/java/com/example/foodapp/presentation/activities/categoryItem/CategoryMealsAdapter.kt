package com.example.foodapp.presentation.activities.categoryItem

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemMealBinding
import com.example.foodapp.domain.models.PopularList

class CategoryMealsAdapter : RecyclerView.Adapter<CategoryMealsAdapter.ViewHolder>() {

    private var mealsList = ArrayList<PopularList.PopularMeal>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list:ArrayList<PopularList.PopularMeal>){
        this.mealsList = list
        notifyDataSetChanged()
    }

    inner class ViewHolder(var binding: ItemMealBinding) :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMealBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(mealsList[position]){
               Glide.with(itemView)
                   .load(this.strMealThumb)
                   .into(binding.mealImg)
                binding.mealNameTv.text = this.strMeal
            }
        }
    }

    override fun getItemCount(): Int = mealsList.size
}