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
        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/dagingmentah.png"));
        } catch (IOException e) {
            e.printStackTrace();
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
            raw = false;
        }
    }
    
    @Override
    public Item getChoppedItem() {
        this.isChopped = true;
        raw = false;
        name = "Daging Potong";
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
            name = "Daging (Cooked)";
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
        }
    }

    @Override
    public void cook() {
        this.isCooked = true;
        name = "Patty Matang";
    }
    
    public Item getCookedItem() {
        if (isChopped) {
            this.isCooked = true;
            name = "Patty Matang";
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