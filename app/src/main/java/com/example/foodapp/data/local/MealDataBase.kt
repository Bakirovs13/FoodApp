package com.example.foodapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.foodapp.domain.models.MealList

@Database(entities = [MealList.Meal::class], version = 3, exportSchema = false)
@TypeConverters(MealTypeConverter::class)
abstract class MealDataBase: RoomDatabase() {

   abstract fun mealDao() :MealDao

   companion object{
       @Volatile
       var INSTANCE:MealDataBase? =  null

       @Synchronized
       fun getInstance(context: Context):MealDataBase{
           if(INSTANCE==null){
               INSTANCE=Room.databaseBuilder(
                   context,
                   MealDataBase::class.java,
                   "mealDB"
               ).fallbackToDestructiveMigration()
                   .build()
           }
           return INSTANCE as MealDataBase
       }
   }

}