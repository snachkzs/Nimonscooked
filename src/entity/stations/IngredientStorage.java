package entity.stations;

import entity.Chef;
import entity.item.*;

public class IngredientStorage extends Station {
    private String ingredientType;

    public IngredientStorage(int x, int y, int width, int height, String ingredientType) {
        super(x, y, width, height);
        this.ingredientType = ingredientType;
    }

    @Override
    public void interact(Chef chef) {
        // Behavior: 
        // 1. If empty hands + no stored item -> take ingredient (unlimited stock)
        // 2. If holding item + no stored item -> place item (works like Assembly Station)
        // 3. If empty hands + has stored item -> take stored item
        // 4. If holding item + has stored item -> cannot interact
        
        if (this.storedItem == null) {
            // No item stored on station
            if (chef.getInventory().isEmpty()) {
                // Empty hands -> give ingredient (unlimited stock)
                Item ingredient = createIngredient();
                if (ingredient != null) {
                    chef.getInventory().add(ingredient);
                    System.out.println("Mengambil " + ingredient.getName() + " dari storage (unlimited stock)");
                }
            } else {
                // Holding item -> place on station (like Assembly Station)
                this.storedItem = chef.getInventory().remove(0);
                System.out.println("Menaruh " + storedItem.getName() + " di ingredient storage");
            }
        } else {
            // Has stored item
            if (chef.getInventory().isEmpty()) {
                // Empty hands -> take stored item
                chef.getInventory().add(this.storedItem);
                System.out.println("Mengambil " + storedItem.getName() + " dari ingredient storage");
                this.storedItem = null;
            } else {
                // Holding item -> cannot interact
                System.out.println("Station sudah ada item! Kosongkan tangan atau ambil item di station dulu");
            }
        }
    }
    
    private Item createIngredient() {
        switch (ingredientType) {
            case "daging":
                return new Daging();
            case "roti":
                return new Roti();
            case "keju":
                return new Keju();
            case "lettuce":
                return new Lettuce();
            case "tomat":
                return new Tomat();
            default:
                return null;
        }
    }
    
    public String getIngredientType() {
        return ingredientType;
    }

    public String getStoredIngredientName() {
        return ingredientType;
    }

    @Override
    public String getType() {
        return "ingredient_storage_" + ingredientType;
    }
}