package entity.item;

public class Plate extends KitchenUtensil {    
    private boolean isClean;

    public Plate() {
        super("Plate"); 
        this.isClean = true; 
    }

    public boolean isClean() {
        return isClean;
    }

    public void wash() {
        this.isClean = true;
        System.out.println("Plate is now clean.");
    }

    public void dirty() {
        this.isClean = false;
        this.contents.clear();
        System.out.println("Plate is now dirty.");
    }

    @Override
    public String getAssetPath() {
        if (isClean) return "/assets/kitchen-utensil/plate_clean.png";
        else return "/assets/kitchen-utensil/plate_dirty.png";
    }
}