package com.example.foodapp.presentation.activities.mealDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.data.local.MealDataBase

class DetailViewModelFactory(
  private  val  dataBase: MealDataBase
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailViewModel(dataBase) as T
    }
}