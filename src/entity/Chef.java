package entity;

import input.KeyHandler;

public class Chef {

    private int x, y;
    private int speed;
    private String direction;
    private int spriteCounter = 0;
    private int spriteNum = 1;

    KeyHandler keyH;

    public Chef(KeyHandler keyH){
        this.keyH = keyH;

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void update(){

        if (keyH.upPressed == true|| keyH.downPressed == true || keyH.leftPressed == true || keyH.rightPressed == true){
            if(keyH.upPressed == true){
                direction = "up";
                y -= speed;
            }
            else if(keyH.downPressed == true){
                direction = "down";
                y += speed;
            }
            else if(keyH.leftPressed == true){
                direction = "left";
                x -= speed;
            }
            else if(keyH.rightPressed == true){
                direction = "right";
                x += speed;
            }

            spriteCounter++;
            if (spriteCounter > 12){
                if (spriteNum == 1){
                    spriteNum = 2;
                }
                else if (spriteNum == 2){
                    spriteNum = 1;
                }
                spriteCounter = 0;
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
    public int getSpriteNum() { 
        return spriteNum; 
        }
    
}