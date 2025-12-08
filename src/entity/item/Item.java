package entity.item;

import java.awt.image.BufferedImage;
import java.awt.Rectangle;

public abstract class Item {

    public BufferedImage image;
    public String name; 
    public boolean collision;
    public int x, y;
    protected boolean stackable;
    public Rectangle collisionArea = new Rectangle(0, 0, 48, 48); //buat tiap item nnt di set aja collisionnya
    public int collisionAreaDefaultX = 0, collisionAreaDefaultY = 0;

    public abstract String getType();
    public abstract void use();

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isStackable() {
        return stackable;
    }

    public boolean hasCollision() {
        return collision;
    }
}