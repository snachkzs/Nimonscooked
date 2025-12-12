package entity.stations;

import entity.Chef;
import entity.item.Plate;
import entity.item.Item;
import entity.order.OrderManagement;
import java.util.List;

public class ServingCounter extends Station {
    private OrderManagement orderManager;
    private PlateStorage plateStorage;
    
    public ServingCounter(int x, int y, int width, int height) {
        super(x, y, width, height);
    }
    
    public void setOrderManager(OrderManagement orderManager) {
        this.orderManager = orderManager;
    }
    
    public void setPlateStorage(PlateStorage plateStorage) {
        this.plateStorage = plateStorage;
    }
    
    public PlateStorage getPlateStorage() {
        return this.plateStorage;
    }

    @Override
    public void interact(Chef chef) {
        if (!chef.getInventory().isEmpty() && 
            chef.getInventory().get(0) instanceof Plate) {
            
            Plate plate = (Plate) chef.getInventory().get(0);
            
            if (!plate.hasIngredients()) {
                System.out.println("Piring kosong! Tidak ada hidangan untuk disajikan.");
                return;
            }

            List<Item> plateContents = plate.getIngredients();
            boolean success = orderManager.processDelivery(plateContents);
            
            chef.getInventory().remove(0);
            
            if (success) {
                System.out.println("\nOrder berhasil disajikan!");
            } else {
                System.out.println("\nOrder salah!");
            }
            System.out.println("Piring akan kembali dalam 10 detik...");
            
            returnDirtyPlate(plate);
            
        } else {
            System.out.println("Tidak ada hidangan untuk disajikan!");
        }
    }
    
    private void returnDirtyPlate(Plate plate) {
        final PlateStorage storage = this.plateStorage;
        plate.setDirty();
        plate.clearIngredients();
        
        new Thread(() -> {
            try {
                Thread.sleep(10000);
                if (storage != null) {
                    storage.addDirtyPlate(plate);
                    System.out.println("Piring kotor kembali ke plate storage");
                } else {
                    System.out.println("ERROR: PlateStorage is null!");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    public String getType() {
        return "serving_counter";
    }
}