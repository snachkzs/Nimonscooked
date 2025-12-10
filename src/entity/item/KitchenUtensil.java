package entity.item;

import java.util.List;
import java.util.ArrayList;

public abstract class KitchenUtensil extends Item {
    protected List<Preparable> contents;
    protected boolean inUse = false;

    public KitchenUtensil(String name) {
        this.name = name; 
        this.contents = new ArrayList<>();
    }

    public List<Preparable> getContents() {
        return this.contents;
    }

    public void addIngredient(Preparable ingredient) {
        this.contents.add(ingredient);
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