package entity.stations;

import entity.Chef;
import entity.item.Item;
import entity.item.Plate;
import entity.item.FryingPan;

public class AssemblyStation extends Station {
    private Item storedItem;

    public AssemblyStation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void interact(Chef chef) {
        Item heldItem = chef.getInventory().isEmpty() ? null : chef.getInventory().get(0);

        // transfer cooked ingredients to held plate 
        if (heldItem instanceof Plate && storedItem instanceof FryingPan) {
            Plate plate = (Plate) heldItem;
            FryingPan fryingPan = (FryingPan) storedItem;
            fryingPan.transferToPlate(plate);
        }
        // transfer cooked ingredients to plate on station
        else if (heldItem instanceof FryingPan && storedItem instanceof Plate) {
            FryingPan fryingPan = (FryingPan) heldItem;
            Plate plate = (Plate) storedItem;
            fryingPan.transferToPlate(plate);
        }
        // add ingredient from station to held plate
        else if (heldItem instanceof Plate && storedItem != null && !(storedItem instanceof Plate) && !(storedItem instanceof FryingPan)) {
            Plate plate = (Plate) heldItem;
            if (plate.addIngredient(storedItem)) {
                storedItem = null;
            }
        }
        // add ingredient from held item to plate on station
        else if (heldItem != null && !(heldItem instanceof Plate) && !(heldItem instanceof FryingPan) && storedItem instanceof Plate) {
            Plate plate = (Plate) storedItem;
            if (plate.addIngredient(heldItem)) {
                chef.getInventory().remove(0);
            }
        }
        // take item from station
        else if (heldItem == null && storedItem != null) {
            chef.getInventory().add(storedItem);
            System.out.println("Mengambil " + storedItem.getName() + " dari assembly station");
            storedItem = null;
        }
        // place item on empty station
        else if (heldItem != null && storedItem == null) {
            storedItem = chef.getInventory().remove(0);
            System.out.println("Menaruh " + storedItem.getName() + " di assembly station");
        }
        // item stays in inventory
        else if (heldItem != null && storedItem != null) {
            System.out.println("Station sudah ada item!");
        }
    }

    @Override
    public String getType() {
        return "assembly_station";
    }
}