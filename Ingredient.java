public class Ingredient {
    private String name;
    private String quantity;

    public Ingredient(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Ingredient)) return false;
        Ingredient other = (Ingredient) obj;
        return name.equals(other.name) && quantity.equals(other.quantity);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + quantity.hashCode();
    }

    @Override
    public String toString() {
        return quantity + " of " + name;
    }
}
