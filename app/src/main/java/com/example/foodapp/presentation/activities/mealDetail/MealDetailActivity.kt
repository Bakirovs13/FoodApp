package com.example.foodapp.presentation.activities.mealDetail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.foodapp.R
import com.example.foodapp.data.local.MealDataBase
import com.example.foodapp.databinding.ActivityMealBinding
import com.example.foodapp.domain.models.MealList
import com.example.foodapp.utils.Constant.MEAL_ID
import com.example.foodapp.utils.Constant.MEAL_NAME
import com.example.foodapp.utils.Constant.MEAL_THUMB

class MealDetailActivity : AppCompatActivity() {

    private lateinit var  mealDataBase:MealDataBase //by lazy { MealDataBase.getInstance(this)  }
    private lateinit var viewModelFactory:DetailViewModelFactory// by lazy { DetailViewModelFactory(mealDataBase) }
    private lateinit var viewModel:DetailViewModel //by lazy { ViewModelProvider(this,viewModelFactory)[DetailViewModel::class.java]}
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var youtubeLink:String

    private lateinit var binding: ActivityMealBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealDataBase = MealDataBase.getInstance(this)
        viewModelFactory = DetailViewModelFactory(mealDataBase)
        viewModel = ViewModelProvider(this,viewModelFactory)[DetailViewModel::class.java]

        getDetailInfo()
        setInfo()
        observeDetailLD()
        onYoutubeCLick()
        onFavoriteClick()

    }

    private fun onFavoriteClick() {
        binding.fab.setOnClickListener {
            mealToSave?.let {
                    it1 -> viewModel.insertMeal(it1)
                Toast.makeText(this, "Meal :${it1.strMeal} is saved", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun onYoutubeCLick() {
        binding.youtubeImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)

        }
    }

    private var mealToSave:MealList.Meal? = null
    @SuppressLint("SetTextI18n")
    private fun observeDetailLD() {
        loadingCase()
        viewModel.getMealDetail(mealId)
        viewModel.observeDetailLD().observe(this
        ) { mealDetail ->
            onResponseCase()
            mealToSave = mealDetail
            binding.descriptionTv.text = mealDetail?.strInstructions
            binding.categoryTv.text = "Category: ${mealDetail?.strCategory}"
            binding.areaTv.text = "Area: ${mealDetail?.strArea}"
            youtubeLink = mealDetail?.strYoutube.toString()
        }
    }

    private fun setInfo() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.mealDetailImg)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(resources.getColor(R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(resources.getColor(R.color.white))

    }

    private fun getDetailInfo() {
        val intent = intent
        mealId = intent.getStringExtra(MEAL_ID)!!
        mealName = intent.getStringExtra(MEAL_NAME)!!
        mealThumb = intent.getStringExtra(MEAL_THUMB)!!


    }


    private fun loadingCase() {
        binding.myProgressbar.visibility = View.VISIBLE
        binding.fab.visibility = View.INVISIBLE
        binding.instTv.visibility = View.INVISIBLE
        binding.categoryTv.visibility = View.INVISIBLE
        binding.areaTv.visibility = View.INVISIBLE
        binding.youtubeImg.visibility = View.INVISIBLE

    }

    private fun onResponseCase() {
        binding.myProgressbar.visibility = View.INVISIBLE
        binding.fab.visibility = View.VISIBLE
        binding.instTv.visibility = View.VISIBLE
        binding.categoryTv.visibility = View.VISIBLE
        binding.areaTv.visibility = View.VISIBLE
        binding.youtubeImg.visibility = View.VISIBLE
    }


}