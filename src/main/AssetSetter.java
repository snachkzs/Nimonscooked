package main;

import entity.item.Daging;
import entity.item.Keju;

public class AssetSetter {
    
    GamePanel gp;

    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setItem(){
        gp.itemList[0] = new Daging();
        gp.itemList[0].setPosition(200, 200);

        gp.itemList[1] = new Daging();
        gp.itemList[1].setPosition(300, 250);

        gp.itemList[2] = new Keju();
        gp.itemList[2].setPosition(400, 300);
    }

    // sebentar bingung
    // public void setStation(){
    //     gp.itemList[0] = new item.Item();
    //     gp.itemList[0].name = "Tomato";
    //     gp.itemList[0].x = 100;
    //     gp.itemList[0].y = 100;

    //     gp.itemList[1] = new item.Item();
    //     gp.itemList[1].name = "Lettuce";
    //     gp.itemList[1].x = 200;
    //     gp.itemList[1].y = 150;


    // }
}