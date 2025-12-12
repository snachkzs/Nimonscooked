package entity.item;

import java.io.IOException;

public class Keju extends Ingredients implements InterfaceChopable, InterfacePlatable {
    
    private boolean isChopped = false;

    public Keju() {
        this.name = "Keju";
        this.raw = true;
        loadImage();
    }

    private void loadImage() {
        try {
            if (isChopped) {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/keju_potong.png"));
            } else {
                image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/keju.png"));
            }
        } catch (IOException e) {
            System.out.println("Error loading keju image: " + e.getMessage());
        }
    }  

    @Override
    public void use() { System.out.println("Memegang Keju"); }

    @Override
    public boolean isChopped() {
        return isChopped;
    }

    @Override
    public void setChopped(boolean chopped) {
        this.isChopped = chopped;
        if (chopped) {
            this.raw = false;
            this.name = "Keju Iris";
            loadImage();
        }
    }

    @Override
    public Item getChoppedItem() {
        this.isChopped = true;
        this.raw = false;
        this.name = "Keju Iris";
        loadImage();
        return this;
    }
    
    @Override
    public boolean canBePlated() {
        return true;
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