package entity.item;

public class Tomat extends Ingredients implements InterfaceChopable, InterfacePlatable {
    
    private boolean isChopped = false;

    public Tomat() {
        this.name = "Tomat";
        this.raw = true;
    }

    @Override
    public void use() { System.out.println("Memegang Tomat"); }

    @Override
    public boolean isChopped() {
        return isChopped;
    }

    @Override
    public void setChopped(boolean chopped) {
        this.isChopped = chopped;
        if (chopped) {
            this.raw = false;
            this.name = "Tomat potong";
        }
    }

    @Override
    public Item getChoppedItem() {
        this.isChopped = true;
        this.raw = false;
        this.name = "Tomat potong";
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