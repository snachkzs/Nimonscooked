package entity.item;

public abstract class Ingredients extends Item {
    protected boolean raw = true;

    @Override
    public String getType() {
        return "ingredients";
    }

    public boolean isRaw() {
        return raw;
    }
}