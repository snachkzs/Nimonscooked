package view;

import entity.Chef;
import entity.item.Item;
import entity.item.Plate;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import java.util.List;

public class InventoryView {
    
    private static final int INVENTORY_BOX_SIZE = 60;
    private static final int INGREDIENT_ICON_SIZE = 20;
    private static final int INGREDIENT_ICON_SPACING = 5;
    private static final int MARGIN = 10;
    
    public void render(Graphics2D g2, Chef chef, int screenWidth, int screenHeight) {
        if (chef == null || chef.inventory == null || chef.inventory.isEmpty()) {
            return;
        }

        int boxX = (screenWidth / 2) - (INVENTORY_BOX_SIZE / 2);
        int boxY = screenHeight - INVENTORY_BOX_SIZE - MARGIN;
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2.setColor(new Color(40, 40, 40, 200));
        g2.fillRoundRect(boxX, boxY, INVENTORY_BOX_SIZE, INVENTORY_BOX_SIZE, 10, 10);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(boxX, boxY, INVENTORY_BOX_SIZE, INVENTORY_BOX_SIZE, 10, 10);

        Item heldItem = chef.inventory.get(0);
        
        if (heldItem.getImage() != null) {
            int imageSize = INVENTORY_BOX_SIZE - 10;
            int imageX = boxX + 5;
            int imageY = boxY + 5;
            g2.drawImage(heldItem.getImage(), imageX, imageY, imageSize, imageSize, null);
        } else {
            // Fallback pake nama item
            g2.setColor(Color.WHITE);
            g2.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 10));
            String itemName = heldItem.getName();
            if (itemName.length() > 8) {
                itemName = itemName.substring(0, 8);
            }
            g2.drawString(itemName, boxX + 5, boxY + 30);
        }
        
        if (heldItem instanceof Plate) {
            Plate plate = (Plate) heldItem;
            List<Item> ingredients = plate.getIngredients();
            
            if (!ingredients.isEmpty()) {
                int iconsX = boxX + INVENTORY_BOX_SIZE + 5;
                int iconsY = boxY;
                
                renderIngredientIcons(g2, ingredients, iconsX, iconsY);
            }
        }
    }
    
    private void renderIngredientIcons(Graphics2D g2, List<Item> ingredients, int startX, int startY) {
        int maxIcons = Math.min(ingredients.size(), 4);
        
        for (int i = 0; i < maxIcons; i++) {
            Item ingredient = ingredients.get(i);
            
            int row = i / 2; // 0 or 1
            int col = i % 2; // 0 or 1
            int iconX = startX + (col * (INGREDIENT_ICON_SIZE + INGREDIENT_ICON_SPACING));
            int iconY = startY + (row * (INGREDIENT_ICON_SIZE + INGREDIENT_ICON_SPACING));
            
            g2.setColor(new Color(255, 255, 255, 220));
            g2.fillOval(iconX, iconY, INGREDIENT_ICON_SIZE, INGREDIENT_ICON_SIZE);
            
            g2.setColor(new Color(100, 100, 100));
            g2.setStroke(new BasicStroke(1));
            g2.drawOval(iconX, iconY, INGREDIENT_ICON_SIZE, INGREDIENT_ICON_SIZE);
            
            if (ingredient.getImage() != null) {
                BufferedImage img = ingredient.getImage();
                int iconSize = INGREDIENT_ICON_SIZE - 4;
                int imgX = iconX + 2;
                int imgY = iconY + 2;
                
                java.awt.Shape oldClip = g2.getClip();
                g2.setClip(new java.awt.geom.Ellipse2D.Float(iconX, iconY, INGREDIENT_ICON_SIZE, INGREDIENT_ICON_SIZE));
                g2.drawImage(img, imgX, imgY, iconSize, iconSize, null);
                g2.setClip(oldClip);
            } else {
                // Fallback pake nama item
                g2.setColor(Color.BLACK);
                g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
                String initial = ingredient.getName().substring(0, 1);
                int textWidth = g2.getFontMetrics().stringWidth(initial);
                g2.drawString(initial, iconX + (INGREDIENT_ICON_SIZE - textWidth) / 2, iconY + 15);
            }
        }
        
        if (ingredients.size() > 4) {
            g2.setColor(Color.WHITE);
            g2.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 10));
            int textX = startX;
            int textY = startY + (2 * (INGREDIENT_ICON_SIZE + INGREDIENT_ICON_SPACING)) + 10;
            g2.drawString("+" + (ingredients.size() - 4), textX, textY);
        }
    }
}