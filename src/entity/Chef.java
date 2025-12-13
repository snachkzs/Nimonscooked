package entity;

import controller.CollisionChecker;
import utils.KeyHandler;
import main.GamePanel;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.*;
import entity.item.Item;
import entity.item.Ingredients;

public class Chef implements Runnable {
    private String id;
    private String name;
    private volatile int x, y;  
    private int speed;
    private volatile String direction;
    private volatile boolean isMoving = false;
    public Rectangle collisionArea;
    public volatile boolean collisionOn = false;
    public int collisionAreaDefaultX, collisionAreaDefaultY;
    GamePanel gp;
    public ArrayList<Item> inventory = new ArrayList<>();
    public final int INVENTORY_SIZE = 1;

    KeyHandler keyH;
    
    private volatile boolean running = false;
    private volatile boolean paused = false;
    private Thread chefThread;

    private boolean isActive = false;
    private boolean isBusy = false;
    private String currentAction = "";

    private ExecutorService taskScheduler;
    private Future<?> currentTask;

    private int pickUpDropCooldown = 0;
    private final int PICK_UP_DROP_DELAY = 15;
    private boolean isDashing = false;
    private int dashCounter = 0;
    private int dashCooldown = 0;
    private final int DASH_DURATION = 15; 
    private final int DASH_COOLDOWN_TIME = 120;
    private final int BASE_SPEED = 4;
    private final int DASH_SPEED = 12;
    
    private int spriteCounter = 0;
    private int spriteNum = 1;

    public Chef(GamePanel gp, KeyHandler keyH, String id, String name){
        this.gp = gp;
        this.keyH = keyH;
        this.x = 0;
        this.y = 0;
        this.id = id;
        this.name = name;

        speed = BASE_SPEED;
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
        this(gp, keyH, "C0", "Chef");
        this.x = x;
        this.y = y;
    }
    
    public void startThread() {
        if (chefThread == null || !chefThread.isAlive()) {
            running = true;
            paused = false;
            chefThread = new Thread(this, "Chef-" + id);
            chefThread.setDaemon(true);
            chefThread.start();
            System.out.println(name + " thread started");
        }
    }

    public void stopThread() {
        running = false;
        if (chefThread != null) {
            try { chefThread.join(1000); } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }
    
    public void pause() { paused = true; }
    public void resume() { paused = false; }
    
    @Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000.0 / 60.0;
        double delta = 0;
        while (running) {
            if (paused) { try { Thread.sleep(100); } catch (InterruptedException e) {} continue; }
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (delta >= 1) { update(); delta--; }
            try { Thread.sleep(1); } catch (InterruptedException e) {}
        }
    }

    public void setActive(boolean active) {
        this.isActive = active;
        this.isMoving = false;
    }

    public boolean isActive() { return isActive; }
    public String getCurrentAction() { return currentAction; }

