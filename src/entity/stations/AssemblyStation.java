package entity.stations;
import entity.Chef;

public class AssemblyStation extends Station {
    @Override
    public void interact(Chef chef) {
        if (this.storedItem == null && chef.inventory != null) {
            this.storedItem = chef.inventory;
            chef.inventory = null;
        } else if (this.storedItem != null && chef.inventory == null) {
            chef.inventory = this.storedItem;
            this.storedItem = null;
        }
    }
}