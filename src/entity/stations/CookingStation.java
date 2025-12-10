package entity.stations;

import entity.Chef;
import entity.item.Item;
import entity.item.InterfaceCookable;

public class CookingStation extends Station {
    private boolean isCooking = false;

    public CookingStation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void interact(Chef chef) {
        if (this.storedItem == null && !chef.getInventory().isEmpty()) {
            Item item = chef.getInventory().get(0);
            if (item instanceof InterfaceCookable) {
                // Check if it's Daging that needs to be chopped first
                if (item instanceof entity.item.Daging) {
                    entity.item.Daging daging = (entity.item.Daging) item;
                    if (!daging.isChopped()) {
                        System.out.println("Daging harus dipotong dulu sebelum dimasak!");
                        return;
                    }
                }
                this.storedItem = chef.getInventory().remove(0);
                System.out.println("Menaruh item di Kompor.");
                startCookingProcess();
            } else {
                System.out.println("Item ini tidak bisa dimasak!");
            }
            return;
        }
        if (this.storedItem != null && chef.getInventory().isEmpty()) {
            chef.getInventory().add(this.storedItem);
            this.storedItem = null;
            this.isCooking = false;
            System.out.println("Mengambil item dari kompor.");
        }
    }

    private void startCookingProcess() {
        isCooking = true;
        
        new Thread(() -> {
            try {
                InterfaceCookable food = (InterfaceCookable) storedItem;
                System.out.println("Kompor ON: Sedang memasak... (12s)");
                
                Thread.sleep(12000);
                
                if (isCooking && storedItem != null) {
                    storedItem = food.getCookedItem();
                    System.out.println("MAKANAN MATANG! (Segera ambil dalam 12s!)");
                } else {
                    return;
                }

                Thread.sleep(12000);
                
                if (isCooking && storedItem != null) {
                    System.out.println("GOSONG! Makanan hangus (Burned).");
                }

            } catch (InterruptedException e) {
                System.out.println("Cooking interrupted.");
            }
        }).start();
    }

    @Override
    public String getType() {
        return "cooking_station";
    }
}