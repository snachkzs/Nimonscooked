package entity;

import controller.CollisionChecker;
import input.KeyHandler;
import main.GamePanel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.*;
import entity.item.Item;
import entity.item.Ingredients;


public class Chef {

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
    private final int PICK_UP_DROP_DELAY = 15; // 15 frames (~0.25 seconds at 60 FPS)

    public Chef(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;
        this.x = 0;
        this.y = 0;

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

    public boolean isBusy() {
        return isBusy;
    }

    public String getCurrentAction() {
        return currentAction;
    }

    public void update(){
        if(isActive && !isBusy){
            isMoving = false;
            
            // Decrement cooldown
            if (pickUpDropCooldown > 0) {
                pickUpDropCooldown--;
            }
            
            // Check item collision and handle pick up/drop
            int itemIndex = gp.collisionChecker.checkItem(this);
            if (keyH.pickUpDrop && pickUpDropCooldown == 0) {
                pickUpDrop(itemIndex);
                pickUpDropCooldown = PICK_UP_DROP_DELAY;
            }
        
            if (keyH.upPressed == true|| keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
                isMoving = true;
                if(keyH.upPressed == true){
                    direction = "up";
                }
                else if(keyH.downPressed == true){
                    direction = "down";
                }
                else if(keyH.leftPressed == true){
                    direction = "left";
                }
                else if(keyH.rightPressed == true){
                    direction = "right";
                }

                collisionOn = false;
                gp.collisionChecker.checkTile(this);

                if (collisionOn == false){
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
                    isMoving = true;
                }
            }
        }

        if (isBusy) {
            if (currentTask != null && currentTask.isDone()) {
                isBusy = false;
                currentAction = "";
            }
        }
    }

    // public void performAction(Runnable action, String actionName) {
    //     if (!isBusy) {
    //         isBusy = true;
    //         currentAction = actionName;
    //         currentTask = taskScheduler.submit(action);
    //     }
    // }

    // public void shutdownTaskScheduler() {
    //     taskScheduler.shutdown();
    // }

    public void pickUpDrop(int i){
        if (i != 999){
            if (inventory.isEmpty()) {
                // Pick up item if inventory is empty
                inventory.add((Item)gp.itemList[i]);
                gp.itemList[i] = null;
            } else if (inventory.size() >= INVENTORY_SIZE) {
                // Drop item if inventory is full
                Item droppedItem = inventory.remove(0);
                droppedItem.setPosition(this.x, this.y);
                
                // Find empty slot in itemList to place the dropped item
                for (int j = 0; j < gp.itemList.length; j++) {
                    if (gp.itemList[j] == null) {
                        gp.itemList[j] = droppedItem;
                        break;
                    }
                }
            }
        } else if (i == 999 && !inventory.isEmpty()) {
            // Drop item when pressing E without any item nearby
            Item droppedItem = inventory.remove(0);
            droppedItem.setPosition(this.x, this.y);
            
            // Find empty slot in itemList to place the dropped item
            for (int j = 0; j < gp.itemList.length; j++) {
                if (gp.itemList[j] == null) {
                    gp.itemList[j] = droppedItem;
                    break;
                }
            }
        }
    }
    
    public int getX() { 
        return x; 
        }
    public int getY() { 
        return y; 
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
    
    public ArrayList<Item> getInventory() {
        return (ArrayList<Item>) inventory;
    }
    
}