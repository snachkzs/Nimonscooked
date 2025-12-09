package entity.stations;

import entity.Chef;
import entity.item.Item;

public abstract class Station {
    protected Item storedItem;

    public Item getStoredItem() {
        return storedItem;
    }
    
    public void setStoredItem(Item item) {
        this.storedItem = item;
    }
    
    public boolean hasItem() {
        return storedItem != null;
    }
    
    public Item takeItem() {
        Item item = this.storedItem;
        this.storedItem = null;
        return item;
    }

    public abstract void interact(Chef chef);
}