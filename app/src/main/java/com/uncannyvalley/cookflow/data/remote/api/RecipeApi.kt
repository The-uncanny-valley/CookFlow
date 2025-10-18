package com.uncannyvalley.cookflow.data.remote.api

import com.uncannyvalley.cookflow.BuildConfig
import com.uncannyvalley.cookflow.data.remote.dto.RecipeDto
import com.uncannyvalley.cookflow.data.remote.dto.RecipeResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RecipeApi {
    @GET("recipes/complexSearch")
    suspend fun getRecipes(
        @Query("query") query: String? = null,
        @Query("type") type: String? = null,
        @Query("addRecipeInformation") addRecipeInformation: Boolean = true,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): RecipeResponse

    @GET("recipes/{id}/information")
    suspend fun getRecipeById(
        @Path("id") id: Int,
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY
    ): RecipeDto
}