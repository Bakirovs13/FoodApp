package com.example.foodapp.presentation.fragments.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemMealBinding
import com.example.foodapp.domain.models.MealList

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {


  inner  class ViewHolder (var binding: ItemMealBinding):RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object :DiffUtil.ItemCallback<MealList.Meal>(){

        override fun areItemsTheSame(oldItem: MealList.Meal, newItem: MealList.Meal): Boolean {
            return oldItem.idMeal == newItem.idMeal
        }

        override fun areContentsTheSame(oldItem: MealList.Meal, newItem: MealList.Meal): Boolean {
            return oldItem ==newItem
        }
    }

    val differ = AsyncListDiffer(this,diffUtil)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(
           ItemMealBinding.inflate(
               LayoutInflater.from(parent.context),
               parent,
               false)
       )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(holder){
           with(differ.currentList[position]){
               Glide.with(itemView)
                   .load(this.strMealThumb)
                   .into(binding.mealImg)
               binding.mealNameTv.text = this.strMeal
           }
       }
    }

    override fun getItemCount(): Int  = differ.currentList.size


}