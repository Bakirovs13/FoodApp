package com.example.foodapp.domain.models

import java.io.Serializable

data class CategoryList(
    val categories: List<Category> = listOf()
) {
    data class Category(
        val idCategory: String = "",
        val strCategory: String = "",
        val strCategoryDescription: String = "",
        val strCategoryThumb: String = ""
    ) : Serializable
}