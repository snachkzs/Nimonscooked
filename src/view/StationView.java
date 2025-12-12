package view;

import entity.stations.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class StationView {
    // Assembly
    private BufferedImage assemblyStation, assemblyStationLR;
    // Cooking
    private BufferedImage cookingStation, cookingStationLeft, cookingStationRight;
    // Cutting
    private BufferedImage cuttingStation, cuttingStationLeft;
    // Ingredients
    private BufferedImage ingStorageBun, ingStorageCheese, ingStorageLettuce, ingStorageMeat, ingStorageTomato;
    // Plate
    private BufferedImage plateStorage, plateStorageRight;
    // Serving
    private BufferedImage servingCounter, servingCounterRight1;
    // Trash
    private BufferedImage trashStation;
    // Washing
    private BufferedImage washingStation, washingStationDown;
    // Default fallback
    private BufferedImage mejaAja;

    public StationView() {
        loadSprites();
    }

    private void loadSprites() {
        try {
            
            mejaAja = load("/stations/meja_aja.png");

            assemblyStation = load("/stations/assembly_station.png");
            assemblyStationLR = load("/stations/assembly_station_leftright.png");
            
            cookingStation = load("/stations/cooking_station.png");
            cookingStationLeft = load("/stations/cooking_station_left.png");
            cookingStationRight = load("/stations/cooking_station_right.png");
            
            cuttingStation = load("/stations/cutting_station.png");
            cuttingStationLeft = load("/stations/cutting_station_left.png");
            
            ingStorageBun = load("/stations/ing_storage_bun.png");
            ingStorageCheese = load("/stations/ing_storage_cheese.png");
            ingStorageLettuce = load("/stations/ing_storage_lettuce.png");
            ingStorageMeat = load("/stations/ing_storage_meat.png");
            ingStorageTomato = load("/stations/ing_storage_tomato.png");
            
            plateStorage = load("/stations/plate_storage.png");
            plateStorageRight = load("/stations/plate_storage_right.png");
            
            servingCounter = load("/stations/serving_counter.png");
            servingCounterRight1 = load("/stations/serving_counter_right_alt1.png"); 
            
            trashStation = load("/stations/trash_station.png");
            
            washingStation = load("/stations/washing_station.png");
            washingStationDown = load("/stations/washing_station_down.png");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Gagal memuat gambar station! Cek path folder assets.");
        }
    }
    
    // Helper biar loading code lebih pendek
    private BufferedImage load(String path) {
        try {
            return ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            System.out.println("Image not found: " + path);
            return null;
        }
    }

    public void render(Graphics2D g2, Station station, int tileSize) {
        BufferedImage image = null;
        
        // posisi grid (Column & Row)
        int col = station.x / tileSize;
        int row = station.y / tileSize;
        
        // orientasi berdasarkan posisi di layout
        boolean isLeftWall = (col == 0);
        boolean isRightWall = (col == 13);
        boolean isBottomWall = (row >= 7);

        // --- 1. CUTTING STATION ---
        if (station instanceof CuttingStation) {
            image = cuttingStation;
            
            if (isRightWall) {
                if (cuttingStationLeft != null) image = cuttingStationLeft;
            }
        } 
        
        // --- 2. COOKING STATION ---
        else if (station instanceof CookingStation) {
            image = cookingStation;
            
            if (isLeftWall) {
                if (cookingStationRight != null) image = cookingStationRight;
            } else if (isRightWall) {
                if (cookingStationLeft != null) image = cookingStationLeft;
            }
        } 
        
        // --- 3. ASSEMBLY STATION ---
        else if (station instanceof AssemblyStation) {
            image = assemblyStation;

            if (isLeftWall || isRightWall) {
                if (assemblyStationLR != null) image = assemblyStationLR;
            }
        } 
        
        // --- 4. SERVING COUNTER ---
        else if (station instanceof ServingCounter) {
            image = servingCounter;
            
            if (isLeftWall) {
               if (servingCounterRight1 != null) image = servingCounterRight1; 
            }
        } 
        
        // --- 5. WASHING STATION ---
        else if (station instanceof WashingStation) {
            
            if (isBottomWall) {
                image = washingStation;
            } else {
                if (washingStationDown != null) image = washingStationDown;
                else image = washingStation;
            }
        } 
        
        // --- 6. TRASH STATION ---
        else if (station instanceof TrashStation) {
            image = trashStation;
        } 
        
        // --- 7. PLATE STORAGE ---
        else if (station instanceof PlateStorage) {
            image = plateStorage;
            
            if (isLeftWall) {
                if (plateStorageRight != null) image = plateStorageRight;
            }
        } 
        
        // --- 8. INGREDIENT STORAGE ---
        else if (station instanceof IngredientStorage) {
            IngredientStorage is = (IngredientStorage) station;
            String type = is.getStoredIngredientName(); 

            if (type != null) {
                switch (type.toLowerCase()) {
                    case "daging": case "meat": image = ingStorageMeat; break;
                    case "keju": case "cheese": image = ingStorageCheese; break;
                    case "roti": case "bread": case "bun": image = ingStorageBun; break;
                    case "lettuce": image = ingStorageLettuce; break;
                    case "tomat": case "tomato": image = ingStorageTomato; break;
                    default: image = mejaAja; break;
                }
            }
        } 
        
        // Fallback
        else {
            image = mejaAja;
        }

        // Render Gambar
        if (image != null) {
            g2.drawImage(image, station.x, station.y, tileSize, tileSize, null);
        }
    }
}