package entity;

import input.KeyHandler;
import main.GamePanel;
import java.awt.Rectangle;
import entity.item.Item;

public class Chef {

    private int x, y;
    private int speed;
    private String direction;
    private boolean isMoving = false;
    public Rectangle collisionArea;
    public boolean collisionOn = false;
    GamePanel gp;

    KeyHandler keyH;
    public Item inventory;

    public Chef(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        x = 150;
        y = 150;
        speed = 4;
        direction = "down";

        collisionArea = new Rectangle(8, 16, 32, 32);
    }

    public void update(){

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
            }
        }
        else {
            isMoving = false;
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
    
    public void setX(int x) { 
        this.x = x; 
        }
    public void setY(int y) { 
        this.y = y; 
        }
    
}