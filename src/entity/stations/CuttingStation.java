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
        // pickup/drop
        if (this.storedItem == null && !chef.getInventory().isEmpty()) {

            Item itemToPlace = chef.getInventory().remove(0);
            setStoredItem(itemToPlace);
            System.out.println("Menaruh " + itemToPlace.getName() + " di Cutting Station.");
            return;
        }
        
        if (this.storedItem != null && chef.getInventory().isEmpty()) {
            if (this.storedItem instanceof InterfaceChopable) {
                InterfaceChopable itemPotong = (InterfaceChopable) this.storedItem;
                if (itemPotong.isChopped()) {
                    chef.getInventory().add(this.storedItem);
                    System.out.println("Mengambil " + storedItem.getName() + " yang sudah dipotong.");
                    this.storedItem = null;
                } else {
                    System.out.println("Item belum dipotong! Tekan . untuk memotong.");
                }
            } else {
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
            System.out.println("Item sudah dipotong! Tekan / untuk mengambil.");
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
                System.out.println(chef.getName() + " mulai memotong... (Busy 3 detik)");
                chef.setBusy(true);
                
                Thread.sleep(3000);
                
                synchronized(this) {
                    item.setChopped(true);
                    System.out.println(chef.getName() + " selesai memotong! Tekan / untuk ambil.");
                }
                
                chef.setBusy(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
                chef.setBusy(false);
            }
        }, "Cutting-" + chef.getName()).start();
    }

    @Override
    public String getType() {
        return "cutting_station";
    }
}