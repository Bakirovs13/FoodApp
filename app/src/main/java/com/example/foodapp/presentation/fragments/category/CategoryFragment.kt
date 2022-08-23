package com.example.foodapp.presentation.fragments.category

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.databinding.FragmentCategoryBinding
import com.example.foodapp.domain.models.CategoryList
import com.example.foodapp.presentation.activities.main.MainActivity
import com.example.foodapp.presentation.fragments.bottomsheet.CategoryBottomSheetFragment


class CategoryFragment : Fragment() {
   private lateinit var binding: FragmentCategoryBinding
   private val categoryAdapter by lazy { CategoryAdapter() }
    private val viewModel by lazy{
        (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoryBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareRV()
        observeCategoryData()
        onCategoryClick()

    }

    private fun onCategoryClick() {
        categoryAdapter.onLongItemCLick = {
            val args = Bundle()
            args.putString("str",it.strCategory)
            args.putString("desc",it.strCategoryDescription)
            args.putString("img",it.strCategoryThumb)
            val newFragment = CategoryBottomSheetFragment()
            newFragment.arguments = args
            newFragment.show(childFragmentManager, "TAG")
        }
    }

    private fun observeCategoryData() {
        viewModel.observeCategoriesLD().observe(viewLifecycleOwner){
            categoryAdapter.setList(it as ArrayList<CategoryList.Category>)
            Log.d("c",it[1].toString())
        }
    }


    private fun prepareRV() {
        binding.categoryRv.apply {
            adapter = categoryAdapter
            layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false)
        }
    }
}