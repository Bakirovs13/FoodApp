package com.example.foodapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.domain.models.MealList

@Dao
interface MealDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal:MealList.Meal)

   @Delete
   suspend fun deleteMeal(meal: MealList.Meal)

   @Query("SELECT * from mealEntity ")
    fun getAllMeals():LiveData<List<MealList.Meal>>

}