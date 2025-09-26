package com.uncannyvalley.cookflow

import com.uncannyvalley.cookflow.domain.model.Ingredient
import com.uncannyvalley.cookflow.domain.model.Recipe

val frenchToast = Recipe(
    id = 1,
    title = "How to make french toast",
    image = "https://example.com/french_toast.jpg",
    summary = "By Roberta Anny",
    instructions = """
        1. In a shallow bowl, whisk together eggs, milk, vanilla extract, and cinnamon.
        2. Dip each slice of bread into the mixture, coating both sides.
        3. Heat butter in a skillet over medium heat.
        4. Cook bread slices until golden brown on both sides.
        5. Serve warm with syrup, powdered sugar, or fresh fruit.
    """.trimIndent(),
    ingredients = listOf(
        Ingredient("Bread", 4.0, "pcs"),
        Ingredient("Eggs", 2.0, "pcs"),
        Ingredient("Milk", 0.5, "cup"),
        Ingredient("Butter", 2.0, "tbsp"),
        Ingredient("Vanilla", 2.0, "tbsp")
    ),
    dishType = "Breakfast"
)

val sushi = Recipe(
    id = 2,
    title = "How to make sushi at home",
    image = "sushi",
    summary = "By Niki Samantha",
    instructions = "",
    ingredients = listOf(
        Ingredient("Sushi rice", 2.0, "cups"),
        Ingredient("Rice vinegar", 3.0, "tbsp"),
        Ingredient("Sugar", 1.0, "tbsp"),
        Ingredient("Salt", 1.0, "tsp"),
        Ingredient("Nori sheets", 5.0, "pcs"),
        Ingredient("Cucumber", 0.5, "pcs"),
        Ingredient("Avocado", 1.0, "pcs"),
        Ingredient("Carrot", 1.0, "pcs"),
        Ingredient("Raw salmon (sushi-grade)", 200.0, "g"),
        Ingredient("Soy sauce", 3.0, "tbsp"),
        Ingredient("Pickled ginger", 2.0, "tbsp"),
        Ingredient("Wasabi", 1.0, "tsp")
    ),
    dishType = "Appetizer"
)
