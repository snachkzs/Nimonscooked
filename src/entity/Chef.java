package entity;

import controller.CollisionChecker;
import utils.KeyHandler;
import main.GamePanel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.*;
import entity.item.Item;
import entity.item.Ingredients;


public class Chef {
    private String id;
    private String name;
    private int x, y;
    private int speed;
    private String direction;
    private boolean isMoving = false;
    public Rectangle collisionArea;
    public boolean collisionOn = false;
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    GamePanel gp;
    public ArrayList<Item> inventory = new ArrayList<>();
    public final int INVENTORY_SIZE = 1;

    KeyHandler keyH;

    private boolean isActive = false;
    private boolean isBusy = false;
    private String currentAction = "";

    private ExecutorService taskScheduler;
    private Future<?> currentTask;
    
    private int pickUpDropCooldown = 0;
    private final int PICK_UP_DROP_DELAY = 15;
    
    // Sprite animation
    private int spriteCounter = 0;
    private int spriteNum = 1;

    public Chef(GamePanel gp, KeyHandler keyH, String id, String name){
        this.gp = gp;
        this.keyH = keyH;
        this.x = 0;
        this.y = 0;
        this.id = id;
        this.name = name;

        speed = 4;
        direction = "down";

        collisionArea = new Rectangle();
        collisionArea.x = 8;
        collisionArea.y = 16;
        collisionArea.width = 32;
        collisionArea.height = 32;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        taskScheduler = Executors.newSingleThreadExecutor();
    }

    public Chef(GamePanel gp, KeyHandler keyH, int x, int y){
        this.gp = gp;
        this.keyH = keyH;
        this.x = x;
        this.y = y;

        speed = 4;
        direction = "down";

        collisionArea = new Rectangle();
        collisionArea.x = 8;
        collisionArea.y = 16;
        collisionArea.width = 32;
        collisionArea.height = 32;
        collisionAreaDefaultX = collisionArea.x;
        collisionAreaDefaultY = collisionArea.y;

        taskScheduler = Executors.newSingleThreadExecutor();
    }

    public void setActive(boolean active) {
        this.isActive = active;
        this.isMoving = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void update(){
        if (isBusy) {
            isMoving = false;
            if (currentTask != null && currentTask.isDone()) {
                isBusy = false;
                currentAction = "";
            }
            return;
        }
        
        if (isActive) {
            // cooldown pickUp/drop
            if (pickUpDropCooldown > 0) {
                pickUpDropCooldown--;
            }
            
            // pick up/drop item
            if (keyH.pickUpDrop && pickUpDropCooldown == 0) {
                if (!interactWithNearbyStation()) {
                    int itemIndex = gp.collisionChecker.checkItem(this);
                    pickUpDrop(itemIndex);
                }
                pickUpDropCooldown = PICK_UP_DROP_DELAY;
            }
            
            if (keyH.chopThrow && pickUpDropCooldown == 0) {
                chopAtNearbyStation();
                pickUpDropCooldown = PICK_UP_DROP_DELAY;
            }
        
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
                isMoving = true;
                if(keyH.upPressed){
                    direction = "up";
                }
                else if(keyH.downPressed){
                    direction = "down";
                }
                else if(keyH.leftPressed){
                    direction = "left";
                }
                else if(keyH.rightPressed){
                    direction = "right";
                }

                collisionOn = false;
                gp.collisionChecker.checkTile(this);

                if (!collisionOn){
                    switch(direction){
                        case "up":
                            y -= speed;
                            break;
                        case "down":
                            y += speed;
                            break;
                        case "left":
                            x -= speed;
                            break;
                        case "right":
                            x += speed;
                            break;
                    }
                    }
                }
            } else {
                isMoving = false;
            }
            
            // Update sprite animation
            if (isMoving) {
                spriteCounter++;
                if (spriteCounter > 12) {
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                    spriteCounter = 0;
                }
            } else {
                spriteNum = 1;
                spriteCounter = 0;
            }
        }
    

    public void pickUpDrop(int i){
        if (i != 999){
            if (inventory.isEmpty()) {
                inventory.add((Item)gp.itemList[i]);
                gp.itemList[i] = null;
            } else if (inventory.size() >= INVENTORY_SIZE) {
                Item droppedItem = inventory.remove(0);
                droppedItem.setPosition(this.x, this.y);
                
                for (int j = 0; j < gp.itemList.length; j++) {
                    if (gp.itemList[j] == null) {
                        gp.itemList[j] = droppedItem;
                        break;
                    }
                }
            }
        } else if (i == 999 && !inventory.isEmpty()) {
            Item droppedItem = inventory.remove(0);
            droppedItem.setPosition(this.x, this.y);
            
            for (int j = 0; j < gp.itemList.length; j++) {
                if (gp.itemList[j] == null) {
                    gp.itemList[j] = droppedItem;
                    break;
                }
            }
        }
    }
    
    public boolean interactWithNearbyStation() {
        int interactionRange = gp.tileSize;
        
        for (int i = 0; i < gp.stationList.length; i++) {
            if (gp.stationList[i] != null) {
                int stationX = gp.stationList[i].x;
                int stationY = gp.stationList[i].y;
                
                // cek jarak station
                int distanceX = Math.abs(this.x - stationX);
                int distanceY = Math.abs(this.y - stationY);
                
                if (distanceX <= interactionRange && distanceY <= interactionRange) {
                    System.out.println("=== [C] Interacting with " + gp.stationList[i].getType() + " ===");
                    gp.stationList[i].interact(this);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public void chopAtNearbyStation() {
        int interactionRange = gp.tileSize;
        
        for (int i = 0; i < gp.stationList.length; i++) {
            if (gp.stationList[i] != null) {
                int stationX = gp.stationList[i].x;
                int stationY = gp.stationList[i].y;
                
                int distanceX = Math.abs(this.x - stationX);
                int distanceY = Math.abs(this.y - stationY);
                
                if (distanceX <= interactionRange && distanceY <= interactionRange) {
                    // cek cutting station
                    if (gp.stationList[i].getType().equals("cutting_station")) {
                        System.out.println("=== [V] Chopping at cutting station ===");
                        entity.stations.CuttingStation cuttingStation = (entity.stations.CuttingStation) gp.stationList[i];
                        cuttingStation.startCutting(this);
                        return;
                    }
                }
            }
        }
        
        System.out.println("Tidak ada cutting station di sekitar");
    }

    public void setBusy(boolean busy) {
        this.isBusy = busy;
    }

    public boolean isBusy() {
        return isBusy;
    }
    
    public String getId() { 
        return id; 
    }
    
    public String getName() { 
        return name; 
    }
    
    public int getX() { 
        return x; 
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() { 
        return y; 
    }
    
    public void setY(int y) {
        this.y = y;
    }
    
    public String getDirection() { 
        return direction; 
    }
    
    public int getSpeed() { 
        return speed; 
    }
    
    public boolean isMoving() {
        return isMoving;
    }
    
    public int getSpriteNum() {
        return spriteNum;
    }
    
    public ArrayList<Item> getInventory() {
        return inventory;
    }
}