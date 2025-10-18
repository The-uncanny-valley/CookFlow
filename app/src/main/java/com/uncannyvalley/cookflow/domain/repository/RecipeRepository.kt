package com.uncannyvalley.cookflow.domain.repository

import com.uncannyvalley.cookflow.domain.model.Recipe

interface RecipeRepository {
    suspend fun getRecipes(query: String? = null, type: String? = null): Result<List<Recipe>>
    suspend fun getRecipeById(id: Int): Result<Recipe>
    suspend fun searchRecipes(query: String): Result<List<Recipe>>
}