package entity;

import entity.item.Item;
import main.GamePanel;
import java.awt.Graphics2D;

public class Projectile {
    private GamePanel gp;
    private Item item;
    private double currentX, currentY;
    private int targetX, targetY;
    private Chef targetChef; 
    private int speed = 12;
    private boolean active = true;

    public Projectile(GamePanel gp, Item item, int startX, int startY, int targetX, int targetY, Chef targetChef) {
        this.gp = gp;
        this.item = item;
        this.currentX = startX;
        this.currentY = startY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.targetChef = targetChef;
    }

    public void update() {
        if (!active) return;

        double dx = targetX - currentX;
        double dy = targetY - currentY;
        double distance = Math.sqrt(dx*dx + dy*dy);

        if (distance <= speed) {
            arrive();
        } else {
            double moveX = (dx / distance) * speed;
            double moveY = (dy / distance) * speed;
            currentX += moveX;
            currentY += moveY;
        }
    }

    private void arrive() {
        active = false;
        
        if (targetChef != null) {
            if (targetChef.getInventory().isEmpty()) {
                targetChef.getInventory().add(item);
                System.out.println("CATCH! " + targetChef.getName() + " menangkap " + item.getName());
            } else {
                dropOnFloor(targetChef.getX(), targetChef.getY());
            }
        } else {
            dropOnFloor(targetX, targetY);
        }
    }

    private void dropOnFloor(int x, int y) {
        int gridX = (x + gp.tileSize/2) / gp.tileSize * gp.tileSize;
        int gridY = (y + gp.tileSize/2) / gp.tileSize * gp.tileSize;
        
        item.setPosition(gridX, gridY);
        for (int i = 0; i < gp.itemList.length; i++) {
            if (gp.itemList[i] == null) {
                gp.itemList[i] = item;
                System.out.println("Item mendarat di (" + gridX + "," + gridY + ")");
                break;
            }
        }
    }

    public void draw(Graphics2D g2) {
        if (item != null && item.getImage() != null) {
            g2.drawImage(item.getImage(), (int)currentX, (int)currentY - 10, gp.tileSize, gp.tileSize, null);
        }
    }

    public boolean isActive() {
        return active;
    }
}