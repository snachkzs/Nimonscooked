package entity.stations;

import entity.Chef;
import entity.item.Item;
import entity.item.FryingPan;
import entity.item.Plate;
import entity.item.InterfaceCookable;

public class CookingStation extends Station {
    private FryingPan fryingPan; 

    public CookingStation(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.fryingPan = new FryingPan(); // frying pan ada di cooking station
    }

    @Override
    public void interact(Chef chef) {
        Item heldItem = chef.getInventory().isEmpty() ? null : chef.getInventory().get(0);

        // place frying pan on cooking station
        if (heldItem instanceof FryingPan) {
            if (fryingPan == null) {
                fryingPan = (FryingPan) chef.getInventory().remove(0);
                System.out.println("Menaruh Frying Pan di Cooking Station");
                
                if (fryingPan.hasIngredient() && !fryingPan.isCooking()) {
                    fryingPan.startCooking();
                }
            } else {
                System.out.println("Cooking Station sudah ada Frying Pan!");
            }
        }
        // chef takes frying pan
        else if (heldItem == null && fryingPan != null) {
            chef.getInventory().add(fryingPan);
            System.out.println("Mengambil Frying Pan dari Cooking Station");
            fryingPan = null;
        }
        // chef takes item
        else if (heldItem instanceof Plate && fryingPan != null) {
            Plate plate = (Plate) heldItem;
            fryingPan.transferToPlate(plate);
        }
        // chef transfers ingredient to frying pan
        else if (heldItem != null && fryingPan != null) {
            boolean added = fryingPan.addIngredient(heldItem);
            if (added) {
                chef.getInventory().remove(0);
                fryingPan.startCooking();
            }
        }
    }

    public void update() {
        if (fryingPan != null) {
            fryingPan.updateCooking();
        }
    }

    public boolean hasFryingPan() {
        return fryingPan != null;
    }

    public FryingPan getFryingPan() {
        return fryingPan;
    }

    @Override
    public String getType() {
        return "cooking_station";
    }
}