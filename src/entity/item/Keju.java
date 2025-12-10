package entity.item;

public class Keju extends Ingredients implements InterfaceChopable, InterfacePlatable {
    
    private boolean isChopped = false;

    public Keju() {
        this.name = "Keju";
        this.raw = true;

        try{
            image = javax.imageio.ImageIO.read(getClass().getResourceAsStream("/ingredients/keju.jpg"));
        } catch(java.io.IOException e){
            e.printStackTrace();
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
        }
    }

    @Override
    public Item getChoppedItem() {
        this.isChopped = true;
        this.raw = false;
        this.name = "Keju Iris";
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