package entity.stations;
import entity.Chef;

public class TrashStation extends Station {
    @Override
    public void interact(Chef chef) {
        if (chef.inventory != null) {
            System.out.println("Membuang " + chef.inventory.getName());
            chef.inventory = null;
        }
    }
}