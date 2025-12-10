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
        if (itemsOnPlate.size() != requiredIngredients.size()) {
            return false;
        }

        List<String> plateItemNames = new ArrayList<>();
        for (Item item : itemsOnPlate) {
            plateItemNames.add(item.getName());
        }

        return plateItemNames.containsAll(requiredIngredients);
    }
}