package entity.item;

import java.util.List;
import java.util.ArrayList;

public abstract class KitchenUtensil extends Item {
    protected List<Item> contents;
    protected boolean inUse = false;

    public KitchenUtensil(String name) {
        this.name = name; 
        this.contents = new ArrayList<>();
    }

    public List<Item> getContents() {
        return this.contents;
    }

    public boolean addIngredient(Item ingredient) {
        this.contents.add(ingredient);
        return true;
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }

    public abstract String getAssetPath();

    @Override
    public String getType() {
        return "KitchenUtensil";
    }

    @Override
    public void use() {
        System.out.println("Menggunakan " + this.name);
    }
}