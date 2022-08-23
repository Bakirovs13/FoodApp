package com.example.foodapp.presentation.activities.categoryItem

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.foodapp.databinding.ActivityCategoryBinding
import com.example.foodapp.domain.models.PopularList
import com.example.foodapp.utils.Constant.CATEGORY_NAME

class CategoryActivity : AppCompatActivity() {
    lateinit var binding: ActivityCategoryBinding
    private val mealsAdapter:CategoryMealsAdapter by lazy { CategoryMealsAdapter() }
    val viewModel :CategoryMealsViewModel by lazy { ViewModelProvider(this)[CategoryMealsViewModel::class.java] }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.getMealsByCategory(intent.getStringExtra(CATEGORY_NAME)!!)
        viewModel.observeMealsLiveData().observe(this){mealsList->
            mealsAdapter.setList(mealsList as ArrayList<PopularList.PopularMeal>)
            binding.categoryRv.apply {
                adapter = mealsAdapter
                layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            }
            binding.categoryTv.text = "Meals: ${mealsList.size}"

        }
    }
}