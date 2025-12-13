package entity.stations;

import entity.Chef;
import entity.item.Plate;
import java.util.Stack;

public class PlateStorage extends Station {
    private Stack<Plate> plates = new Stack<>();
    private int maxPlates = 4;

    public PlateStorage(int x, int y, int width, int height) {
        super(x, y, width, height);
        for (int i = 0; i < maxPlates; i++) {
            Plate cleanPlate = new Plate();
            plates.push(cleanPlate);
        }
    }

    @Override
    public void interact(Chef chef) {
        // stack dirty plates if holding one
        if (chef.getInventory().size() == 1) {
            if (chef.getInventory().get(0) instanceof Plate) {
                Plate heldPlate = (Plate) chef.getInventory().get(0);
                if (heldPlate.isDirty() && !plates.isEmpty() && !plates.peek().isClean()) {
                    // Stack dirty plates
                    Plate plate = plates.pop();
                    chef.getInventory().add(plate);
                    System.out.println("Mengambil piring kotor tambahan (" + chef.getInventory().size() + " di tangan, " + plates.size() + " tersisa)");
                    return;
                }
            }
            System.out.println("Tidak bisa menaruh item di Plate Storage!");
            return;
        }
        
        if (!chef.getInventory().isEmpty()) {
            System.out.println("Tidak bisa menaruh item di Plate Storage!");
            return;
        }
        
        if (plates.isEmpty()) {
            System.out.println("Piring habis! Tunggu piring kotor kembali dari serving.");
            return;
        }
        
        Plate plate = plates.pop();
        chef.getInventory().add(plate);
        
        if (plate.isClean()) {
            System.out.println("Mengambil piring bersih (" + plates.size() + " tersisa)");
        } else {
            System.out.println("Mengambil piring kotor (" + plates.size() + " tersisa)");
        }
    }
    
    public void addDirtyPlate(Plate plate) {
        plates.add(0, plate);
        System.out.println("Piring kotor ditambahkan ke bottom stack (" + plates.size() + " total plates)");
    }

    @Override
    public String getType() {
        return "plate_storage";
    }
}