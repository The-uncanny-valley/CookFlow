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
    val errorMessage: String? = null
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
            errorMessage = null
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
    fun loadRecipesByCategory(type: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                getRecipesUseCase(type = type).collect { result ->
                    result
                        .onSuccess { recipes ->
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