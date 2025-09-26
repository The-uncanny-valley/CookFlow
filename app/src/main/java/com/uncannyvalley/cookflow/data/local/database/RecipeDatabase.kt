package com.uncannyvalley.cookflow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.uncannyvalley.cookflow.data.local.dao.RecipeDao
import com.uncannyvalley.cookflow.data.local.entity.IngredientEntity
import com.uncannyvalley.cookflow.data.local.entity.RecipeEntity

@Database(
    entities = [RecipeEntity::class, IngredientEntity::class],
    version = 1,
    exportSchema = false
)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}