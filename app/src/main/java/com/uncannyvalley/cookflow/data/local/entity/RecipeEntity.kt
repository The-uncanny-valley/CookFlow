package com.uncannyvalley.cookflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val image: String,
    val summary: String,
    val instructions: String,
    val dishTypes: String
)