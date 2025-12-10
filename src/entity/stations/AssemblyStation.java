package entity.stations;
import entity.Chef;
import entity.item.Item;

public class AssemblyStation extends Station {
    private Item storedItem;

    public AssemblyStation(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void interact(Chef chef) {
        if (storedItem == null && !chef.getInventory().isEmpty()) {
            this.storedItem = chef.getInventory().remove(0);
            System.out.println("Menaruh item di assembly station");
        } else if (storedItem != null && chef.getInventory().isEmpty()) {
            chef.getInventory().add(this.storedItem);
            System.out.println("Mengambil item dari assembly station");
            this.storedItem = null;
        }
    }

    @Override
    public String getType() {
        return "assembly_station";
    }
}