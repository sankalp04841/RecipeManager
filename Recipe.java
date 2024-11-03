import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private String cuisine;
    private String mealType;
    private boolean isVegetarian;
    private boolean isVegan;
    private int prepTime;
    private List<Ingredient> ingredients;
    private boolean favorite; 

    public Recipe(String name, String description, String cuisine, String mealType,
                  boolean isVegetarian, boolean isVegan, int prepTime) throws InvalidPrepTimeException {
        if (prepTime < 0) {
            throw new InvalidPrepTimeException("Preparation time cannot be negative.");
        }
        this.name = name;
        this.description = description;
        this.cuisine = cuisine;
        this.mealType = mealType;
        this.isVegetarian = isVegetarian;
        this.isVegan = isVegan;
        this.prepTime = prepTime;
        this.ingredients = new ArrayList<>();
        this.favorite = false; 
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCuisine() {
        return cuisine;
    }

    public String getMealType() {
        return mealType;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public boolean isVegan() {
        return isVegan;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Recipe)) return false;
        Recipe other = (Recipe) obj;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
