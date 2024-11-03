import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RecipeManager {
    private List<Recipe> recipes;
    private String filePath;

    public RecipeManager(String filePath) {
        this.filePath = filePath;
        this.recipes = new ArrayList<>();
        loadRecipes();
    }

    public void loadRecipes() {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 8) {
                    System.out.println("Not enough data fields: " + line);
                    continue;
                }

                String name = parts[0].trim();
                String description = parts[1].trim();
                String cuisine = parts[2].trim();
                String mealType = parts[3].trim();
                boolean isVegetarian = Boolean.parseBoolean(parts[4].trim());
                boolean isVegan = Boolean.parseBoolean(parts[5].trim());

                int prepTime = 0;
                try {
                    prepTime = Integer.parseInt(parts[6].trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing preparation time for recipe " + name + ": " + parts[6].trim() + " - " + e.getMessage());
                    continue;
                }

                try {
                    Recipe recipe = new Recipe(name, description, cuisine, mealType, isVegetarian, isVegan, prepTime);
                    recipe.setFavorite(Boolean.parseBoolean(parts[7].trim()));

                    for (int i = 8; i < parts.length; i += 2) {
                        if (i + 1 < parts.length) {
                            String ingredientName = parts[i].trim();
                            String ingredientQuantity = parts[i + 1].trim();
                            recipe.addIngredient(new Ingredient(ingredientName, ingredientQuantity));
                        }
                    }

                    recipes.add(recipe);
                    System.out.println("Loaded recipe: " + recipe.getName());
                } catch (InvalidPrepTimeException e) {
                    System.out.println("Error creating recipe '" + name + "': " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading the file: " + e.getMessage());
        }
    }

    public void addRecipe(Recipe recipe) {
        recipes.add(recipe);
        saveRecipe(recipe);
    }

    public void saveRecipe(Recipe recipe) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(recipe.getName() + "," + recipe.getDescription() + "," + recipe.getCuisine() + "," +
                    recipe.getMealType() + "," + recipe.isVegetarian() + "," + recipe.isVegan() + "," +
                    recipe.getPrepTime() + "," + recipe.isFavorite());
            for (Ingredient ingredient : recipe.getIngredients()) {
                bw.write("," + ingredient.getName() + "," + ingredient.getQuantity());
            }
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to the file: " + e.getMessage());
        }
    }

    public List<Recipe> getAllRecipes() {
        return recipes;
    }

    public List<Recipe> searchRecipes(String query) {
        List<Recipe> results = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.getName().toLowerCase().contains(query.toLowerCase()) ||
                recipe.getDescription().toLowerCase().contains(query.toLowerCase()) ||
                recipe.getCuisine().toLowerCase().contains(query.toLowerCase()) ||
                recipe.getMealType().toLowerCase().contains(query.toLowerCase())) {
                results.add(recipe);
            }
        }
        return results;
    }

    public void toggleFavorite(Recipe recipe) {
        recipe.setFavorite(!recipe.isFavorite());
    }

    public List<Recipe> getFavoriteRecipes() {
        List<Recipe> favoriteRecipes = new ArrayList<>();
        for (Recipe recipe : recipes) {
            if (recipe.isFavorite()) {
                favoriteRecipes.add(recipe);
            }
        }
        return favoriteRecipes;
    }
}
