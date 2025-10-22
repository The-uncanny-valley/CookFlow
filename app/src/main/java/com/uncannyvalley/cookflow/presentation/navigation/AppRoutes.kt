package com.uncannyvalley.cookflow.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeScreen

@Serializable
data class RecipeDetailScreen(
    val recipeId: Int
)