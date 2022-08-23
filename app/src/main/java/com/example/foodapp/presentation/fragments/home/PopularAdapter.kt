package com.example.foodapp.presentation.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemPopularBinding
import com.example.foodapp.domain.models.PopularList

class PopularAdapter: RecyclerView.Adapter<PopularAdapter.ViewHolder>() {

    private var popularList = ArrayList<PopularList.PopularMeal>()
   lateinit var onItemCLick:((PopularList.PopularMeal)->Unit)



    @SuppressLint("NotifyDataSetChanged")
    fun setPopularMeals(list:ArrayList<PopularList.PopularMeal>){
        this.popularList = list
        notifyDataSetChanged()
    }

   inner class ViewHolder (var binding: ItemPopularBinding): RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding  = ItemPopularBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder){
            with(popularList[position]){
                itemView.setOnClickListener {
                    onItemCLick.invoke(this)
                }
              Glide.with(itemView)
                  .load(this.strMealThumb)
                  .into(binding.popularMealImg)
            }
        }
    }

    override fun getItemCount(): Int  = popularList.size


}