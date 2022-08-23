package com.example.foodapp.presentation.activities.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.foodapp.R
import com.example.foodapp.data.local.MealDataBase
import com.example.foodapp.databinding.ActivityMainBinding
import com.example.foodapp.presentation.fragments.home.HomeViewModel
import com.example.foodapp.presentation.fragments.home.HomeViewModelFactory

class MainActivity : AppCompatActivity() {

     val viewModel  by lazy {
        val dataBase = MealDataBase.getInstance(this)
        val homeViewModelFactory = HomeViewModelFactory(dataBase)
        ViewModelProvider(this,homeViewModelFactory)[HomeViewModel::class.java]
    }
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navController = Navigation.findNavController(this,R.id.my_container)
        NavigationUI.setupWithNavController(binding.myBottomNav,navController)
    }
}