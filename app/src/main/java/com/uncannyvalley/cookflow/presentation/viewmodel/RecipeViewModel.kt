package com.uncannyvalley.cookflow.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.uncannyvalley.cookflow.domain.model.Recipe
import com.uncannyvalley.cookflow.domain.usecase.GetRecipeUseCase
import com.uncannyvalley.cookflow.domain.usecase.GetRecipesUseCase
import androidx.compose.runtime.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase,
    private val getRecipeUseCase: GetRecipeUseCase
) : ViewModel() {

    private val _recipes = mutableStateOf<RecipeState>(RecipeState.Loading)
    val recipes: State<RecipeState> = _recipes

    private val _selectedRecipe = mutableStateOf<RecipeDetailState>(RecipeDetailState.Loading)
    val selectedRecipe: State<RecipeDetailState> = _selectedRecipe
}

sealed interface RecipeState {
    data object Loading : RecipeState
    data class Success(val recipes: List<Recipe>) : RecipeState
    data class Error(val message: String) : RecipeState
}

sealed interface RecipeDetailState {
    data object Loading : RecipeDetailState
    data class Success(val recipe: Recipe) : RecipeDetailState
    data class Error(val message: String) : RecipeDetailState
}