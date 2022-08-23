package com.example.foodapp.presentation.fragments.home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.databinding.FragmentHomeBinding
import com.example.foodapp.domain.models.CategoryList
import com.example.foodapp.domain.models.PopularList
import com.example.foodapp.domain.models.MealList
import com.example.foodapp.presentation.activities.categoryItem.CategoryActivity
import com.example.foodapp.presentation.activities.main.MainActivity
import com.example.foodapp.presentation.activities.mealDetail.MealDetailActivity
import com.example.foodapp.presentation.fragments.category.CategoryAdapter
import com.example.foodapp.utils.Constant.CATEGORY_NAME
import com.example.foodapp.utils.Constant.MEAL_ID
import com.example.foodapp.utils.Constant.MEAL_NAME
import com.example.foodapp.utils.Constant.MEAL_THUMB


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private val viewModel  by lazy {(activity as MainActivity).viewModel }
    private val popularAdapter by lazy { PopularAdapter() }
    private val categoryAdapter by lazy { CategoryAdapter() }
    private lateinit var randomMeal:MealList.Meal


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRandomMeal()
        observeMealRandom()
        onRandomMealClick()

        viewModel.getPopularMeal()
        observePopularMeal()
        onPopularItemCLick()

        viewModel.getCategoryList()
        observeCategories()
        onCategoryClick()

        onSearchIconClick()
    }

    private fun onSearchIconClick() {
        binding.searchIv.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
    }

    private fun onCategoryClick() {
        categoryAdapter.onItemCLick = {
            val intent = Intent(activity,CategoryActivity::class.java)
            intent.putExtra(CATEGORY_NAME,it.strCategory)
            startActivity(intent)
        }
    }

    private fun observeCategories() {
        viewModel.observeCategoriesLD().observe(viewLifecycleOwner){categoryList->
                binding.categoryRecycler.apply {
                  layoutManager = GridLayoutManager(context,3,GridLayoutManager.VERTICAL,false )
                    binding.categoryRecycler.adapter = categoryAdapter
                    categoryAdapter.setList(categoryList as ArrayList<CategoryList.Category>)
                }
        }
    }

    private fun onPopularItemCLick() {
        popularAdapter.onItemCLick= { meal->
            val intent = Intent(activity,MealDetailActivity::class.java)
            intent.putExtra(MEAL_ID,meal.idMeal)
            intent.putExtra(MEAL_THUMB,meal.strMealThumb)
            intent.putExtra(MEAL_NAME,meal.strMeal)
            startActivity(intent)
        }
    }

    private fun observePopularMeal() {
        viewModel.observePopularLD().observe(viewLifecycleOwner
        ) {list->
            binding.popularRecycler.adapter = popularAdapter
            popularAdapter.setPopularMeals(list as ArrayList<PopularList.PopularMeal>)
        }
    }

    private fun onRandomMealClick() {
         binding.bannerCard.setOnClickListener {
             val intent = Intent(activity, MealDetailActivity::class.java)
             intent.putExtra(MEAL_ID,randomMeal.idMeal)
             intent.putExtra(MEAL_NAME,randomMeal.strMeal)
             intent.putExtra(MEAL_THUMB,randomMeal.strMealThumb)
             startActivity(intent)
         }
    }

    private fun observeMealRandom() {
        viewModel.observeRandomMealLD().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal?.strMealThumb)
                .into(binding.imageRandom)

            randomMeal = meal!!
        }
    }


}