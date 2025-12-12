package entity.item;

import java.io.IOException;

public class Roti extends Ingredients implements InterfacePlatable {
    
    public Roti() {
        this.name = "Roti";
        this.raw = true;

        try {
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/roti_burger.png"));
        } catch (IOException e) {
            System.out.println("Error loading roti image: " + e.getMessage());
        }
    }

    @Override
    public void use() {
        System.out.println("Memegang Roti");
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
