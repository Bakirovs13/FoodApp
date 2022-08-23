package com.example.foodapp.domain.models

data class PopularList(
    val meals: List<PopularMeal> = listOf()
) {
    data class PopularMeal(
        val idMeal: String = "",
        val strMeal: String = "",
        val strMealThumb: String = ""
    )
}