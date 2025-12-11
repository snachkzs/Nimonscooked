package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import utils.KeyHandler;
import entity.Chef;
import view.ChefView;
import entity.item.Daging;
import entity.map.TileManager;
import controller.CollisionChecker;
import controller.ChefManager;
import entity.item.Item;
import entity.stations.Station;
import entity.order.OrderManagement;
import view.GameStateView;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int OriginalTileSize = 16;
    final int scale = 3;

    public final int tileSize = OriginalTileSize * scale; // 48 x 48 
    public final int maxScreenCol = 14;
    public final int maxScreenRow = 10;
    
    // Render scale
    public final double renderScale = 1.3; // 130%
    
    final int screenWidth = (int)(tileSize * maxScreenCol * renderScale); // diperbesar untuk rendering
    final int screenHeight = (int)(tileSize * maxScreenRow * renderScale); // diperbesar untuk rendering

    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    ChefView chefView = new ChefView();
    GameStateView gameStateView = new GameStateView();
    public Item itemList[] = new Item[30]; 
    public Station stationList[] = new Station[28];
    
    public ChefManager chefManager;
    public OrderManagement orderManager;

    // Game state
    public int gameState;
    public final int titleState = 0;
    public final int menuState = 1;
    public final int howToPlayState = 2;
    public final int creditsState = 3;
    public final int stageSelectState = 4;
    public final int playState = 5;
    public final int pauseState = 6;
    public final int stageOverState = 7;
    
    // Stage timer and scoring
    public int stageTimeRemaining;
    private int timerFrameCounter;
    public int currentScore;
    public int consecutiveFailedOrders;
    public final int MAX_FAILED_ORDERS = 5;
    public final int SCORE_PER_ORDER = 30;
    public final int MIN_SCORE_TO_PASS = 90;
    public boolean stagePassed = false;
    
    // Menu selection
    public int menuSelection = 0; // 0=Start Game, 1=How to Play, 2=Credits
    public final int maxMenuOptions = 3;
    
    // Pause menu selection
    public int pauseMenuSelection = 0; // 0=Resume, 1=Restart, 2=Main Menu
    public final int maxPauseMenuOptions = 3;
    
    // Overlay states
    public boolean showHowToPlayOverlay = false;
    public boolean showCreditsOverlay = false;

    private boolean switchKeyPressed = false;

    public GamePanel(){
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // better rendering
        
        keyH = new KeyHandler(this);
        this.addKeyListener(keyH);
        this.setFocusable(true);

        chefManager = new ChefManager(this, keyH);
        orderManager = new OrderManagement(this);
    }

    public void setupGame(){
        assetSetter.setStation();

        gameState = titleState;
    }
    
    public void startGame() {
        stageTimeRemaining = 180; // 3 minutes
        timerFrameCounter = 0;
        currentScore = 0;
        consecutiveFailedOrders = 0;
        stagePassed = false;
        
        orderManager.generateNewOrder();
        orderManager.generateNewOrder();
        gameState = playState;
    }

    public void startGameThread(){

        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();
            repaint();
            
            try{
                double remainingTime = nextDrawTime - System.nanoTime();

                if(remainingTime < 0){
                    remainingTime = 0;
                }

                Thread.sleep((long)(remainingTime/1000000));

                nextDrawTime += drawInterval;
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    public void update(){
        if (gameState == playState) {
            timerFrameCounter++;
            if (timerFrameCounter >= FPS) {
                timerFrameCounter = 0;
                stageTimeRemaining--;
                
                if (stageTimeRemaining <= 0) {
                    endStage();
                    return;
                }
            }
            
            if (consecutiveFailedOrders >= MAX_FAILED_ORDERS) {
                stagePassed = false;
                gameState = stageOverState;
                return;
            }
            
            if (keyH.switchPressed && !switchKeyPressed) {
                chefManager.swapChef();
                switchKeyPressed = true;
            }
            if (!keyH.switchPressed) {
                switchKeyPressed = false;
            }
            
            orderManager.update();

            for (int i = 0; i < stationList.length; i++) {
                if (stationList[i] != null) {
                    stationList[i].update();
                }
            }
        }
        if (gameState == pauseState) {
        }
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.scale(renderScale, renderScale);

        if (gameState != playState && gameState != pauseState) {
            gameStateView.render(g2, gameState, (int)(screenWidth/renderScale), (int)(screenHeight/renderScale), showHowToPlayOverlay, showCreditsOverlay, menuSelection, pauseMenuSelection, stagePassed, currentScore);
        }
        
        if (gameState == playState || gameState == pauseState) {
            // gambar tiles
            tileM.draw(g2);
            
            // gambar item
            for (int i = 0; i < itemList.length; i++) {
                if (itemList[i] != null) {
                    itemList[i].draw(g2, this);
                }
            }

            // gambar kedua chef
            chefView.render(g2, chefManager.getChef1(), tileSize);
            chefView.render(g2, chefManager.getChef2(), tileSize);
            
            if (gameState == pauseState) {
                gameStateView.render(g2, gameState, (int)(screenWidth/renderScale), (int)(screenHeight/renderScale), showHowToPlayOverlay, showCreditsOverlay, menuSelection, pauseMenuSelection, stagePassed, currentScore);
            }
            
            if (gameState == playState) {
                renderTimerAndScore(g2);
            }
        }

        
        g2.dispose();
    }
    
    private void endStage() {
        stagePassed = (currentScore >= MIN_SCORE_TO_PASS);
        gameState = stageOverState;
    }
    
    private void renderTimerAndScore(Graphics2D g2) {
        int baseScreenWidth = (int)(screenWidth/renderScale);
        int baseScreenHeight = (int)(screenHeight/renderScale);
        
        // Set font
        g2.setFont(new java.awt.Font("Katoria Sans", java.awt.Font.BOLD, 20));
        
        // Render score (bottom left)
        g2.setColor(new Color(255, 255, 255));
        String scoreText = "Score: " + currentScore;
        g2.drawString(scoreText, 10, baseScreenHeight - 10);
        
        // Render timer (bottom right)
        int minutes = stageTimeRemaining / 60;
        int seconds = stageTimeRemaining % 60;
        String timerText = String.format("Time: %d:%02d", minutes, seconds);
        int timerWidth = g2.getFontMetrics().stringWidth(timerText);
        
        // Change color if time is running out
        if (stageTimeRemaining <= 30) {
            g2.setColor(new Color(255, 0, 0)); // Red
        } else if (stageTimeRemaining <= 60) {
            g2.setColor(new Color(255, 200, 0)); // Yellow
        } else {
            g2.setColor(new Color(255, 255, 255)); // White
        }
        
        g2.drawString(timerText, baseScreenWidth - timerWidth - 10, baseScreenHeight - 10);
    }
    
}
