package com.uncannyvalley.cookflow.data.remote.dto

import com.uncannyvalley.cookflow.data.local.entity.IngredientEntity
import com.uncannyvalley.cookflow.data.local.entity.RecipeEntity
import com.uncannyvalley.cookflow.domain.model.Ingredient
import com.uncannyvalley.cookflow.domain.model.Recipe
import kotlin.String

data class RecipeDto(
    val id: Int,
    val title: String?,
    val image: String?,
    val summary: String?,
    val instructions: String?,
    val dishTypes: List<String>?,
    val extendedIngredients: List<IngredientDto>?
)

data class IngredientDto(
    val name: String,
    val amount: Double,
    val unit: String
)

fun RecipeDto.toRecipe(): Recipe {
    return Recipe(
        id = id,
        title = title ?: "Untitled Recipe",
        image = image ?: "",
        summary = summary?.replace(Regex("<.*?>"), "") ?: "",
        instructions = instructions?.replace(Regex("<.*?>"), "") ?: "",
        ingredients = extendedIngredients?.map { it.toIngredient() } ?: emptyList(),
        dishType = dishTypes?.firstOrNull() ?: ""
    )
}

fun IngredientDto.toIngredient(): Ingredient {
    return Ingredient(
        name = name,
        amount = amount,
        unit = unit
    )
}

// Map Domain Recipe to Entity (for database)
fun Recipe.toEntity(): RecipeEntity {
    return RecipeEntity(
        id = id,
        title = title,
        image = image,
        summary = summary,
        instructions = instructions,
        dishType = dishType
    )
}

// Map Entity to Domain Recipe (from database)
fun RecipeEntity.toRecipe(): Recipe {
    return Recipe(
        id = id,
        title = title,
        image = image,
        summary = summary,
        instructions = instructions,
        ingredients = emptyList(), // Ingredients loaded separately
        dishType = dishType
    )
}

// Map Domain Ingredient to Entity (for database)
fun Ingredient.toEntity(recipeId: Int): IngredientEntity {
    return IngredientEntity(
        recipeId = recipeId,
        name = name,
        amount = amount,
        unit = unit
    )
}

// Map Entity to Domain Ingredient (from database)
fun IngredientEntity.toIngredient(): Ingredient {
    return Ingredient(
        name = name,
        amount = amount,
        unit = unit
    )
}
