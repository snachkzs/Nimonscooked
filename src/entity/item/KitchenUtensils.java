package entity.item;

public abstract class KitchenUtensils extends Item {
    protected boolean inUse = false;

    @Override
    public String getType() {
        return "kitchenUtensils";
    }

    public boolean isInUse() {
        return inUse;
    }

    public void setInUse(boolean inUse) {
        this.inUse = inUse;
    }
}