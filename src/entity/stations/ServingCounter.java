package entity.stations;

import entity.Chef;
import entity.item.Plate;

public class ServingCounter extends Station {
    public ServingCounter(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void interact(Chef chef) {
        if (!chef.getInventory().isEmpty() && 
            chef.getInventory().get(0) instanceof Plate) {
            System.out.println("Menyajikan makanan...");
            
            final Plate servedPlate = (Plate) chef.getInventory().remove(0);
            
            new Thread(() -> {
                try {
                    Thread.sleep(10000);
                    System.out.println("Piring telah kembali kotor ke PlateStorage.");
                    // nnt return plate to PlateStorage
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } else {
            System.out.println("Tidak ada hidangan untuk disajikan!");
        }
    }

    @Override
    public String getType() {
        return "serving_counter";
    }
}