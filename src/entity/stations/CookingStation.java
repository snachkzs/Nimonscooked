package entity.stations;

import entity.Chef;
import entity.item.Item;
import entity.item.InterfaceCookable;

public class CookingStation extends Station {
    
    private boolean isCooking = false;

    @Override
    public void interact(Chef chef) {
        if (this.storedItem == null && chef.inventory != null) {
            if (chef.inventory instanceof InterfaceCookable) {
                this.storedItem = chef.inventory;
                chef.inventory = null;
                System.out.println("Menaruh item di Kompor.");
                startCookingProcess(); 
            } else {
                System.out.println("Item ini tidak bisa dimasak!");
            }
            return;
        }
        if (this.storedItem != null && chef.inventory == null) {
            chef.inventory = this.storedItem;
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
                    food.getCookedItem();
                    System.out.println("MAKANAN MATANG! (Segera ambil dalam 12s!)");
                } else {return;}
                Thread.sleep(12000);
                
                if (isCooking && storedItem != null) {
                    System.out.println("GOSONG! Makanan hangus (Burned).");
                }

            }catch(InterruptedException e){
                System.out.println("Cooking interrupted.");
            }
        }).start();
    }
}