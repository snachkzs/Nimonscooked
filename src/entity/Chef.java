package entity;

import main.GamePanel;
import input.KeyHandler;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class Chef {

    public int x, y;
    public int speed;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;
    public int spriteCounter = 0;
    public int spriteNum = 1;

    GamePanel gp;
    KeyHandler keyH;

    public Chef(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        x = 100;
        y = 100;
        speed = 4;
        direction = "down";

        getPlayerImage();

    }

    public void getPlayerImage(){

        try{

            up1 = ImageIO.read(getClass().getResourceAsStream("/chef/up1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/chef/up2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/chef/down1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/chef/down2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/chef/left1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/chef/left2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/chef/right1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/chef/right2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    public void draw(Graphics2D g2){
        BufferedImage image = null;

        switch(direction){
            case "up":
                if (spriteNum == 1){
                    image = up1;
                }
                if (spriteNum == 2){
                    image = up2;
                }
                break;
            case "down":
                if (spriteNum == 1){
                    image = down1;
                }
                if (spriteNum == 2){
                    image = down2;
                }
                break;
            case "left":
                if (spriteNum == 1){
                    image = left1;
                }
                if (spriteNum == 2){
                    image = left2;
                }
                break;
            case "right":
                if (spriteNum == 1){
                    image = right1;
                }
                if (spriteNum == 2){
                    image = right2;
                }
                break;
        }

        g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
    }

    
}