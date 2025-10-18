package com.uncannyvalley.cookflow.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val instructions: String,
    val ingredients: List<Ingredient>,
    val dishTypes: List<String>
)

data class Ingredient(
    val name: String,
    val amount: Double,
    val unit: String
)