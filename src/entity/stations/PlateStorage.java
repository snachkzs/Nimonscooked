package entity.stations;

import entity.Chef;
import entity.item.Plate;
import java.util.Stack;

public class PlateStorage extends Station {
    private Stack<Plate> plates = new Stack<>();

    public PlateStorage() {
        for(int i=0; i<5; i++) plates.push(new Plate());
    }

    public void returnDirtyPlate(Plate p) {
        p.dirty();
        plates.push(p);
    }

    @Override
    public void interact(Chef chef) {
        if (chef.inventory == null && !plates.isEmpty()) {
            Plate topPlate = plates.peek();
            if (topPlate.isClean()) {
                chef.inventory = plates.pop();
                System.out.println("Mengambil Piring Bersih.");
            } else {
                chef.inventory = plates.pop();
                System.out.println("Mengambil Piring Kotor (Harus dicuci!).");
            }
        }
    }
}