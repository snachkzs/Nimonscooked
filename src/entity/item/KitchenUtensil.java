package entity.item;

import entity.item.Item; 
import entity.item.Preparable;
import java.util.List;
import java.util.ArrayList;

public abstract class KitchenUtensil extends Item {
    protected List<Preparable> contents;

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