package com.example.foodapp.presentation.fragments.category

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.ItemCategoryBinding
import com.example.foodapp.domain.models.CategoryList

class CategoryAdapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    lateinit var onItemCLick:((CategoryList.Category)->Unit)
    lateinit var onLongItemCLick:((CategoryList.Category)->Unit)

    private var categoryList = ArrayList<CategoryList.Category>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: ArrayList<CategoryList.Category>){
        this.categoryList = list
        notifyDataSetChanged()
    }

  inner  class ViewHolder(var binding: ItemCategoryBinding) :RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      with(holder){
          with(categoryList[position]){
              itemView.setOnClickListener {
                  onItemCLick.invoke(this)
              }
             itemView.setOnLongClickListener {
                 onLongItemCLick.invoke(this)
               true
             }
              binding.categoryTv.text = this.strCategory
              Glide.with(itemView)
                  .load(this.strCategoryThumb)
                  .into(binding.categoryImg)
          }
      }
    }



    override fun getItemCount(): Int  = categoryList.size
}