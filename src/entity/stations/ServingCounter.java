package entity.stations;

import entity.Chef;
import entity.item.Plate;

public class ServingCounter extends Station {
    @Override
    public void interact(Chef chef) {
        if (chef.inventory instanceof Plate) {
            Plate p = (Plate) chef.inventory;
            // Validasi Order harusnya ada di sini (Panggil OrderManagement)
            System.out.println("Menyajikan makanan...");
            
            chef.inventory = null;
            new Thread(() -> {
                try {
                    Thread.sleep(10000); 
                    // TODO: Panggil PlateStorage.returnDirtyPlate(p);
                    // (Butuh akses ke objek PlateStorage yang ada di Map)
                    System.out.println("Piring kembali kotor ke PlateStorage.");
                } catch (Exception e) {}
            }).start();
        }
    }
}