package com.uncannyvalley.cookflow.domain.usecase

import com.uncannyvalley.cookflow.domain.model.Recipe
import com.uncannyvalley.cookflow.domain.repository.RecipeRepository

class GetRecipesUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(query: String? = null): Result<List<Recipe>> {
        return repository.getRecipes(query)
    }
}

class GetRecipeUseCase(private val repository: RecipeRepository) {
    suspend operator fun invoke(id: Int): Result<Recipe> {
        return repository.getRecipeById(id)
    }
}