package view;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.awt.AlphaComposite;
import javax.imageio.ImageIO;

public class GameStateView {
    
    private BufferedImage titleScreen;
    private BufferedImage gameMenu;
    private BufferedImage howToPlay;
    private BufferedImage stageSelect;
    private BufferedImage pausedMenu;
    
    public GameStateView() {
        loadImages();
    }
    
    private void loadImages() {
        try {
            titleScreen = ImageIO.read(getClass().getResourceAsStream("/state/TitleScreen.png"));
            System.out.println("Loaded titleScreen");
        } catch (Exception e) {
            System.out.println("Could not load titleScreen: " + e.getMessage());
        }
        
        try {
            gameMenu = ImageIO.read(getClass().getResourceAsStream("/state/GameMenu.png"));
            System.out.println("Loaded gameMenu");
        } catch (Exception e) {
            System.out.println("Could not load gameMenu: " + e.getMessage());
        }
        
        try {
            howToPlay = ImageIO.read(getClass().getResourceAsStream("/state/HowToPlay.png"));
            System.out.println("Loaded howToPlay");
        } catch (Exception e) {
            System.out.println("Could not load howToPlay: " + e.getMessage());
            e.printStackTrace();
        }
        
        try {
            stageSelect = ImageIO.read(getClass().getResourceAsStream("/state/StageSelect.jpg"));
            System.out.println("Loaded stageSelect");
        } catch (Exception e) {
            System.out.println("Could not load stageSelect: " + e.getMessage());
        }
        
        try {
            pausedMenu = ImageIO.read(getClass().getResourceAsStream("/state/PausedMenu.png"));
            System.out.println("Loaded pausedMenu");
        } catch (Exception e) {
            System.out.println("Could not load pausedMenu: " + e.getMessage());
        }
        
        System.out.println("Game state image loading completed");
    }
    
    public void render(Graphics2D g2, int gameState, int screenWidth, int screenHeight, boolean showHowToPlayOverlay, boolean showCreditsOverlay, int menuSelection, int pauseMenuSelection, boolean stagePassed, int finalScore) {
        switch (gameState) {
            case 0: // titleState
                renderTitleScreen(g2, screenWidth, screenHeight);
                break;
            case 1: // menuState
                renderMenuScreen(g2, screenWidth, screenHeight, menuSelection);
                // Render overlays on top of menu
                if (showHowToPlayOverlay) {
                    renderHowToPlay(g2, screenWidth, screenHeight);
                }
                if (showCreditsOverlay) {
                    renderCreditsOverlay(g2, screenWidth, screenHeight);
                }
                break;
            case 2: // howToPlayState
                renderHowToPlay(g2, screenWidth, screenHeight);
                break;
            case 3: // creditsState
                renderCreditsOverlay(g2, screenWidth, screenHeight);
                break;
            case 4: // stageSelectState
                renderStageSelect(g2, screenWidth, screenHeight, stagePassed);
                break;
            case 5: // playState
                // handled in GamePanel
                break;
            case 6: // pauseState
                renderPauseScreen(g2, screenWidth, screenHeight, pauseMenuSelection);
                break;
            case 7: // stageOverState
                renderStageOver(g2, screenWidth, screenHeight, stagePassed, finalScore);
                break;
            default:
                break;
        }
    }
    
    private void renderTitleScreen(Graphics2D g2, int screenWidth, int screenHeight) {
        if (titleScreen != null) {
            g2.drawImage(titleScreen, 0, 0, screenWidth, screenHeight, null);
        }
    }
    
    private void renderMenuScreen(Graphics2D g2, int screenWidth, int screenHeight, int menuSelection) {
        if (gameMenu != null) {
            g2.drawImage(gameMenu, 0, 0, screenWidth, screenHeight, null);
        }
        
        drawMenuIndicator(g2, screenWidth, screenHeight, menuSelection);
    }
    
    private void drawMenuIndicator(Graphics2D g2, int screenWidth, int screenHeight, int menuSelection) {
        int startX = (int)(screenWidth * 0.265);
        int startY = screenHeight / 5;
        int menuSpacing = 20; 
        
        int selectedY = startY + (menuSelection * menuSpacing);
        
        g2.setColor(new Color(255, 255, 255, 100));
        g2.setStroke(new BasicStroke(2));
        int boxWidth = 75;
        int boxHeight = 20;
        g2.drawRoundRect(startX - boxWidth/2, selectedY - boxHeight/2, boxWidth, boxHeight, 10, 10);
    }
    
