package com.uncannyvalley.cookflow.domain.model

sealed class RecipeFilter {
    data object None : RecipeFilter()
    data class ByQuery(val query: String) : RecipeFilter()
    data class ByType(val type: RecipeType) : RecipeFilter()
}

enum class RecipeType {
    MAIN_COURSE, SIDE_DISH, DESSERT, APPETIZER, SALAD,
    BREAD, BREAKFAST, SOUP, BEVERAGE, SAUCE,
    MARINADE, FINGERFOOD, SNACK, DRINK;

    override fun toString(): String = name.lowercase().replace("_", " ")
}