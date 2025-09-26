package com.uncannyvalley.cookflow.data.repository

import com.uncannyvalley.cookflow.data.local.dao.RecipeDao
import com.uncannyvalley.cookflow.data.remote.api.RecipeApiImpl
import com.uncannyvalley.cookflow.data.remote.dto.toEntity
import com.uncannyvalley.cookflow.data.remote.dto.toIngredient
import com.uncannyvalley.cookflow.data.remote.dto.toRecipe
import com.uncannyvalley.cookflow.domain.model.Recipe
import com.uncannyvalley.cookflow.domain.repository.RecipeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RecipeRepositoryImpl(
    private val api: RecipeApiImpl,
    private val dao: RecipeDao,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : RecipeRepository {

    override suspend fun getRecipes(query: String?): Result<List<Recipe>> {
        return withContext(dispatcher) {
            try {
                val response = api.getRecipes(query = query)

                val recipes = response.results.map { it.toRecipe() }

                dao.insertRecipes(recipes.map { it.toEntity() })
                recipes.forEach { recipe ->
                    dao.insertIngredients(recipe.ingredients.map { it.toEntity(recipe.id) })
                }

                Result.success(recipes)
            } catch (e: Exception) {
                try {
                    val entities = if (query.isNullOrBlank()) {
                        dao.getAllRecipes()
                    } else {
                        dao.getRecipesByQuery(query)
                    }
                    val recipes = entities.map { entity ->
                        val ingredients = dao.getIngredientsForRecipe(entity.id).map { it.toIngredient() }
                        entity.toRecipe().copy(ingredients = ingredients)
                    }
                    Result.success(recipes)
                } catch (dbException: Exception) {
                    Result.failure(dbException)
                }
            }
        }
    }

    override suspend fun getRecipeById(id: Int): Result<Recipe> {
        return withContext(dispatcher) {
            try {
                // Try API first
                val response = api.getRecipeById(id)
                val recipe = response.toRecipe()

                // Save to database
                dao.insertRecipe(recipe.toEntity())
                dao.insertIngredients(recipe.ingredients.map { it.toEntity(recipe.id) })

                Result.success(recipe)
            } catch (e: Exception) {
                // Fallback to local database
                try {
                    val entity = dao.getRecipeById(id)
                    if (entity != null) {
                        val ingredients = dao.getIngredientsForRecipe(id).map { it.toIngredient() }
                        Result.success(entity.toRecipe().copy(ingredients = ingredients))
                    } else {
                        Result.failure(Exception("Recipe not found"))
                    }
                } catch (dbException: Exception) {
                    Result.failure(dbException)
                }
            }
        }
    }

    override suspend fun searchRecipes(query: String): Result<List<Recipe>> {
        // searchRecipes can be the same as getRecipes with query
        return getRecipes(query)
    }
}