package entity.item;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

public class Plate extends KitchenUtensil {
    private boolean isDirty = false;
    private List<Item> ingredients = new ArrayList<>();

    public Plate() {
        this(false);
    }
    
    public Plate(boolean isDirty) {
        super(isDirty ? "Plate (Dirty)" : "Plate");
        this.collision = false;
        this.isDirty = isDirty;
        loadImage();
    }

    private void loadImage() {
        try {
            if (isDirty) {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/kitchen-utensil/plate_dirty.png"));
            } else {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/kitchen-utensil/plate_clean.png"));
            }
        } catch (IOException e) {
            System.out.println("Error loading piring image: " + e.getMessage());
        }
    }  

    @Override
    public String getAssetPath() {
        return isDirty ? "/items/plate_dirty.png" : "/items/plate_clean.png";
    }

    public boolean isClean() {
        return !isDirty;
    }

    public boolean addIngredient(Item ingredient) {
        if (isDirty) {
            System.out.println("Tidak bisa menambahkan ingredient ke piring kotor!");
            return false;
        }
        
        if (ingredient instanceof InterfaceCookable) {
            InterfaceCookable cookable = (InterfaceCookable) ingredient;
            if (cookable.isBurned()) {
                System.out.println(ingredient.getName() + " sudah gosong! Tidak bisa ditaruh di piring. Buang ke trash!");
                return false;
            }
        }
        
        if (ingredient instanceof Ingredients || ingredient instanceof Dish) {
            ingredients.add(ingredient);
            System.out.println("Menambahkan " + ingredient.getName() + " ke piring");
            return true;
        }
        
        System.out.println("Item ini tidak bisa ditambahkan ke piring!");
        return false;
    }

    public boolean transferFromFryingPan(FryingPan fryingPan) {
        return fryingPan.transferToPlate(this);
    }

    public Item removeIngredient(int index) {
        if (index >= 0 && index < ingredients.size()) {
            return ingredients.remove(index);
        }
        return null;
    }

    public List<Item> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public void clearIngredients() {
        ingredients.clear();
        System.out.println("Piring dibersihkan dari semua ingredients");
    }

    public boolean hasIngredients() {
        return !ingredients.isEmpty();
    }

    public int getIngredientCount() {
        return ingredients.size();
    }

    public boolean isDirty() {
        return isDirty;
    }

    public void setDirty() {
        this.isDirty = true;
        this.name = "Plate (Dirty)";
        this.ingredients.clear();
        loadImage();
    }

    public void setClean() {
        this.isDirty = false;
        this.name = "Plate";
        loadImage();
    }

    public void wash() {
        this.isDirty = false;
        this.name = "Plate";
        loadImage();
        System.out.println("Piring telah dicuci bersih");
    }

    @Override
    public String getType() {
        return "plate";
    }
}