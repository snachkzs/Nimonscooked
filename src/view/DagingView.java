// package view;

// import entity.item.Daging;
// import java.awt.Graphics2D;
// import java.awt.image.BufferedImage;
// import javax.imageio.ImageIO;
// import java.awt.Color;

// public class DagingView {
//     private BufferedImage rawSprite, cookedSprite;
//     private int spriteCounter = 0;
//     private int spriteNum = 1;

//     public DagingView(){
//         loadSprites();
//     }

//     private void loadSprites(){
//         try{
//             rawSprite = ImageIO.read(getClass().getResourceAsStream("/ingredients/dagingmentah.png"));
//             cookedSprite = ImageIO.read(getClass().getResourceAsStream("/ingredients/dagingmatang.jpg"));
//             System.out.println("Daging sprites loaded successfully");
//         } catch (Exception e) {
//             System.out.println("Error loading daging sprites: " + e.getMessage());
//             e.printStackTrace();
//         }
//     }

//     public void render(Graphics2D g2, Daging daging, int x, int y, int size){
//         BufferedImage image;
//         if (daging.isRaw()) {
//             image = rawSprite;
//         } else {
//             image = cookedSprite;
//         }
        
//         if (image != null) {
//             g2.drawImage(image, x, y, size, size, null);
//         } 
//     }
// }