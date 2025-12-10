package entity.stations;

import entity.Chef;
import entity.item.Item;

public abstract class Station {
    protected Item storedItem;
    public int x, y, width, height; // Public untuk bisa diakses dari Chef

    public Station(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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
    public abstract String getType();
}