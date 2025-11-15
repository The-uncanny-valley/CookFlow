package com.uncannyvalley.cookflow.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.uncannyvalley.cookflow.presentation.screen.HomeScreen
import com.uncannyvalley.cookflow.presentation.screen.RecipeDetailScreen

@Composable
fun CookFlowNavHost(
    navController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navController,
        startDestination = HomeScreen
    ) {
        composable<HomeScreen> {
            HomeScreen(
                onRecipeClick = { recipe ->
                    navController.navigate(RecipeDetailScreen(recipe.id))
                }
            )
        }

        composable<RecipeDetailScreen> { backStackEntry ->
            val args = backStackEntry.toRoute<RecipeDetailScreen>()
            RecipeDetailScreen(
                recipeId = args.recipeId,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}