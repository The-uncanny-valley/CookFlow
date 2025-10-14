package com.uncannyvalley.cookflow.presentation.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.uncannyvalley.cookflow.domain.model.Recipe
import com.uncannyvalley.cookflow.domain.usecase.GetRecipeUseCase
import com.uncannyvalley.cookflow.domain.usecase.GetRecipesUseCase
import androidx.compose.runtime.State
import androidx.lifecycle.viewModelScope
import com.uncannyvalley.cookflow.domain.model.Category
import com.uncannyvalley.cookflow.domain.model.RecipeType
import com.uncannyvalley.cookflow.frenchToast
import com.uncannyvalley.cookflow.sushi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

    // sample of categories
    private val _categories = mutableStateOf(
        listOf(
            Category(1, RecipeType.SALAD),
            Category(2, RecipeType.BREAKFAST),
            Category(3, RecipeType.SOUP),
            Category(4, RecipeType.FINGERFOOD),
            Category(5, RecipeType.APPETIZER),
            Category(6, RecipeType.MAIN_COURSE)
        )
    )
    val categories: State<List<Category>> = _categories

    // sample of popular recipes
    private val _popularRecipes = mutableStateOf<List<Recipe>>(emptyList())
    val popularRecipes: State<List<Recipe>> = _popularRecipes

    init {
        // TODO: Replace this with actual useCase logic later
        viewModelScope.launch {
            loadPopularRecipes()
        }
    }

    private fun loadPopularRecipes() {
        // sample placeholder data
        val sampleRecipes = listOf(
            frenchToast,
            sushi
        )
        _popularRecipes.value = sampleRecipes
    }
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