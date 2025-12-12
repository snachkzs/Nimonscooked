package entity.item;
import java.io.IOException;

public class Daging extends Ingredients implements InterfaceChopable, InterfaceCookable, InterfacePlatable {
    private boolean isChopped;
    private boolean isCooked;
    private boolean isBurned;
    private int cookingTime;

    public Daging() {
        name = "Daging";
        raw = true;
        loadImage();
    }
    
    private void loadImage() {
        try {
            if (isBurned) {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/daging_masak.png")); //blm ada assetnya
            } else if (isCooked) {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/daging_masak.png"));
            } else if (isChopped) {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/daging_potong_mentah.png"));
            } else {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/daging_mentah.png"));
            }
        } catch (IOException e) {
            System.out.println("Error loading daging image: " + e.getMessage());
        }
    }
    
    @Override
    public void use() {
        System.out.println("Memegang Daging");
    }

    @Override
    public boolean isChopped() {
        return isChopped;
    }

    @Override
    public void setChopped(boolean chopped) {
        this.isChopped = chopped;
        if (chopped) {
            name = "Daging Potong";
            raw = true;
            loadImage();
        }
    }
    
    @Override
    public Item getChoppedItem() {
        this.isChopped = true;
        raw = true;
        name = "Daging Potong";
        loadImage();
        return this;
    }

    @Override
    public void setCookingTime(int time) {
        this.cookingTime = time;
    }

    @Override
    public int getCookingTime() {
        return cookingTime;
    }

    @Override
    public boolean isCooked() {
        return isCooked;
    }

    @Override
    public void setCooked(boolean cooked) {
        this.isCooked = cooked;
        if (cooked && !isBurned) {
            name = "Patty Matang";
            loadImage();
        }
    }

    @Override
    public boolean isBurned() {
        return isBurned;
    }

    @Override
    public void setBurned(boolean burned) {
        this.isBurned = burned;
        if (burned) {
            name = "Daging (BURNED)";
            loadImage();
        }
    }

    @Override
    public void cook() {
        this.isCooked = true;
        name = "Patty Matang";
        raw = false;
        loadImage();
    }
    
    public Item getCookedItem() {
        if (isChopped) {
            this.isCooked = true;
            name = "Patty Matang";
            loadImage();
            return this;
        }
        return null;
    }

    @Override
    public boolean canBePlated() {
        return isCooked || isBurned;
    }
    
    @Override
    public boolean isPlated() {
        return true;
    }
    
    @Override
    public Item getPlatedItem() {
        return this;
    }
}