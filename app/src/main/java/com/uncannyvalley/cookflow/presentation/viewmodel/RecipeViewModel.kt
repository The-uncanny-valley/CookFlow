package com.uncannyvalley.cookflow.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uncannyvalley.cookflow.domain.model.Category
import com.uncannyvalley.cookflow.domain.model.Recipe
import com.uncannyvalley.cookflow.domain.model.RecipeType
import com.uncannyvalley.cookflow.domain.usecase.GetRecipeUseCase
import com.uncannyvalley.cookflow.domain.usecase.GetRecipesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = true,
    val categories: List<Category> = emptyList(),
    val popularRecipes: List<Recipe> = emptyList(),
    val allRecipes: List<Recipe> = emptyList(),
    val errorMessage: String? = null,
    val currentRecipe: Recipe? = null
)

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
    private val defaultCategories = listOf(
        Category(1, RecipeType.SALAD),
        Category(2, RecipeType.BREAKFAST),
        Category(3, RecipeType.SOUP),
        Category(4, RecipeType.FINGERFOOD),
        Category(5, RecipeType.APPETIZER),
        Category(6, RecipeType.MAIN_COURSE)
    )

    private val _uiState = MutableStateFlow(
        HomeUiState(
            isLoading = true,
            categories = defaultCategories,
            popularRecipes = emptyList(),
            errorMessage = null,
            allRecipes = emptyList(),
            currentRecipe = null
        )
    )
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        fetchRecipes()
    }

    private fun fetchRecipes() {
        viewModelScope.launch {

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                getRecipesUseCase().collect { result ->
                    result
                        .onSuccess { recipes ->
                            // update both domain and ui state
                            _recipes.value = RecipeState.Success(recipes)
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    popularRecipes = recipes.take(12),
                                    allRecipes = recipes,
                                    errorMessage = null
                                )
                            }
                        }
                        .onFailure { exception ->
                            _recipes.value = RecipeState.Error(exception.message ?: "Error")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = exception.message ?: "Error"
                                )
                            }
                        }
                }
            } catch (e: Exception) {
                _recipes.value = RecipeState.Error("Unexpected error")
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Unexpected error")
                }
            }
        }
    }

    fun loadRecipesByCategory(type: String) {
        viewModelScope.launch {
            Log.d("RecipeViewModel", "Fetching recipes for category: $type")

            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                getRecipesUseCase(type = type).collect { result ->
                    result
                        .onSuccess { recipes ->
                            Log.d(
                                "RecipeViewModel",
                                "Success! ${recipes.size} recipes loaded for $type"
                            )
                            // update both domain and ui state
                            _recipes.value = RecipeState.Success(recipes)
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    popularRecipes = recipes,
                                    allRecipes = recipes,
                                    errorMessage = null
                                )
                            }
                        }
                        .onFailure { exception ->
                            Log.e("RecipeViewModel", "Error fetching $type recipes")
                            _recipes.value = RecipeState.Error(exception.message ?: "Error")
                            _uiState.update {
                                it.copy(
                                    isLoading = false,
                                    errorMessage = exception.message ?: "Error"
                                )
                            }
                        }
                }
            } catch (e: Exception) {
                Log.e("RecipeViewModel", "Unexpected exception: ${e.message}")
                _recipes.value = RecipeState.Error("Unexpected error")
                _uiState.update {
                    it.copy(isLoading = false, errorMessage = e.message ?: "Unexpected error")
                }
            }
        }
    }

    fun loadRecipeById(recipeId: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            _selectedRecipe.value = RecipeDetailState.Loading

            try {
                val result = getRecipeUseCase(recipeId)
                result
                    .onSuccess { recipe ->
                        _selectedRecipe.value = RecipeDetailState.Success(recipe)
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                currentRecipe = recipe,
                                errorMessage = null
                            )
                        }
                    }
                    .onFailure { exception ->
                        _selectedRecipe.value = RecipeDetailState.Error(
                            exception.message ?: "Failed to load recipe"
                        )
                        _uiState.update { it.copy(
                            isLoading = false,
                            currentRecipe = null,
                            errorMessage = exception.message ?: "Failed to load recipe"
                        ) }
                    }
            } catch (e: Exception) {
                _selectedRecipe.value = RecipeDetailState.Error(
                    "Unexpected error: ${e.message ?: "Unknown error"}"
                )

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        currentRecipe = null,
                        errorMessage = "Unexpected error: ${e.message ?: "Unknown error"}"
                    )
                }
            }
        }
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