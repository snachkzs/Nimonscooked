package entity.item;

import java.util.ArrayList;
import java.util.List;

public abstract class Dish extends Item {
    protected List<Ingredients> ingredients;
    protected boolean isDone;

    public Dish() {

        ingredients = new ArrayList<>();
        stackable = false;
        collision = false;
    }

    @Override
    public String getType() {
        return "dish";
    }

    public void addIngredient(Ingredients ingredient) {
        ingredients.add(ingredient);
        checkCompletion();
    }

    protected abstract void checkCompletion();

    public List<Ingredients> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public boolean isDone() {
        return isDone;
    }
}