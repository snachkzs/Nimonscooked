package entity.item;

public class FryingPan extends KitchenUtensil implements CookingDevice {

    private int capacity;

    public FryingPan() {
        super("Frying Pan");
        this.capacity = 1;
    }

    // implementasi dari interface CookingDevice

    @Override
    public boolean isPortable() {
        // frying Pan bisa dibawa-bawa
        return true;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public boolean canAccept(Preparable ingredient) {
        // hanya terima bahan jika wajan kosong dan bahan itu bisa dimasak
        if (this.contents.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public void addIngredient(Preparable ingredient) {
        if (canAccept(ingredient)) {
            this.contents.add(ingredient);
            System.out.println("Ingredient added to Frying Pan.");
        } else {
            System.out.println("Frying Pan is full or cannot accept this item!");
        }
    }

    @Override
    public void startCooking() {
        // logika memasak nanti di sini
        if (!this.contents.isEmpty()) {
            System.out.println("Frying Pan starts cooking...");
            // nanti di sini panggil method cook
        }
    }

    @Override
    public String getAssetPath() {
        return "/assets/kitchen-utensil/frying_pan.png";
    }
    
}