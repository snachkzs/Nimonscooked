package entity.item;

import java.awt.image.BufferedImage;

public abstract class Item {

    public BufferedImage image;
    public String name; 
    public boolean collision;
    public boolean collisionArea;
    public int x, y;
    protected boolean stackable;

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