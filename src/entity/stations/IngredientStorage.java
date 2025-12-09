package entity.stations;

import entity.Chef;
import entity.item.Daging;
import entity.item.Roti;
import entity.item.Keju;
import entity.item.Lettuce;
import entity.item.Tomat;

public class IngredientStorage extends Station {
    private String ingredientName;

    public void setIngredientType(String type) {
        this.ingredientName = type;
    }

    @Override
    public void interact(Chef chef) {
        if (chef.inventory == null && ingredientName != null) {
            switch (ingredientName) {
                case "Meat": 
                    chef.inventory = new Daging(); 
                    break;
                case "Bread": 
                    chef.inventory = new Roti(); 
                    break;
                case "Cheese": 
                    chef.inventory = new Keju(); 
                    break;
                case "Lettuce": 
                    chef.inventory = new Lettuce(); 
                    break;
                case "Tomato": 
                    chef.inventory = new Tomat(); 
                    break;
                default: 
                    System.out.println("Error: Bahan '" + ingredientName + "' tidak dikenal!");
                    return;
            }
            System.out.println("Mengambil " + ingredientName);
        }
    }
}