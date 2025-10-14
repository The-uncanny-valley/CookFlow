package com.uncannyvalley.cookflow.domain.usecase

import com.uncannyvalley.cookflow.domain.model.Recipe
import com.uncannyvalley.cookflow.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(query: String? = null): Flow<Result<List<Recipe>>> = flow {
        try {
            val recipesResult: Result<List<Recipe>> = repository.getRecipes(query)
            emit(recipesResult)
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }
}

class GetRecipeUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(id: Int): Result<Recipe> {
        return repository.getRecipeById(id)
    }
}