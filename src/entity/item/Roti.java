package entity.item;

public class Roti extends Ingredients implements InterfacePlatable {
    
    public Roti() {
        this.name = "Roti";
        this.raw = true;
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
