package entity.item;

public class FryingPan extends KitchenUtensil implements CookingDevice {

    private int capacity;
    private boolean isCooking = false;
    private int cookingProgress = 0;
    private final int COOKING_TIME = 720; // 12 seconds at 60 FPS

    public FryingPan() {
        super("Frying Pan");
        this.capacity = 1;
        this.collision = true;
    }

    @Override
    public boolean isPortable() {
        return true;
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public boolean canAccept(Item ingredient) {
        return this.contents.isEmpty() && ingredient instanceof InterfaceCookable;
    }

    @Override
    public boolean addIngredient(Item ingredient) {
        if (!this.contents.isEmpty()) {
            System.out.println("Frying Pan penuh!");
            return false;
        }
        
        if (!(ingredient instanceof InterfaceCookable)) {
            System.out.println(ingredient.getName() + " tidak bisa dimasak!");
            return false;
        }
        
        if (ingredient instanceof Daging) {
            Daging daging = (Daging) ingredient;
            if (!daging.isChopped()) {
                System.out.println("Daging harus dipotong dulu sebelum dimasak!");
                return false;
            }
        }
        
        this.contents.add(ingredient);
        System.out.println("Menambahkan " + ingredient.getName() + " ke Frying Pan");
        return true;
    }

    @Override
    public void startCooking() {
        if (!this.contents.isEmpty() && !isCooking) {
            isCooking = true;
            cookingProgress = 0;
            System.out.println("Frying Pan mulai memasak...");
        }
    }

    public void updateCooking() {
        if (isCooking && !contents.isEmpty()) {
            cookingProgress++;
            
            Item ingredient = contents.get(0);
            if (ingredient instanceof InterfaceCookable) {
                InterfaceCookable cookable = (InterfaceCookable) ingredient;
                
                if (cookingProgress >= COOKING_TIME && !cookable.isCooked()) {
                    // Selesai masak
                    cookable.setCooked(true);
                    isCooking = false;
                    System.out.println(ingredient.getName() + " sudah matang!");
                } else if (cookingProgress >= COOKING_TIME * 2 && !cookable.isBurned()) {
                    // BURNED
                    cookable.setBurned(true);
                    isCooking = false;
                    System.out.println(ingredient.getName() + " GOSONG!");

                }
            }
        }
    }

    public boolean transferToPlate(Plate plate) {
        if (contents.isEmpty()) {
            System.out.println("Frying Pan kosong!");
            return false;
        }

        Item ingredient = contents.get(0);
        if (ingredient instanceof InterfaceCookable) {
            InterfaceCookable cookable = (InterfaceCookable) ingredient;
            
            if (cookable.isBurned()) {
                System.out.println("Ingredient sudah gosong! Tidak bisa dipindahkan ke plate. Buang ke trash!");
                return false;
            }
            
            if (cookable.isCooked()) {
                Item item = (Item) contents.remove(0);
                boolean added = plate.addIngredient(item);
                
                if (added) {
                    System.out.println("Memindahkan " + item.getName() + " dari Frying Pan ke Plate");
                    cookingProgress = 0;
                    isCooking = false;
                    return true;
                } else {
                    contents.add(0, ingredient);
                    System.out.println("Gagal memindahkan ke plate!");
                    return false;
                }
            } else {
                System.out.println("Ingredient belum matang! Tunggu sampai selesai memasak.");
                return false;
            }
        }
        
        return false;
    }

    public boolean hasReadyIngredient() {
        if (!contents.isEmpty()) {
            Item ingredient = contents.get(0);
            if (ingredient instanceof InterfaceCookable) {
                InterfaceCookable cookable = (InterfaceCookable) ingredient;
                return cookable.isCooked() && !cookable.isBurned();
            }
        }
        return false;
    }

    public boolean isCooking() {
        return isCooking;
    }

    public int getCookingProgress() {
        return cookingProgress;
    }

    public boolean hasIngredient() {
        return !contents.isEmpty();
    }
    
    public boolean isEmpty() {
        return contents.isEmpty();
    }
    
    public void clearContents() {
        contents.clear();
        isCooking = false;
        cookingProgress = 0;
    }

    @Override
    public String getAssetPath() {
        return "/assets/kitchen-utensil/frying_pan.png";
    }
}