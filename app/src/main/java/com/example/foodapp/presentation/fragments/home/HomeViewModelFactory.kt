package com.example.foodapp.presentation.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.data.local.MealDataBase

class HomeViewModelFactory(
   private val dataBase: MealDataBase
):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(dataBase) as T
    }
}