package entity.stations;
import entity.Chef;
import entity.item.Item;
import entity.item.Ingredients;
import entity.item.Plate;
import entity.item.FryingPan;

public class TrashStation extends Station {
    public TrashStation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void interact(Chef chef) {
        if (chef.getInventory().isEmpty()) {
            System.out.println("Tidak ada yang bisa dibuang!");
            return;
        }
        
        Item heldItem = chef.getInventory().get(0);
        
        // throw away ingredient
        if (heldItem instanceof Ingredients) {
            chef.getInventory().remove(0);
            System.out.println("Membuang " + heldItem.getName());
            return;
        }
        
        // clear plate contents
        if (heldItem instanceof Plate) {
            Plate plate = (Plate) heldItem;
            if (plate.hasIngredients()) {
                plate.clearIngredients();
                System.out.println("Mengosongkan piring");
            } else {
                System.out.println("Piring sudah kosong!");
            }
            return;
        }
        
        // clear frying pan contents
        if (heldItem instanceof FryingPan) {
            FryingPan pan = (FryingPan) heldItem;
            if (!pan.isEmpty()) {
                pan.clearContents();
                System.out.println("Mengosongkan frying pan");
            } else {
                System.out.println("Frying pan sudah kosong!");
            }
            return;
        }
    }

    @Override
    public String getType() {
        return "trash_station";
    }
}