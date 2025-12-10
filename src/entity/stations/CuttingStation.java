package entity.stations;

import entity.Chef;
import entity.item.Item;
import entity.item.InterfaceChopable;

public class CuttingStation extends Station {

    public CuttingStation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void interact(Chef chef) {
        // C key: Place item on station OR take item from station
        if (this.storedItem == null && !chef.getInventory().isEmpty()) {
            // Place item on cutting station
            this.storedItem = chef.getInventory().remove(0);
            System.out.println("Menaruh " + storedItem.getName() + " di Cutting Station.");
            return;
        }
        
        if (this.storedItem != null && chef.getInventory().isEmpty()) {
            // Take item from station (only if it's already chopped or not choppable)
            if (this.storedItem instanceof InterfaceChopable) {
                InterfaceChopable itemPotong = (InterfaceChopable) this.storedItem;
                if (itemPotong.isChopped()) {
                    // Already chopped, can take it
                    chef.getInventory().add(this.storedItem);
                    System.out.println("Mengambil " + storedItem.getName() + " yang sudah dipotong.");
                    this.storedItem = null;
                } else {
                    System.out.println("Item belum dipotong! Tekan V untuk memotong.");
                }
            } else {
                // Not choppable, just take it
                chef.getInventory().add(this.storedItem);
                System.out.println("Mengambil " + storedItem.getName() + " dari station.");
                this.storedItem = null;
            }
            return;
        }
        
        if (this.storedItem == null && chef.getInventory().isEmpty()) {
            System.out.println("Cutting station kosong dan tangan kosong.");
        } else if (!chef.getInventory().isEmpty()) {
            System.out.println("Cutting station sudah ada item. Ambil dulu item yang ada.");
        }
    }
    
    public void startCutting(Chef chef) {
        // V key: Start chopping process (only if item is choppable and not chopped yet)
        if (this.storedItem == null) {
            System.out.println("Tidak ada item di cutting station untuk dicincang.");
            return;
        }
        
        if (!(this.storedItem instanceof InterfaceChopable)) {
            System.out.println("Item ini tidak bisa dipotong!");
            return;
        }
        
        InterfaceChopable itemPotong = (InterfaceChopable) this.storedItem;
        if (itemPotong.isChopped()) {
            System.out.println("Item sudah dipotong! Tekan C untuk mengambil.");
            return;
        }
        
        if (!chef.getInventory().isEmpty()) {
            System.out.println("Kosongkan tangan untuk memotong!");
            return;
        }
        
        processCutting(chef, itemPotong);
    }

    private void processCutting(Chef chef, InterfaceChopable item) {
        new Thread(() -> {
            try {
                System.out.println("Mulai memotong... (Busy 3 detik)");
                chef.setBusy(true);
                
                Thread.sleep(3000);
                
                storedItem = item.getChoppedItem();
                System.out.println("Selesai memotong!");
                
            } catch (InterruptedException e) {
                System.out.println("Proses memotong terganggu.");
            } finally {
                chef.setBusy(false);
            }
        }).start();
    }

    @Override
    public String getType() {
        return "cutting_station";
    }
}