package entity.stations;
import entity.Chef;

public class TrashStation extends Station {
    public TrashStation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void interact(Chef chef) {
        if (!chef.getInventory().isEmpty()) {
            String itemName = chef.getInventory().get(0).getName();
            chef.getInventory().remove(0);
            System.out.println("Membuang " + itemName);
        }
    }

    @Override
    public String getType() {
        return "trash_station";
    }
}