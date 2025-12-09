package entity.stations;

import entity.Chef;
import entity.item.Item;
import entity.item.InterfaceChopable;

public class CuttingStation extends Station {

    @Override
    public void interact(Chef chef) {
        if (this.storedItem == null && chef.inventory != null) {
            this.storedItem = chef.inventory;
            chef.inventory = null;
            System.out.println("Menaruh " + storedItem.getName() + " di Cutting Station.");
            return;
        }
        if (this.storedItem != null) {
            if (this.storedItem instanceof InterfaceChopable) {
                InterfaceChopable itemPotong = (InterfaceChopable) this.storedItem;
                if (!itemPotong.isChopped()) {
                    if (chef.inventory == null) {
                        processCutting(chef, itemPotong);
                    } else {
                        System.out.println("Kosongkan tangan untuk memotong!");
                    }
                    return;
                }
            }
            if (chef.inventory == null) {
                chef.inventory = this.storedItem;
                this.storedItem = null;
                System.out.println("Mengambil item dari meja.");
            }
        }
    }

    private void processCutting(Chef chef, InterfaceChopable item) {
        new Thread(() -> {
            try {
                System.out.println("Mulai memotong... (Busy 3 detik)");
                chef.setBusy(true);
                
                Thread.sleep(3000); 
                
                item.getChoppedItem(); 
                System.out.println("Selesai memotong!");
                
            } catch (InterruptedException e) {
                System.out.println("Proses memotong terganggu.");
            } finally {
                chef.setBusy(false);
            }
        }).start();
    }
}