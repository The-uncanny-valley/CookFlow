package com.uncannyvalley.cookflow.data.remote.api

import com.uncannyvalley.cookflow.data.remote.dto.RecipeDto
import com.uncannyvalley.cookflow.data.remote.dto.RecipeResponse
import javax.inject.Inject

class RecipeApiImpl @Inject constructor(
    private val api: RecipeApi,
    private val apiKey: String
) {

    suspend fun getRecipes(query: String? = null, type: String? = null): RecipeResponse {
        return api.getRecipes(query, type, apiKey = apiKey)
    }

    suspend fun getRecipeById(id: Int): RecipeDto {
        return api.getRecipeById(id, apiKey)
    }
}