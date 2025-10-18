package com.uncannyvalley.cookflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.uncannyvalley.cookflow.data.local.entity.IngredientEntity
import com.uncannyvalley.cookflow.data.local.entity.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    suspend fun getAllRecipes(): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity?

    @Query("SELECT * FROM recipes WHERE title LIKE '%' || :query || '%'")
    suspend fun getRecipesByQuery(query: String): List<RecipeEntity>

    @Query("SELECT * FROM recipes WHERE dishTypes LIKE '%' || :type || '%'")
    suspend fun getRecipesByType(type: String): List<RecipeEntity>

    @Query("SELECT DISTINCT dishTypes FROM recipes WHERE dishTypes IS NOT NULL AND dishTypes != ''")
    suspend fun getAllDishTypes(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipes: List<RecipeEntity>)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId")
    suspend fun getIngredientsForRecipe(recipeId: Int): List<IngredientEntity>
}