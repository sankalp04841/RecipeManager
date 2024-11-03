import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter recipe name:");
        String name = scanner.nextLine();

        System.out.println("Enter recipe description:");
        String description = scanner.nextLine();

        System.out.println("Enter cuisine type:");
        String cuisine = scanner.nextLine();

        System.out.println("Enter meal type (Breakfast/Lunch/Dinner/Dessert):");
        String mealType = scanner.nextLine();

        System.out.println("Is the recipe vegetarian? (true/false):");
        boolean isVegetarian = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Is the recipe vegan? (true/false):");
        boolean isVegan = Boolean.parseBoolean(scanner.nextLine());

        System.out.println("Enter preparation time (in minutes):");
        int prepTime = Integer.parseInt(scanner.nextLine());

        RecipeManager manager = new RecipeManager("path/to/Recipes.txt");

        try {
            Recipe newRecipe = new Recipe(name, description, cuisine, mealType, isVegetarian, isVegan, prepTime);
            manager.addRecipe(newRecipe);
            System.out.println("Recipe added successfully!");
        } catch (InvalidPrepTimeException e) {
            System.out.println("Error creating recipe: " + e.getMessage());
        }

        System.out.println("\nAll Recipes:");
        for (Recipe recipe : manager.getAllRecipes()) {
            System.out.println(recipe.getName() + " - " + recipe.getDescription());
        }

        scanner.close();
    }
}
