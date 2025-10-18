package com.uncannyvalley.cookflow.domain.usecase

import android.util.Log
import com.uncannyvalley.cookflow.domain.model.Recipe
import com.uncannyvalley.cookflow.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(query: String? = null, type: String? = null): Flow<Result<List<Recipe>>> = flow {
        Log.d("GetRecipesUseCase", "Invoked with query = $query")
        try {
            val recipesResult: Result<List<Recipe>> = repository.getRecipes(query, type)
            Log.d("GetRecipesUseCase", "Repository returned ${recipesResult.getOrNull()?.size ?: 0} recipes")
            emit(recipesResult)
        } catch (e: Exception) {
            Log.e("GetRecipesUseCase", "Exception: ${e.message}")
            emit(Result.failure(e))
        }
    }
}

class GetRecipeUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: Int): Result<Recipe> {
        return repository.getRecipeById(id)
    }
}