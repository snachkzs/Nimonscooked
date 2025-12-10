package entity.stations;

import entity.Chef;
import entity.item.Plate;
import java.util.Stack;

public class PlateStorage extends Station {
    private Stack<Plate> plates = new Stack<>();
    private int maxPlates = 4;

    public PlateStorage(int x, int y, int width, int height) {
        super(x, y, width, height);
        // Initialize with 4 plates
        for (int i = 0; i < maxPlates; i++) {
            plates.push(new Plate());
        }
    }

    @Override
    public void interact(Chef chef) {
        if (!plates.isEmpty() && chef.getInventory().isEmpty()) {
            chef.getInventory().add(plates.pop());
            System.out.println("Mengambil piring (" + plates.size() + " tersisa)");
        } else if (plates.isEmpty()) {
            System.out.println("Piring habis!");
        } else {
            System.out.println("Inventory penuh!");
        }
    }

    @Override
    public String getType() {
        return "plate_storage";
    }
}