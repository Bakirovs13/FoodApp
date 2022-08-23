package com.example.foodapp.presentation.fragments.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.local.MealDataBase
import com.example.foodapp.data.remote.RetrofitInstance
import com.example.foodapp.domain.models.CategoryList
import com.example.foodapp.domain.models.PopularList
import com.example.foodapp.domain.models.MealList
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val mealDataBase: MealDataBase

):ViewModel() {

    private var favoriteMealsLiveData= mealDataBase.mealDao().getAllMeals()
    private  var randomMealLiveData:MutableLiveData<MealList.Meal> = MutableLiveData()
    private  var popularMealLiveData:MutableLiveData<List<PopularList.PopularMeal>> = MutableLiveData()
    private  var categoryLiveData:MutableLiveData<List<CategoryList.Category>> = MutableLiveData()
    private  var searchMealLiveData:MutableLiveData<List<MealList.Meal>> = MutableLiveData()


  private var saveStateRandomMeal :MealList.Meal? = null

    fun getCategoryList(){
        RetrofitInstance.api.getCategories().enqueue(object :Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body()!=null){
                    categoryLiveData.value = response.body()!!.categories
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
              Log.d("category",t.message.toString())
            }

        })
    }

    fun getPopularMeal(){
        RetrofitInstance.api.getPopularMeal("Dessert").enqueue(object :Callback<PopularList>{

            override fun onResponse(call: Call<PopularList>, response: Response<PopularList>) {
               if(response.body() != null){
                   val popularMeal = response.body()!!.meals
                   popularMealLiveData.value = popularMeal
               }else{
                   return
               }
            }
            override fun onFailure(call: Call<PopularList>, t: Throwable) {
                Log.d("Popular ",t.message.toString())

            }

        })
    }

    fun getRandomMeal (){
        saveStateRandomMeal?.let {randomMeal->
            randomMealLiveData.postValue(randomMeal)
            return

        }
        RetrofitInstance.api.getRandomMeal().enqueue(object : Callback<MealList> {
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                if(response.body()!= null){
                    val randomMeal = response.body()!!.meals[0]
                    randomMealLiveData.value = randomMeal
                    saveStateRandomMeal = randomMeal
                }else{
                    return
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
                Log.d("Home",t.message.toString())
            }

        })
    }


    fun searchMealByName(searchQuery:String){
        RetrofitInstance.api.searchByName(searchQuery).enqueue(object :Callback<MealList>{
            override fun onResponse(call: Call<MealList>, response: Response<MealList>) {
                val mealList = response.body()?.meals
                mealList?.let {
                    searchMealLiveData.value = it
                }
            }

            override fun onFailure(call: Call<MealList>, t: Throwable) {
               Log.d("search",t.message.toString())
            }

        })
    }

    fun observeSearchMealLiveData():LiveData<List<MealList.Meal>> = searchMealLiveData


    fun observeRandomMealLD():LiveData<MealList.Meal>{
      return  randomMealLiveData
    }

    fun observePopularLD(): MutableLiveData<List<PopularList.PopularMeal>> {
        return popularMealLiveData
    }
    fun observeCategoriesLD(): MutableLiveData<List<CategoryList.Category>> {
        return categoryLiveData
    }


    fun observeFavoritesLiveData():LiveData<List<MealList.Meal>>{
        return favoriteMealsLiveData
    }

    fun deleteMeal(meal: MealList.Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().deleteMeal(meal)
        }
    }

    fun insertMeal(meal: MealList.Meal){
        viewModelScope.launch {
            mealDataBase.mealDao().insertMeal(meal)
        }
    }

}