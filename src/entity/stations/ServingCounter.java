package entity.stations;

import entity.Chef;
import entity.item.Plate;

public class ServingCounter extends Station {
    @Override
    public void interact(Chef chef) {
        if (chef.inventory instanceof Plate) {
            System.out.println("Menyajikan makanan...");
            
            chef.inventory = null;
            
            new Thread(() -> {
                try {
                    Thread.sleep(10000); 
                    System.out.println("Piring telah kembali kotor ke PlateStorage.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}