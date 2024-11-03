import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class RecipeManagerGUI {
    private RecipeManager recipeManager;
    private JFrame frame;
    private JPanel recipePanel;
    private JTextField nameField, descriptionField, cuisineField, mealTypeField, prepTimeField, searchField;
    private JCheckBox vegetarianCheckbox, veganCheckbox;
    private JRadioButton selectedRecipeButton;
    private Recipe selectedRecipe;

    public RecipeManagerGUI() {
        recipeManager = new RecipeManager("C:\\RecipeMan\\Recipes.txt");
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Recipe Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Recipe Manager", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.BLUE);
        titleLabel.setPreferredSize(new Dimension(800, 50));
        frame.add(titleLabel, BorderLayout.NORTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());
        searchField = new JTextField(20);
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchRecipes());
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        frame.add(searchPanel, BorderLayout.SOUTH);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        inputPanel.add(descriptionField);

        inputPanel.add(new JLabel("Cuisine:"));
        cuisineField = new JTextField();
        inputPanel.add(cuisineField);

        inputPanel.add(new JLabel("Meal Type:"));
        mealTypeField = new JTextField();
        inputPanel.add(mealTypeField);

        inputPanel.add(new JLabel("Preparation Time (min):"));
        prepTimeField = new JTextField();
        inputPanel.add(prepTimeField);

        vegetarianCheckbox = new JCheckBox("Vegetarian");
        veganCheckbox = new JCheckBox("Vegan");
        inputPanel.add(vegetarianCheckbox);
        inputPanel.add(veganCheckbox);

        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Recipe");
        addButton.addActionListener(e -> addRecipe());
        buttonPanel.add(addButton);

        JButton viewButton = new JButton("View Recipes");
        viewButton.addActionListener(e -> viewRecipes());
        buttonPanel.add(viewButton);

        JButton viewFavoritesButton = new JButton("View Favorites");
        viewFavoritesButton.addActionListener(e -> viewFavorites());
        buttonPanel.add(viewFavoritesButton);

        JButton confirmFavoriteButton = new JButton("Confirm Favorite");
        confirmFavoriteButton.addActionListener(e -> confirmFavorite());
        buttonPanel.add(confirmFavoriteButton);

        frame.add(buttonPanel, BorderLayout.EAST);

        recipePanel = new JPanel();
        recipePanel.setLayout(new BoxLayout(recipePanel, BoxLayout.Y_AXIS));
        frame.add(new JScrollPane(recipePanel), BorderLayout.WEST);

        frame.setVisible(true);
    }

    private void addRecipe() {
        String name = nameField.getText();
        String description = descriptionField.getText();
        String cuisine = cuisineField.getText();
        String mealType = mealTypeField.getText();
        boolean isVegetarian = vegetarianCheckbox.isSelected();
        boolean isVegan = veganCheckbox.isSelected();
        int prepTime;

        try {
            prepTime = Integer.parseInt(prepTimeField.getText());
            Recipe recipe = new Recipe(name, description, cuisine, mealType, isVegetarian, isVegan, prepTime);
            recipeManager.addRecipe(recipe);
            JOptionPane.showMessageDialog(frame, "Recipe added successfully!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Preparation time must be a number.");
        } catch (InvalidPrepTimeException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    private void viewRecipes() {
        recipePanel.removeAll();
        List<Recipe> recipes = recipeManager.getAllRecipes();

        if (recipes.isEmpty()) {
            recipePanel.add(new JLabel("No recipes available."));
        } else {
            ButtonGroup buttonGroup = new ButtonGroup();
            for (Recipe recipe : recipes) {
                JRadioButton radioButton = new JRadioButton(recipe.getName());
                radioButton.setSelected(recipe.isFavorite());
                radioButton.addActionListener(e -> showRecipeDetails(recipe, radioButton));
                buttonGroup.add(radioButton);
                recipePanel.add(radioButton);
            }
        }

        recipePanel.revalidate();
        recipePanel.repaint();
    }

    private void showRecipeDetails(Recipe recipe, JRadioButton radioButton) {
        selectedRecipe = recipe;
        selectedRecipeButton = radioButton;
        String details = String.format("Name: %s\nDescription: %s\nCuisine: %s\nMeal Type: %s\nVegetarian: %b\nVegan: %b\nPreparation Time: %d min",
                recipe.getName(), recipe.getDescription(), recipe.getCuisine(), recipe.getMealType(), recipe.isVegetarian(), recipe.isVegan(), recipe.getPrepTime());
        JOptionPane.showMessageDialog(frame, details, "Recipe Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void confirmFavorite() {
        if (selectedRecipe != null) {
            recipeManager.toggleFavorite(selectedRecipe);
            String message = selectedRecipe.isFavorite() ? "Added to favorites: " : "Removed from favorites: ";
            JOptionPane.showMessageDialog(frame, message + selectedRecipe.getName());
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a recipe first.");
        }
    }

    private void viewFavorites() {
        List<Recipe> favoriteRecipes = recipeManager.getFavoriteRecipes();
        StringBuilder details = new StringBuilder();
        if (favoriteRecipes.isEmpty()) {
            details.append("No favorite recipes found.");
        } else {
            for (Recipe recipe : favoriteRecipes) {
                details.append("Name: ").append(recipe.getName()).append("\n")
                       .append("Description: ").append(recipe.getDescription()).append("\n")
                       .append("Cuisine: ").append(recipe.getCuisine()).append("\n")
                       .append("Meal Type: ").append(recipe.getMealType()).append("\n")
                       .append("Vegetarian: ").append(recipe.isVegetarian()).append("\n")
                       .append("Vegan: ").append(recipe.isVegan()).append("\n")
                       .append("Preparation Time: ").append(recipe.getPrepTime()).append(" min\n\n");
            }
        }
        JOptionPane.showMessageDialog(frame, details.toString());
    }

    private void searchRecipes() {
        String query = searchField.getText().trim();
        List<Recipe> results = recipeManager.searchRecipes(query);
        StringBuilder details = new StringBuilder();
        if (results.isEmpty()) {
            details.append("No matching recipes found.");
        } else {
            for (Recipe recipe : results) {
                details.append("Name: ").append(recipe.getName()).append("\n")
                       .append("Description: ").append(recipe.getDescription()).append("\n")
                       .append("Cuisine: ").append(recipe.getCuisine()).append("\n")
                       .append("Meal Type: ").append(recipe.getMealType()).append("\n")
                       .append("Vegetarian: ").append(recipe.isVegetarian()).append("\n")
                       .append("Vegan: ").append(recipe.isVegan()).append("\n")
                       .append("Preparation Time: ").append(recipe.getPrepTime()).append(" min\n\n");
            }
        }
        JOptionPane.showMessageDialog(frame, details.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RecipeManagerGUI::new);
    }
}
