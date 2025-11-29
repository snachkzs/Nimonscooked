package view;

import entity.Chef;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ChefView {

    private BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;

    public ChefView(){
        loadSprites();
    }
    
    private void loadSprites(){
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
    
    public void render(Graphics2D g2, Chef chef, int tileSize){
        BufferedImage image = getSprite(chef.getDirection(), chef.getSpriteNum());
        g2.drawImage(image, chef.getX(), chef.getY(), tileSize, tileSize, null);
    }
    
    private BufferedImage getSprite(String direction, int spriteNum){
        switch(direction){
            case "up":
                return spriteNum == 1 ? up1 : up2;
            case "down":
                return spriteNum == 1 ? down1 : down2;
            case "left":
                return spriteNum == 1 ? left1 : left2;
            case "right":
                return spriteNum == 1 ? right1 : right2;
            default:
                return down1;
        }
    }
    
}