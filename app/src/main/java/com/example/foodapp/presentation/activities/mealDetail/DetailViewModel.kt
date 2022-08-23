package com.example.foodapp.presentation.activities.mealDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.local.MealDataBase
import com.example.foodapp.data.remote.RetrofitInstance
import com.example.foodapp.domain.models.MealList
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(
    private val mealDataBase: MealDataBase
):ViewModel() {

    private var detailLiveData = MutableLiveData<MealList.Meal>()

    fun getMealDetail(id:String){
        RetrofitInstance.api.getMealDetail(id).enqueue(object :Callback<MealList>{

            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
              if(response.body()!= null){
                  detailLiveData.value = response.body()!!.meals[0]
              }else{
                  return
              }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.e("Detail",t.message.toString())
            }
        })
    }

    fun observeDetailLD():LiveData<MealList.Meal>{
        return  detailLiveData
    }

    fun insertMeal(meal: MealList.Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().insertMeal(meal)
        }
    }

//    fun deleteMeal(meal: MealList.Meal){
//        viewModelScope.launch {
//            mealDataBase.mealDao().deleteMeal(meal)
//        }
//    }
}