    public void update(){
        if (isBusy) {
            isMoving = false;
            if (currentTask != null && currentTask.isDone()) {
                isBusy = false;
                currentAction = "";
            }
            return;
        }
        
        if (!isActive) return;
        
        // cooldown
        if (pickUpDropCooldown > 0) pickUpDropCooldown--;
        if (dashCooldown > 0) dashCooldown--;
        
        // logic dash
        if (isDashing) {
            dashCounter--;
            if (dashCounter <= 0) {
                isDashing = false;
                speed = BASE_SPEED;
                dashCooldown = DASH_COOLDOWN_TIME;
                System.out.println("Dash ended");
            }
        } else if (keyH.dashPressed && dashCooldown == 0) {
            isDashing = true;
            speed = DASH_SPEED;
            dashCounter = DASH_DURATION;
            System.out.println("DASH!");
        }

        if (keyH.pickUpDrop && pickUpDropCooldown == 0) {
            if (!interactWithNearbyStation()) {
                int itemIndex = gp.collisionChecker.checkItem(this);
                pickUpDrop(itemIndex);
            }
            pickUpDropCooldown = PICK_UP_DROP_DELAY;
        }
        
        // chop dan throw
        if (keyH.chopThrow && pickUpDropCooldown == 0) {
            boolean chopping = chopAtNearbyStation();
            if (!chopping && !inventory.isEmpty()) {
                throwItem();
            }
            pickUpDropCooldown = PICK_UP_DROP_DELAY;
        }
    
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
            isMoving = true;
            if(keyH.upPressed){ direction = "up"; }
            else if(keyH.downPressed){ direction = "down"; }
            else if(keyH.leftPressed){ direction = "left"; }
            else if(keyH.rightPressed){ direction = "right"; }

            collisionOn = false;
            gp.collisionChecker.checkTile(this);
            gp.collisionChecker.checkChef(this);

            if (!collisionOn){
                switch(direction){
                    case "up": y -= speed; break;
                    case "down": y += speed; break;
                    case "left": x -= speed; break;
                    case "right": x += speed; break;
                }
            }
        } else {
            isMoving = false;
        }
        
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > (isDashing ? 5 : 12)) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        } else {
            spriteNum = 1;
            spriteCounter = 0;
        }
    }
    
    // method throw
    public void throwItem() {
        if (inventory.isEmpty()) return;
        
        Item itemToThrow = inventory.remove(0);
        
        Projectile p = gp.collisionChecker.calculateThrow(this, itemToThrow);
        
        if (p != null) {
            gp.addProjectile(p);
            System.out.println("Melempar " + itemToThrow.getName());
        } else {
            inventory.add(itemToThrow);
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
                        gp.itemList[j] = droppedItem; break;
                    }
                }
            }
        } else if (i == 999 && !inventory.isEmpty()) {
            Item droppedItem = inventory.remove(0);
            droppedItem.setPosition(this.x, this.y);
            for (int j = 0; j < gp.itemList.length; j++) {
                if (gp.itemList[j] == null) {
                    gp.itemList[j] = droppedItem; break;
                }
            }
        }
    }
    
    private entity.stations.Station findNearbyStation() {
        int interactionRange = gp.tileSize; 
        entity.stations.Station closestStation = null;
        int minDistance = Integer.MAX_VALUE;
        for (int i = 0; i < gp.stationList.length; i++) {
            if (gp.stationList[i] != null) {
                int stationX = gp.stationList[i].x;
                int stationY = gp.stationList[i].y;
                boolean isInFront = false;
                int distance = 0;
                switch(direction) {
                    case "up": isInFront = (stationY < this.y) && (Math.abs(stationX - this.x) <= gp.tileSize/2); distance = this.y - stationY; break;
                    case "down": isInFront = (stationY > this.y) && (Math.abs(stationX - this.x) <= gp.tileSize/2); distance = stationY - this.y; break;
                    case "left": isInFront = (stationX < this.x) && (Math.abs(stationY - this.y) <= gp.tileSize/2); distance = this.x - stationX; break;
                    case "right": isInFront = (stationX > this.x) && (Math.abs(stationY - this.y) <= gp.tileSize/2); distance = stationX - this.x; break;
                }
                if (isInFront && distance <= interactionRange && distance < minDistance) {
                    closestStation = gp.stationList[i];
                    minDistance = distance;
                }
            }
        }
        return closestStation;
    }
    
    public boolean interactWithNearbyStation() {
        entity.stations.Station station = findNearbyStation();
        if (station != null) {
            if (station.tryLock(this)) {
                try {
                    System.out.println("=== [" + name + "] Interacting with " + station.getType() + " ===");
                    station.interact(this);
                    return true;
                } finally {
                    station.unlock(this);
                }
            } else {
                System.out.println("WARNING: " + name + ": Station sedang dipakai oleh " + station.getInteractingChef().getName());
                return false;
            }
        }
        return false;
    }
    
    public boolean chopAtNearbyStation() {
        entity.stations.Station station = findNearbyStation();
        if (station != null && station.getType().equals("cutting_station")) {
            System.out.println("=== [V] Chopping at cutting station ===");
            entity.stations.CuttingStation cuttingStation = (entity.stations.CuttingStation) station;
            cuttingStation.startCutting(this);
            return true;
        }
        return false;
    }

    public void setBusy(boolean busy){ 
        this.isBusy = busy; 
    }
    public boolean isBusy(){ 
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
    public void clearInventory() { 
        inventory.clear(); 
    }
}