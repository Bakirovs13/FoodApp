package com.example.foodapp.presentation.activities.categoryItem

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodapp.data.remote.RetrofitInstance
import com.example.foodapp.domain.models.PopularList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel :ViewModel() {

    val mealsLiveData = MutableLiveData<List<PopularList.PopularMeal>>()

    fun getMealsByCategory(categoryName:String){
        RetrofitInstance.api.getMealsByCategory(categoryName).enqueue(object :Callback<PopularList>{
            override fun onResponse(call: Call<PopularList>, response: Response<PopularList>) {
                if (response.body()!=null){
                    mealsLiveData.value = response.body()!!.meals
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<PopularList>, t: Throwable) {
               Log.d("category",t.message.toString())
            }

        })
    }

    fun observeMealsLiveData():LiveData<List<PopularList.PopularMeal>>{
        return mealsLiveData
    }
}