package entity.order;

import entity.item.Item;
import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private List<String> requiredIngredients; 

    public Recipe(String name, List<String> requiredIngredients) {
        this.name = name;
        this.requiredIngredients = requiredIngredients;
    }

    public String getName() {
        return name;
    }

    public boolean validate(List<Item> itemsOnPlate) {
        // Create list of item names from plate
        List<String> plateItemNames = new ArrayList<>();
        for (Item item : itemsOnPlate) {
            plateItemNames.add(item.getName());
            System.out.println("Plate has: " + item.getName());
        }
        
        // Print required ingredients
        System.out.println("Recipe requires:");
        for (String req : requiredIngredients) {
            System.out.println("  - " + req);
        }
        
        // Check size match
        if (itemsOnPlate.size() != requiredIngredients.size()) {
            System.out.println("Validation failed: Size mismatch. Expected " + requiredIngredients.size() + ", got " + itemsOnPlate.size());
            return false;
        }

        // Check if all required ingredients are on the plate
        for (String required : requiredIngredients) {
            if (!plateItemNames.contains(required)) {
                System.out.println("Missing ingredient: " + required);
                return false;
            }
        }
        
        // Check if plate has any extra ingredients not in recipe
        for (String plateName : plateItemNames) {
            if (!requiredIngredients.contains(plateName)) {
                System.out.println("Extra ingredient not in recipe: " + plateName);
                return false;
            }
        }
        
        System.out.println("Order matches recipe!");
        return true;
    }
}