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
    
    public List<String> getRequiredIngredients() {
        return requiredIngredients;
    }

    public boolean validate(List<Item> itemsOnPlate) {
        List<String> plateItemNames = new ArrayList<>();
        for (Item item : itemsOnPlate) {
            plateItemNames.add(item.getName());
            System.out.println("Plate has: " + item.getName());
        }
        
        System.out.println("Recipe requires:");
        for (String req : requiredIngredients) {
            System.out.println("  - " + req);
        }
        
        if (itemsOnPlate.size() != requiredIngredients.size()) {
            System.out.println("Validation failed: Size mismatch. Expected " + requiredIngredients.size() + ", got " + itemsOnPlate.size());
            return false;
        }

        for (String required : requiredIngredients) {
            if (!plateItemNames.contains(required)) {
                System.out.println("Missing ingredient: " + required);
                return false;
            }
        }
        
        System.out.println("Order matches recipe!");
        return true;
    }
}