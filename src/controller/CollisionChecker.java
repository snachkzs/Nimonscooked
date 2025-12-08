package controller;

import main.GamePanel;
import entity.Chef;

public class CollisionChecker {
    
    GamePanel gp;

    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    public void checkTile(Chef chef){
        int chefLeftX = chef.getX() + chef.collisionArea.x;
        int chefRightX = chef.getX() + chef.collisionArea.x + chef.collisionArea.width;
        int chefTopY = chef.getY() + chef.collisionArea.y;
        int chefBottomY = chef.getY() + chef.collisionArea.y + chef.collisionArea.height;

        int chefLeftCol = chefLeftX / gp.tileSize;
        int chefRightCol = chefRightX / gp.tileSize;
        int chefTopRow = chefTopY / gp.tileSize;
        int chefBottomRow = chefBottomY / gp.tileSize;

        int tileNum1, tileNum2;

        switch(chef.getDirection()){
            case "up":
                chefTopRow = (chefTopY - chef.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[chefLeftCol][chefTopRow];
                tileNum2 = gp.tileM.mapTileNum[chefRightCol][chefTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    chef.collisionOn = true;
                }
                break;
            case "down":
                chefBottomRow = (chefBottomY + chef.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[chefLeftCol][chefBottomRow];
                tileNum2 = gp.tileM.mapTileNum[chefRightCol][chefBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    chef.collisionOn = true;
                }
                break;
            case "left":
                chefLeftCol = (chefLeftX - chef.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[chefLeftCol][chefTopRow];
                tileNum2 = gp.tileM.mapTileNum[chefLeftCol][chefBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    chef.collisionOn = true;
                }
                break;
            case "right":
                chefRightCol = (chefRightX + chef.getSpeed()) / gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[chefRightCol][chefTopRow];
                tileNum2 = gp.tileM.mapTileNum[chefRightCol][chefBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    chef.collisionOn = true;
                }
                break;
        }
    }

    public int checkItem(Chef chef){
        int index = 999;

        for (int i = 0; i < gp.itemList.length; i++){
            if (gp.itemList[i] != null){
                int chefLeftX = chef.getX() + chef.collisionArea.x;
                int chefRightX = chef.getX() + chef.collisionArea.x + chef.collisionArea.width;
                int chefTopY = chef.getY() + chef.collisionArea.y;
                int chefBottomY = chef.getY() + chef.collisionArea.y + chef.collisionArea.height;

                int objectLeftX = gp.itemList[i].x + gp.itemList[i].collisionArea.x;
                int objectRightX = gp.itemList[i].x + gp.itemList[i].collisionArea.x + gp.itemList[i].collisionArea.width;
                int objectTopY = gp.itemList[i].y + gp.itemList[i].collisionArea.y;
                int objectBottomY = gp.itemList[i].y + gp.itemList[i].collisionArea.y + gp.itemList[i].collisionArea.height;

                switch(chef.getDirection()){
                    case "up":
                        chefTopY -= chef.getSpeed();
                        break;
                    case "down":
                        chefBottomY += chef.getSpeed();
                        break;
                    case "left":
                        chefLeftX -= chef.getSpeed();
                        break;
                    case "right":
                        chefRightX += chef.getSpeed();
                        break;
                }

                if (chefLeftX < objectRightX &&
                    chefRightX > objectLeftX &&
                    chefTopY < objectBottomY &&
                    chefBottomY > objectTopY){
                    
                    if (gp.itemList[i].collision == true){
                        chef.collisionOn = true;
                    }
                    index = i;
                }
            }
        }
        return index;
    }
}