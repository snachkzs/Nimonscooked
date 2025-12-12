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
        
        if (this.storedItem == null) {
            // station empty    
            if (chef.getInventory().isEmpty()) {
                // inventory empty
                Item ingredient = createIngredient();
                if (ingredient != null) {
                    chef.getInventory().add(ingredient);
                    System.out.println("Mengambil " + ingredient.getName() + " dari storage (unlimited stock)");
                }
            } else {
                // put items on station
                Item itemToPlace = chef.getInventory().remove(0);
                setStoredItem(itemToPlace);
                System.out.println("Menaruh " + itemToPlace.getName() + " di ingredient storage");
            }
        } else {
            // Has stored item
            if (chef.getInventory().isEmpty()) {
                // take item from station
                chef.getInventory().add(this.storedItem);
                System.out.println("Mengambil " + storedItem.getName() + " dari ingredient storage");
                this.storedItem = null;
            } else {
                // holding item
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