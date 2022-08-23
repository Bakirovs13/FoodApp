package com.example.foodapp.presentation.fragments.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.foodapp.databinding.FragmentCategoryBottomSheetBinding
import com.example.foodapp.presentation.activities.categoryItem.CategoryActivity
import com.example.foodapp.utils.Constant.CATEGORY_NAME
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class CategoryBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCategoryBottomSheetBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBottomSheetBinding.inflate(layoutInflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        onBottomSheetCLick()
    }

    private fun onBottomSheetCLick() {
        binding.myBottomSheet.setOnClickListener {
            val intent = Intent(activity, CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME,categoryName)
            startActivity(intent)
        }
    }

    private var categoryName = ""
    private fun getData() {
        val mArgs = arguments
        val name = mArgs!!.getString("str")
        categoryName = name.toString()
        val description = mArgs.getString("desc")
        val img = mArgs.getString("img")
        binding.categoryNameTv.text = name
        binding.categoryDescTv.text = description
        Glide.with(requireView())
            .load(img)
            .into(binding.categpryImg)
    }



}