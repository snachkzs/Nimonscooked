package entity.item;

public class ItemFactory {
    
    // public static Item createItem(String itemType) {
    //     switch(itemType.toLowerCase()) {
    //         // Ingredients
    //         case "tomato":
    //             return new Tomato();
    //         case "lettuce":
    //             return new Lettuce();
    //         case "onion":
    //             return new Onion();
    //         case "meat":
    //             return new Meat();
                
    //         // Dishes
    //         case "salad":
    //             return new Salad();
    //         case "burger":
    //             return new Burger();
                
    //         // Kitchen Utensils
    //         case "knife":
    //             return new Knife();
    //         case "plate":
    //             return new Plate();
    //         case "pan":
    //             return new Pan();
                
    //         default:
    //             throw new IllegalArgumentException("Unknown item type: " + itemType);
    //     }
    // }
    
    // Overload for items with position
    // public static Item createItem(String itemType, int x, int y) {
    //     Item item = createItem(itemType);
    //     item.setPosition(x, y);
    //     return item;
    // }
}