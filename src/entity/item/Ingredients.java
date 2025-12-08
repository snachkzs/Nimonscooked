package entity.item;

public abstract class Ingredients extends Item {
    public boolean raw = true;

    @Override
    public String getType() {
        return "ingredients";
    }

    public boolean isRaw() {
        return raw;
    }
}

