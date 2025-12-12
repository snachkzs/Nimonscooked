package view;

import entity.order.Order;
import entity.order.Recipe;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;

public class OrderView {
    private Map<String, BufferedImage> recipeImages;
    private Map<String, BufferedImage> ingredientImages;
    
    public OrderView() {
        recipeImages = new HashMap<>();
        ingredientImages = new HashMap<>();
        loadImages();
    }
    
    private void loadImages() {
        try {
            recipeImages.put("Classic Burger", ImageIO.read(getClass().getResourceAsStream("/menu/classic_burger.png")));
            recipeImages.put("Cheeseburger", ImageIO.read(getClass().getResourceAsStream("/menu/cheeseburger.png")));
            recipeImages.put("BLT Burger", ImageIO.read(getClass().getResourceAsStream("/menu/blt_burger.png")));
            recipeImages.put("Deluxe Burger", ImageIO.read(getClass().getResourceAsStream("/menu/deluxe_burger.png")));
            
            ingredientImages.put("Roti", ImageIO.read(getClass().getResourceAsStream("/ingredients/roti_burger.png")));
            ingredientImages.put("Patty Matang", ImageIO.read(getClass().getResourceAsStream("/ingredients/daging_masak.png")));
            ingredientImages.put("Keju Iris", ImageIO.read(getClass().getResourceAsStream("/ingredients/keju_potong.png")));
            ingredientImages.put("Lettuce potong", ImageIO.read(getClass().getResourceAsStream("/ingredients/lettuce_potong.png")));
            ingredientImages.put("Tomat potong", ImageIO.read(getClass().getResourceAsStream("/ingredients/tomat_potong.png")));
        } catch (Exception e) {
            System.err.println("Error loading order view images: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void render(Graphics2D g2, List<Order> orders, int screenWidth, int screenHeight) {
        if (orders == null || orders.isEmpty()) {
            return;
        }
        
        // max 4 order
        int ordersToDisplay = Math.min(orders.size(), 4);
        
        int boxWidth = 55;
        int boxHeight = 75;
        int boxSpacing = 5;
        int startX = 10;
        int startY = screenHeight - boxHeight - 35;
        
        for (int i = 0; i < ordersToDisplay; i++) {
            Order order = orders.get(i);
            Recipe recipe = order.getRecipe();
            
            int boxX = startX + (i * (boxWidth + boxSpacing));
            int boxY = startY;
            
            g2.setColor(Color.WHITE);
            g2.fillRect(boxX, boxY, boxWidth, boxHeight);
            
            g2.setColor(Color.BLACK);
            g2.drawRect(boxX, boxY, boxWidth, boxHeight);
            
            BufferedImage recipeImg = recipeImages.get(recipe.getName());
            if (recipeImg != null) {
                int imgSize = 28;
                int imgX = boxX + (boxWidth - imgSize) / 2;
                int imgY = boxY + 4;
                g2.drawImage(recipeImg, imgX, imgY, imgSize, imgSize, null);
            }
            
            int timerBarWidth = boxWidth - 10;
            int timerBarHeight = 4;
            int timerBarX = boxX + 5;
            int timerBarY = boxY + 35; 
            
            float timePercentage = (float) order.getTimeLeft() / (float) order.getInitialTime();

            g2.setColor(new Color(200, 200, 200));
            g2.fillRect(timerBarX, timerBarY, timerBarWidth, timerBarHeight);

            Color timerColor;
            if (timePercentage > 0.5f) {
                timerColor = new Color(100, 200, 100); // Green
            } else if (timePercentage > 0.25f) {
                timerColor = new Color(255, 200, 0); // Yellow
            } else {
                timerColor = new Color(255, 100, 100); // Red
            }
            g2.setColor(timerColor);
            int filledWidth = Math.min((int)(timerBarWidth * timePercentage), timerBarWidth);
            if (filledWidth > 0) {
                g2.fillRect(timerBarX, timerBarY, filledWidth, timerBarHeight);
            }
            
            List<String> ingredients = recipe.getRequiredIngredients();
            int ingredientSize = 13;
            int ingredientSpacing = 2;
            int ingredientsPerRow = 3;
            int ingredientStartY = boxY + 43;

            for (int j = 0; j < ingredients.size(); j++) {
                String ingredient = ingredients.get(j);
                BufferedImage ingredientImg = ingredientImages.get(ingredient);
                
                if (ingredientImg != null) {
                    int row = j / ingredientsPerRow;
                    int col = j % ingredientsPerRow;
                    
                    int ingredientX = boxX + ((boxWidth - (ingredientsPerRow * (ingredientSize + ingredientSpacing) - ingredientSpacing)) / 2) + col * (ingredientSize + ingredientSpacing);
                    int ingredientY = ingredientStartY + row * (ingredientSize + ingredientSpacing);
                    
                    g2.setColor(new Color(240, 240, 240));
                    g2.fillOval(ingredientX, ingredientY, ingredientSize, ingredientSize);
                    
                    g2.drawImage(ingredientImg, ingredientX + 2, ingredientY + 2, ingredientSize - 4, ingredientSize - 4, null);
                }
            }
        }
    }
}