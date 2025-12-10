package entity.stations;

import entity.Chef;
import entity.item.Plate;
import java.util.Stack;

public class WashingStation extends Station {
    // Washing area
    private Stack<Plate> dirtyPlates = new Stack<>();
    
    // Clean plates area
    private Stack<Plate> cleanPlates = new Stack<>();
    
    private int washingProgress = 0;
    private final int WASHING_TIME = 600; // 10 seconds
    private boolean isWashing = false;
    private Chef currentChef = null;

    public WashingStation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    public void update() {
        if (isWashing && currentChef != null && !dirtyPlates.isEmpty()) {
            if (isChefNearby(currentChef)) {
                washingProgress++;
                if (washingProgress >= WASHING_TIME) {
                    completeCleaning();
                }
            } else {
                pauseWashing();
            }
        }
    }
    
    private void completeCleaning() {
        Plate cleanedPlate = dirtyPlates.pop();
        cleanedPlate.wash();
        cleanPlates.push(cleanedPlate);
        System.out.println("Piring selesai dicuci! (Bersih: " + cleanPlates.size() + ", Kotor: " + dirtyPlates.size() + ")");
        resetWashing();
    }
    
    private void pauseWashing() {
        if (washingProgress > 0) {
            System.out.println("Chef pergi - Progres tersimpan (" + (washingProgress * 100 / WASHING_TIME) + "%)");
        }
        isWashing = false;
        currentChef = null;
    }
    
    private void resetWashing() {
        washingProgress = 0;
        isWashing = false;
        currentChef = null;
    }
    
    private boolean isChefNearby(Chef chef) {
        int chefX = chef.getX();
        int chefY = chef.getY();
        int distance = Math.abs(chefX - this.x) + Math.abs(chefY - this.y);
        return distance <= 64;
    }

    @Override
    public void interact(Chef chef) {
        boolean hasItem = !chef.getInventory().isEmpty();
        Plate heldPlate = hasItem && chef.getInventory().get(0) instanceof Plate 
                         ? (Plate) chef.getInventory().get(0) : null;
        
        // put dirty plate on station area
        if (heldPlate != null) {
            if (heldPlate.isDirty()) {
                dirtyPlates.push(heldPlate);
                chef.getInventory().remove(0);
                System.out.println("Menaruh piring kotor (Total: " + dirtyPlates.size() + ")");
            } else {
                System.out.println("Piring ini sudah bersih!");
            }
            return;
        }
        
        // Take clean plate
        if (!cleanPlates.isEmpty() && !hasItem && !isWashing) {
            chef.getInventory().add(cleanPlates.pop());
            System.out.println("✨ Mengambil piring bersih (Tersisa: " + cleanPlates.size() + ")");
            return;
        }
        
        // washing
        if (!dirtyPlates.isEmpty() && chef.getInventory().isEmpty()) {
            if (!isWashing) {
                isWashing = true;
                currentChef = chef;
                
                if (washingProgress > 0) {
                    System.out.println("Melanjutkan mencuci piring... (Stay dekat station)");
                    System.out.println("Progres: " + (washingProgress * 100 / WASHING_TIME) + "%");
                } else {
                    System.out.println("Mulai mencuci piring... (Stay dekat station 10 detik)");
                }
            }
            return;
        }
        
        if (dirtyPlates.isEmpty() && cleanPlates.isEmpty()) {
            System.out.println("Washing station kosong!");
        } else if (!chef.getInventory().isEmpty()) {
            System.out.println("Kosongkan tangan terlebih dahulu!");
        }
    }

    @Override
    public String getType() {
        return "washing_station";
    }
    
    public int getWashingProgress() {
        return washingProgress;
    }
    
    public boolean isWashing() {
        return isWashing;
    }
    
    public int getDirtyPlateCount() {
        return dirtyPlates.size();
    }
    
    public int getCleanPlateCount() {
        return cleanPlates.size();
    }
}