    private void renderHowToPlay(Graphics2D g2, int screenWidth, int screenHeight) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, screenWidth, screenHeight);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        if (howToPlay != null) {
            int imgWidth = (int)(screenWidth * 0.8);
            int imgHeight = (int)(screenHeight * 0.8);
            int x = (screenWidth - imgWidth) / 2;
            int y = (screenHeight - imgHeight) / 2;
            
            g2.drawImage(howToPlay, x, y, imgWidth, imgHeight, null);
        }
    }
    
    private void renderCreditsOverlay(Graphics2D g2, int screenWidth, int screenHeight) {
        // Dark blue overlay (Overcooked theme)
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
        g2.setColor(new Color(30, 60, 100, 180)); // Dark blue
        g2.fillRect(0, 0, screenWidth, screenHeight);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        int boxWidth = (int)(screenWidth * 0.7);
        int boxHeight = (int)(screenHeight * 0.7);
        int x = (screenWidth - boxWidth) / 2;
        int y = (screenHeight - boxHeight) / 2;
        
        // White background box
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        
        // Blue border
        g2.setColor(new Color(70, 130, 180)); // Steel blue
        g2.setStroke(new java.awt.BasicStroke(4));
        g2.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);
         
        // Title in orange/yellow (Overcooked accent color)
        g2.setColor(new Color(255, 160, 0)); // Bright orange
        g2.setFont(new Font("Katoria Sans", Font.BOLD, 32));
        String title = "CREDITS";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, x + (boxWidth - titleWidth) / 2, y + 60);
        
        // Content in dark blue
        g2.setColor(new Color(40, 70, 110)); // Dark blue text
        g2.setFont(new Font("Katoria Sans", Font.PLAIN, 20));
        String[] credits = {
            "TEAM",
            "",
            "Alma Felicia Vielrizki - 18223112",
            "Andreas Saputra Tambun - 18224110",
            "Herlambang Setiaji Prabowo - 18224113",
            "Muchammad Rafif Azis Syahlevi - 18224123",
            "",
            "Press ESC to close"
        };
        
        int lineY = y + 120;
        for (String line : credits) {
            int lineWidth = g2.getFontMetrics().stringWidth(line);
            g2.drawString(line, x + (boxWidth - lineWidth) / 2, lineY);
            lineY += 35;
        }
    }
    
    private void renderStageSelect(Graphics2D g2, int screenWidth, int screenHeight, boolean stagePassed) {
        if (stageSelect != null) {
            g2.drawImage(stageSelect, 0, 0, screenWidth, screenHeight, null);
        }
        
        if (stagePassed) {
            g2.setFont(new Font("Katoria Sans", Font.BOLD, 24));
            g2.setColor(new Color(100, 200, 100));
            String successText = "SUCCEEDED!";
            int textWidth = g2.getFontMetrics().stringWidth(successText);
            int textX = screenWidth - textWidth - 30;
            int textY = 60; 
            
            g2.setColor(Color.BLACK);
            g2.drawString(successText, textX + 2, textY + 2);
            
            g2.setColor(new Color(100, 200, 100));
            g2.drawString(successText, textX, textY);
        }
    }
    
    private void renderPauseScreen(Graphics2D g2, int screenWidth, int screenHeight, int pauseMenuSelection) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(0, 0, screenWidth, screenHeight);
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        if (pausedMenu != null) {
            int imgWidth = (int)(screenWidth * 0.3);
            int imgHeight = (int)(screenHeight * 0.3);
            int x = (screenWidth - imgWidth) / 2;
            int y = (screenHeight - imgHeight) / 2;
            
            g2.drawImage(pausedMenu, x, y, imgWidth, imgHeight, null);
        }

        drawPauseMenuIndicator(g2, screenWidth, screenHeight, pauseMenuSelection);
    }
    
    private void drawPauseMenuIndicator(Graphics2D g2, int screenWidth, int screenHeight, int pauseMenuSelection) {
        int startX = (int)(screenWidth * 0.5);
        int startY = (int)(screenHeight * 0.41);
        int menuSpacing = 45;
        
        int selectedY = startY + (pauseMenuSelection * menuSpacing);
        
        g2.setColor(new Color(255, 200, 0, 100)); // Semi-transparent yellow
        g2.setStroke(new BasicStroke(2));
        int boxWidth = 185;
        int boxHeight = 37;
        g2.drawRoundRect(startX - boxWidth/2, selectedY - boxHeight/2, boxWidth, boxHeight, 10, 10);
    }
    
    // belom ada assetnya
    private void renderStageOver(Graphics2D g2, int screenWidth, int screenHeight, boolean passed, int finalScore) {
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
        g2.setColor(new Color(0, 0, 0, 200));
        g2.fillRect(0, 0, screenWidth, screenHeight);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        
        int boxWidth = (int)(screenWidth * 0.6);
        int boxHeight = (int)(screenHeight * 0.5);
        int x = (screenWidth - boxWidth) / 2;
        int y = (screenHeight - boxHeight) / 2;
        

        if (passed) {
            g2.setColor(new Color(50, 150, 50)); // Green for pass
        } else {
            g2.setColor(new Color(150, 50, 50)); // Red for fail
        }
        g2.fillRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        
        // Border
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, boxWidth, boxHeight, 20, 20);
        
        // Title
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Katoria Sans", Font.BOLD, 48));
        String title = passed ? "STAGE CLEAR!" : "STAGE FAILED";
        int titleWidth = g2.getFontMetrics().stringWidth(title);
        g2.drawString(title, x + (boxWidth - titleWidth) / 2, y + 80);
        
        // Score
        g2.setFont(new Font("Katoria Sans", Font.BOLD, 32));
        String scoreText = "Final Score: " + finalScore;
        int scoreWidth = g2.getFontMetrics().stringWidth(scoreText);
        g2.drawString(scoreText, x + (boxWidth - scoreWidth) / 2, y + 140);
        
        // Result message
        g2.setFont(new Font("Katoria Sans", Font.PLAIN, 24));
        String message;
        if (passed) {
            message = "Congratulations! You passed the stage!";
        } else {
            message = "Try again to pass the stage!";
        }
        int messageWidth = g2.getFontMetrics().stringWidth(message);
        g2.drawString(message, x + (boxWidth - messageWidth) / 2, y + 190);
        
        // Instructions
        g2.setFont(new Font("Katoria Sans", Font.PLAIN, 20));
        String instruction = "Press SPACE to continue";
        int instrWidth = g2.getFontMetrics().stringWidth(instruction);
        g2.drawString(instruction, x + (boxWidth - instrWidth) / 2, y + boxHeight - 40);
    }
}