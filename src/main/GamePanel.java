package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import utils.KeyHandler;
import view.ChefView;
import view.StationView;
import entity.Chef;
import entity.Projectile;
import entity.map.TileManager;
import controller.CollisionChecker;
import controller.ChefManager;
import entity.item.Item;
import entity.stations.Station;
import entity.order.OrderManagement;
import view.GameStateView;
import view.InventoryView;
import view.OrderView;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int OriginalTileSize = 16;
    final int scale = 3;
    public final int tileSize = OriginalTileSize * scale; 
    public final int maxScreenCol = 14;
    public final int maxScreenRow = 10;
    public final double renderScale = 1.3; 
    final int screenWidth = (int)(tileSize * maxScreenCol * renderScale); 
    final int screenHeight = (int)(tileSize * maxScreenRow * renderScale); 
    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    public KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    ChefView chefView = new ChefView();
    public StationView stationView;
    GameStateView gameStateView = new GameStateView();
    InventoryView inventoryView = new InventoryView();
    OrderView orderView = new OrderView();
    public Item itemList[] = new Item[30]; 
    public Station stationList[] = new Station[100];
    
    // PROJECTILE
    public ArrayList<Projectile> projectiles = new ArrayList<>();
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
    public int stageTimeRemaining;
    private int timerFrameCounter;
    public int currentScore;
    public int consecutiveFailedOrders;
    public final int MAX_FAILED_ORDERS = 5;
    public final int SCORE_PER_ORDER = 30;
    public final int MIN_SCORE_TO_PASS = 90;
    public boolean stagePassed = false;
    public int menuSelection = 0; 
    public final int maxMenuOptions = 3;
    public int pauseMenuSelection = 0; 
    public final int maxPauseMenuOptions = 3;
    public boolean showHowToPlayOverlay = false;
    public boolean showCreditsOverlay = false;
    private boolean switchKeyPressed = false;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); 
        keyH = new KeyHandler(this);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        chefManager = new ChefManager(this, keyH);
        orderManager = new OrderManagement(this);
        stationView = new StationView();
    }

    public void setupGame(){
        assetSetter.setStation();
        gameState = titleState;
    }
    
    public void startGame() {
        stageTimeRemaining = 180; 
        timerFrameCounter = 0;
        currentScore = 0;
        consecutiveFailedOrders = 0;
        stagePassed = false;
        
        chefManager.resetChefs();
        for (int i = 0; i < itemList.length; i++) itemList[i] = null;
        for (int i = 0; i < stationList.length; i++) stationList[i] = null;
        
        projectiles.clear();
        
        assetSetter.setStation();
        orderManager.reset();
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
                if(remainingTime < 0) remainingTime = 0;
                Thread.sleep((long)(remainingTime/1000000));
                nextDrawTime += drawInterval;
            } catch (InterruptedException e){ e.printStackTrace(); }
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
                if (stationList[i] != null) stationList[i].update();
            }
            
            // update projectile
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = projectiles.get(i);
                if (p.isActive()) {
                    p.update();
                } else {
                    projectiles.remove(i);
                    i--;
                }
            }
        }
    }
    
    // Method Helper
    public void addProjectile(Projectile p) {
        projectiles.add(p);
    }
    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        g2.scale(renderScale, renderScale);

        if (gameState != playState && gameState != pauseState) {
            gameStateView.render(g2, gameState, (int)(screenWidth/renderScale), (int)(screenHeight/renderScale), showHowToPlayOverlay, showCreditsOverlay, menuSelection, pauseMenuSelection, stagePassed, currentScore);
        }
        
        if (gameState == playState || gameState == pauseState) {
            tileM.draw(g2);
            for (int i = 0; i < stationList.length; i++) {
                if (stationList[i] != null) stationView.render(g2, stationList[i], tileSize);
            }
            for (int i = 0; i < itemList.length; i++) {
                if (itemList[i] != null) itemList[i].draw(g2, this);
            }
            
            // draw projectile
            for (Projectile p : projectiles) {
                p.draw(g2);
            }

            chefView.render(g2, chefManager.getChef1(), tileSize);
            chefView.render(g2, chefManager.getChef2(), tileSize);
            
            if (gameState == pauseState) {
                gameStateView.render(g2, gameState, (int)(screenWidth/renderScale), (int)(screenHeight/renderScale), showHowToPlayOverlay, showCreditsOverlay, menuSelection, pauseMenuSelection, stagePassed, currentScore);
            }
            if (gameState == playState) {
                renderTimerAndScore(g2);
                Chef activeChef = chefManager.getActiveChef();
                if (activeChef != null) {
                    inventoryView.render(g2, activeChef, (int)(screenWidth/renderScale), (int)(screenHeight/renderScale));
                }
            }
            if (gameState == playState) {
                orderView.render(g2, orderManager.getActiveOrders(), (int)(screenWidth/renderScale), (int)(screenHeight/renderScale));
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
        g2.setFont(new java.awt.Font("Katoria Sans", java.awt.Font.BOLD, 20));
        g2.setColor(new Color(255, 255, 255));
        String scoreText = "Score: " + currentScore;
        g2.drawString(scoreText, 10, baseScreenHeight - 10);
        int minutes = stageTimeRemaining / 60;
        int seconds = stageTimeRemaining % 60;
        String timerText = String.format("Time: %d:%02d", minutes, seconds);
        int timerWidth = g2.getFontMetrics().stringWidth(timerText);
        if (stageTimeRemaining <= 30) g2.setColor(new Color(255, 0, 0)); 
        else if (stageTimeRemaining <= 60) g2.setColor(new Color(255, 200, 0)); 
        else g2.setColor(new Color(255, 255, 255)); 
        g2.drawString(timerText, baseScreenWidth - timerWidth - 10, baseScreenHeight - 10);
    }
}