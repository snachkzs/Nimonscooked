package entity.stations;

import entity.Chef;
import entity.item.Plate;
import java.util.Stack;

public class WashingStation extends Station {
    private Stack<Plate> dirtyPlates = new Stack<>();
    private Stack<Plate> cleanPlates = new Stack<>();

    public void addDirtyPlate(Plate p) {
        dirtyPlates.push(p);
    }

    @Override
    public void interact(Chef chef) {
        if (chef.inventory instanceof Plate) {
            Plate p = (Plate) chef.inventory;
            if (!p.isClean()) {
                dirtyPlates.push(p);
                chef.inventory = null;
                System.out.println("Menaruh piring kotor. Total: " + dirtyPlates.size());
                return;
            }
        }
        if (!dirtyPlates.isEmpty()) {
            processWashing(chef);
            return;
        }
        if (!cleanPlates.isEmpty() && chef.inventory == null) {
            chef.inventory = cleanPlates.pop();
            System.out.println("Mengambil piring bersih dari tirisan.");
        }
    }

    private void processWashing(Chef chef) {
        new Thread(() -> {
            try {
                System.out.println("Mencuci piring... (Busy 3 detik)");
                chef.setBusy(true);
                
                Thread.sleep(3000);
                
                if (!dirtyPlates.isEmpty()) {
                    Plate p = dirtyPlates.pop();
                    p.wash();
                    cleanPlates.push(p);
                    System.out.println("Piring bersih! (Tersedia: " + cleanPlates.size() + ")");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                chef.setBusy(false);
            }
        }).start();
    }
}