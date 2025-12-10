package entity.item;

public class Lettuce extends Ingredients implements InterfaceChopable, InterfacePlatable {
    
    private boolean isChopped = false;

    public Lettuce() {
        this.name = "Lettuce";
        this.raw = true;
    }

    @Override
    public void use() { System.out.println("Memegang Lettuce"); }

    @Override
    public boolean isChopped() {
        return isChopped;
    }

    @Override
    public void setChopped(boolean chopped) {
        this.isChopped = chopped;
        if (chopped) {
            this.raw = false;
            this.name = "Lettuce potong";
        }
    }

    @Override
    public Item getChoppedItem() {
        this.isChopped = true;
        this.raw = false;
        this.name = "Lettuce potong";
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