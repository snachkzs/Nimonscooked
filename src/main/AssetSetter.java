package main;

import entity.item.Daging;
import entity.item.Keju;
import entity.stations.*;

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

    public void setStation(){
        int stationIndex = 0;
        int ingredientStorageCount = 0;
        
        // 1 = cut, 2 = cook, 3 = assembly, 4 = serving, 5 = wash, 7 = plate, 8 = trash
        // 11 = daging, 12 = keju, 13 = roti, 14 = lettuce, 15 = tomat
        
        for (int row = 0; row < gp.maxScreenRow; row++) {
            for (int col = 0; col < gp.maxScreenCol; col++) {
                int tileNum = gp.tileM.mapTileNum[col][row];
                int x = col * gp.tileSize;
                int y = row * gp.tileSize;
                
                Station station = null;
                
                switch(tileNum) {
                    case 1: // Cutting Station
                        station = new CuttingStation(x, y, gp.tileSize, gp.tileSize);
                        break;
                    case 2: // Cooking Station
                        station = new CookingStation(x, y, gp.tileSize, gp.tileSize);
                        break;
                    case 3: // Assembly Station
                        station = new AssemblyStation(x, y, gp.tileSize, gp.tileSize);
                        break;
                    case 4: // Serving Counter
                        station = new ServingCounter(x, y, gp.tileSize, gp.tileSize);
                        break;
                    case 5: // Washing Station
                        station = new WashingStation(x, y, gp.tileSize, gp.tileSize);
                        break;
                    case 7: // Plate Storage
                        station = new PlateStorage(x, y, gp.tileSize, gp.tileSize);
                        break;
                    case 8: // Trash Station
                        station = new TrashStation(x, y, gp.tileSize, gp.tileSize);
                        break;
                    case 11: // Daging Storage
                        station = new IngredientStorage(x, y, gp.tileSize, gp.tileSize, "daging");
                        ingredientStorageCount++;
                        break;
                    case 12: // Keju Storage
                        station = new IngredientStorage(x, y, gp.tileSize, gp.tileSize, "keju");
                        ingredientStorageCount++;
                        break;
                    case 13: // Roti Storage
                        station = new IngredientStorage(x, y, gp.tileSize, gp.tileSize, "roti");
                        ingredientStorageCount++;
                        break;
                    case 14: // Lettuce Storage
                        station = new IngredientStorage(x, y, gp.tileSize, gp.tileSize, "lettuce");
                        ingredientStorageCount++;
                        break;
                    case 15: // Tomat Storage
                        station = new IngredientStorage(x, y, gp.tileSize, gp.tileSize, "tomat");
                        ingredientStorageCount++;
                        break;
                }
                
                if (station != null && stationIndex < gp.stationList.length) {
                    gp.stationList[stationIndex] = station;
                    stationIndex++;
                }
            }
        }
        
        System.out.println("Loaded " + stationIndex + " stations from map");
        System.out.println("- " + ingredientStorageCount + " ingredient storages");
    }
}