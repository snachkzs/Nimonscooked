package entity.stations;

import entity.Chef;
import entity.item.Item;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Station {
    protected Item storedItem;
    public int x, y, width, height;
    
    // Lock untuk thread-safe access
    protected final ReentrantLock lock = new ReentrantLock();
    
    // Track chef yang sedang interact dengan station ini
    protected Chef interactingChef = null;

    public Station(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public Item getStoredItem() {
        return storedItem;
    }
    
    public synchronized void setStoredItem(Item item) {
        this.storedItem = item;
        if (item != null) {
            item.setPosition(this.x, this.y);
        }
    }
    
    public synchronized boolean hasItem() {
        return storedItem != null;
    }
    
    public synchronized Item takeItem() {
        Item item = this.storedItem;
        this.storedItem = null;
        return item;
    }
    
    public boolean tryLock(Chef chef) {
        if (lock.tryLock()) {
            interactingChef = chef;
            return true;
        }
        return false;
    }

    public void unlock(Chef chef) {
        if (interactingChef == chef) {
            interactingChef = null;
            lock.unlock();
        }
    }

    public boolean isOccupied() {
        return lock.isLocked();
    }
    
    public Chef getInteractingChef() {
        return interactingChef;
    }
    
    public abstract void interact(Chef chef);
    public abstract String getType();

    public void update() {
    }
}