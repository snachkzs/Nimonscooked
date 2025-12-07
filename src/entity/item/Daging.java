package entity.item;

public class Daging extends Ingredients implements InterfaceChopable, InterfaceCookable, InterfacePlatable {
    private boolean isChopped;
    private boolean isCooked;
    
    public Daging() {
        this.name = "Daging";
        this.raw = true;
        this.isChopped = false;
        this.isCooked = false;
    }

    @Override
    public void use() {
        System.out.println("Memegang Daging");
    }

    @Override
    public void canBeChopped() {
        if (this.raw && !this.isChopped) {
            this.isChopped = true;
            System.out.println("Daging telah dicincang.");
        } else {
            System.out.println("Daging tidak bisa dicincang lagi.");
        }
    }

    @Override
    public boolean isChopped() {
        return this.isChopped;
    }

    @Override
    public Item getChoppedItem() {
        this.isChopped = true;
        this.raw = false;
        this.name = "Daging Potong";
        return this;
    }

    @Override
    public void canBeCooked() {
        if (this.isChopped && !this.isCooked) {
            this.isCooked = true;
            System.out.println("Daging telah dimasak.");
        } else {
            System.out.println("Daging tidak bisa dimasak lagi.");
        }
    }

    @Override
    public boolean isCooked() {
        return this.isCooked;
    }

    @Override
    public Item getCookedItem() {
        if (isChopped) {
            this.isCooked = true;
            this.name = "Patty Matang";
            return this;
        }
        return null;
    }

    @Override
    public void canBePlated() {
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