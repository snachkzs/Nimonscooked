package entity.stations;

import entity.Chef;
import entity.item.Daging;
import entity.item.Roti;
import entity.item.Keju;

public class IngredientStorage extends Station {
    private String ingredientName;

    public void setIngredientType(String type) {
        this.ingredientName = type;
    }

    @Override
    public void interact(Chef chef) {
        if (chef.inventory == null && ingredientName != null) {
            switch (ingredientName) {
                case "Meat": chef.inventory = new Daging(); break;
                // case "Bread": chef.inventory = new Roti(); break;
                default: System.out.println("Unknown Ingredient"); return;
            }
            System.out.println("Mengambil " + ingredientName);
        }
    }
}