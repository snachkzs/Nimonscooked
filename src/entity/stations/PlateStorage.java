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
        if (!chef.getInventory().isEmpty()) {
            System.out.println("Tidak bisa menaruh item di Plate Storage!");
            return;
        }
        
        if (plates.isEmpty()) {
            System.out.println("❌ Piring habis! Tunggu piring kotor kembali dari serving.");
            return;
        }
        
        Plate plate = plates.pop();
        chef.getInventory().add(plate);
        
        if (plate.isClean()) {
            System.out.println("✅ Mengambil piring bersih ✨ (" + plates.size() + " tersisa)");
        } else {
            System.out.println("🧼 Mengambil piring kotor (cuci di Washing Station dulu!) (" + plates.size() + " tersisa)");
        }
    }
    
    public void addDirtyPlate(Plate plate) {
        plates.add(0, plate); // Add to BOTTOM of stack (index 0)
        System.out.println("🍽️  Piring kotor ditambahkan ke BOTTOM stack (" + plates.size() + " total plates)");
    }

    @Override
    public String getType() {
        return "plate_storage";
    }
}