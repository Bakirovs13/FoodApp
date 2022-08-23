package com.example.foodapp.data.remote

import com.example.foodapp.domain.models.CategoryList
import com.example.foodapp.domain.models.PopularList
import com.example.foodapp.domain.models.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    @GET("lookup.php?")
    fun getMealDetail(
        @Query("i") id:String) :Call<MealList>

    @GET("filter.php?")
    fun getPopularMeal(
        @Query("c") category:String):Call<PopularList>

    @GET("categories.php")
    fun getCategories():Call<CategoryList>

   @GET("filter.php?")
   fun getMealsByCategory(
       @Query("c") categoryName:String):Call<PopularList>

   @GET("search.php?")
   fun searchByName(
       @Query("s")mealName:String):Call<MealList>

}