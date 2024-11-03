import java.util.ArrayList;
import java.util.List;

public class RecipeData {
    
    
    public static void initializeRecipes(RecipeManager manager) {
       
        List<RecipeInfo> recipeInfos = List.of(
            new RecipeInfo("Pasta", "Boil water, add pasta, cook for 10 minutes.", "Italian", "Lunch", true, false, 20, 
                List.of(new Ingredient("Pasta", "200g"), new Ingredient("Salt", "1 tsp"))),
            new RecipeInfo("Steamed Rice", "Boil water, add rice, cook for 10-15 minutes.", "Asian", "Lunch", true, false, 20, 
                List.of(new Ingredient("Rice", "200g"), new Ingredient("Salt", "1 tsp"))),
            new RecipeInfo("Chickpea and Vegetable Curry", "Heat oil and sauté onions, garlic, and ginger. Add curry powder and stir. Add chickpeas, coconut milk, and carrots. Simmer for 15 minutes. Add spinach and cook for 5 more minutes. Season and serve.", "Indian", "Dinner", true, true, 30, 
                List.of(new Ingredient("Chickpeas", "100g"), new Ingredient("Salt", "1 tsp"), new Ingredient("Onion", "1 chopped"),
                          new Ingredient("Garlic", "2 cloves minced"), new Ingredient("Ginger", "1 tbsp grated"), 
                          new Ingredient("Curry Powder", "1 tsp"), new Ingredient("Spinach", "100g"), 
                          new Ingredient("Carrot", "1 chopped"), new Ingredient("Olive oil", "1 tsp"))),
            new RecipeInfo("Avocado Toast", "Toast the bread. Mash the avocado with lime juice, salt, and pepper. Spread on toast and top with optional toppings like chili flakes, tomatoes, or poached eggs.", "American", "Breakfast", true, false, 10, 
                List.of(new Ingredient("Bread", "2 slices (can be gluten-free)"), new Ingredient("Avocado", "1 ripe"), 
                          new Ingredient("Lime Juice", "1 tsp"), new Ingredient("Salt", "to taste"), 
                          new Ingredient("Black Pepper", "to taste"), new Ingredient("Chili Flakes", "optional"), 
                          new Ingredient("Tomato", "optional (sliced)"), new Ingredient("Poached Egg", "optional"))),
            new RecipeInfo("Chicken Salad", "Grill chicken breast and set aside. Toss lettuce, croutons, and Parmesan with Caesar dressing. Top with sliced chicken and extra Parmesan.", "American", "Lunch", false, false, 25, 
                List.of(new Ingredient("Chicken Breast", "200g grilled"), new Ingredient("Romaine Lettuce", "1 head chopped"), 
                          new Ingredient("Croutons", "1/2 cup"), new Ingredient("Parmesan Cheese", "50g grated"), 
                          new Ingredient("Caesar Dressing", "3 tbsp"))),
            new RecipeInfo("Banana Smoothie", "Blend banana, almond milk, peanut butter, and honey until smooth. Serve chilled.", "American", "Breakfast", true, true, 5, 
                List.of(new Ingredient("Banana", "1 large"), new Ingredient("Almond Milk", "1 cup"), 
                          new Ingredient("Peanut Butter", "1 tbsp"), new Ingredient("Honey", "1 tsp"))),
            new RecipeInfo("Pancakes", "Mix flour, sugar, baking powder, milk, egg, and melted butter. Cook on a griddle until golden brown. Serve with syrup or fruit.", "American", "Breakfast", true, true, 10, 
                List.of(new Ingredient("Flour", "1 cup"), new Ingredient("Sugar", "2 tbsp"), 
                          new Ingredient("Baking Powder", "2 tsp"), new Ingredient("Milk", "1 cup"), 
                          new Ingredient("Egg", "1"), new Ingredient("Melted Butter", "2 tbsp"))),
            new RecipeInfo("Chole Bhature", "Cook chickpeas with onions, tomatoes, and spices to make chole. Prepare and fry bhature dough. Serve chole with bhature.", "Indian", "Lunch", true, true, 60, 
                List.of(new Ingredient("Chickpeas", "1 cup"), new Ingredient("Onions", "2, finely chopped"), 
                          new Ingredient("Tomatoes", "2, chopped"), new Ingredient("Ginger-Garlic Paste", "1 tbsp"), 
                          new Ingredient("Chole Masala", "2 tbsp"), new Ingredient("Oil", "2 tbsp"), 
                          new Ingredient("Flour (for Bhature)", "2 cups"), new Ingredient("Yogurt", "1/2 cup"), 
                          new Ingredient("Baking Powder", "1/2 tsp"), new Ingredient("Salt", "to taste"))),
            new RecipeInfo("Chocolate Chip Cookies", "Cream butter and sugar, then mix in eggs and vanilla. Stir in flour, baking soda, and chocolate chips. Bake until golden brown.", "American", "Dessert", true, true, 20, 
                List.of(new Ingredient("Butter", "1 cup"), new Ingredient("Sugar", "1 cup"), 
                          new Ingredient("Eggs", "2"), new Ingredient("Vanilla Extract", "1 tsp"), 
                          new Ingredient("Flour", "2 1/4 cups"), new Ingredient("Baking Soda", "1/2 tsp"), 
                          new Ingredient("Chocolate Chips", "1 cup"))),
            new RecipeInfo("Masala Dosa", "Prepare dosa batter and cook on a griddle. Fill with a spiced potato filling made with onions, potatoes, mustard seeds, and curry leaves. Serve with chutneys.", "Indian", "Breakfast", true, true, 30, 
                List.of(new Ingredient("Dosa Batter", "2 cups"), new Ingredient("Potatoes", "2, boiled and mashed"), 
                          new Ingredient("Onions", "1, finely chopped"), new Ingredient("Mustard Seeds", "1 tsp"), 
                          new Ingredient("Curry Leaves", "8-10 leaves"), new Ingredient("Turmeric Powder", "1/2 tsp"), 
                          new Ingredient("Salt", "to taste")))
            
        );

     
        for (RecipeInfo info : recipeInfos) {
            try {
                Recipe recipe = new Recipe(info.name, info.description, info.cuisine, info.mealType, info.isVegetarian, info.isVegan, info.prepTime);
                for (Ingredient ingredient : info.ingredients) {
                    recipe.addIngredient(ingredient);
                }
                manager.addRecipe(recipe);
            } catch (InvalidPrepTimeException e) {
                System.out.println("Error initializing recipe '" + info.name + "': " + e.getMessage());
            }
        }
    }

    
    private static class RecipeInfo {
        String name;
        String description;
        String cuisine;
        String mealType;
        boolean isVegetarian;
        boolean isVegan;
        int prepTime;
        List<Ingredient> ingredients;

        RecipeInfo(String name, String description, String cuisine, String mealType, boolean isVegetarian, boolean isVegan, int prepTime, List<Ingredient> ingredients) {
            this.name = name;
            this.description = description;
            this.cuisine = cuisine;
            this.mealType = mealType;
            this.isVegetarian = isVegetarian;
            this.isVegan = isVegan;
            this.prepTime = prepTime;
            this.ingredients = ingredients;
        }
    }
}
