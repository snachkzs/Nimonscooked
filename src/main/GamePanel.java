package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import input.KeyHandler;
import entity.Chef;
import view.ChefView;
import entity.item.Daging;
import view.DagingView;
import map.TileManager;
import controller.CollisionChecker;
import controller.ChefManager;
import entity.item.Item;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int OriginalTileSize = 16; // 16 x 16 
    final int scale = 3;

    public final int tileSize = OriginalTileSize * scale; // 48 x 48
    public final int maxScreenCol = 14;
    public final int maxScreenRow = 10;
    final int screenWidth = tileSize * maxScreenCol; //672 pixel
    final int screenHeight = tileSize * maxScreenRow; // 480 pixel

    int FPS = 60;

    public TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    DagingView dagingView = new DagingView();
    Chef chef = new Chef(this, keyH);
    ChefView chefView = new ChefView();
    public entity.item.Item[] itemList = new entity.item.Item[20]; //ganti sesuai banyak item
    
    public ChefManager chefManager;


    private boolean switchKeyPressed = false;

    public GamePanel(){
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // better rendering
        this.addKeyListener(keyH);
        this.setFocusable(true);

        chefManager = new ChefManager(this, keyH);
        assetSetter.setItem();
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
        if (keyH.switchPressed && !switchKeyPressed) {
            chefManager.swapChef();
            switchKeyPressed = true;
        }
        if (!keyH.switchPressed) {
            switchKeyPressed = false;
        }

        chefManager.update();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Draw tiles
        tileM.draw(g2);
        
        // Draw items
        for (int i = 0; i < itemList.length; i++) {
            if (itemList[i] != null && itemList[i] instanceof Daging) {
                dagingView.render(g2, (Daging)itemList[i], itemList[i].getX(), itemList[i].getY(), tileSize);
            }
        }
        
        // Draw both chefs
        chefView.render(g2, chefManager.getChef1(), tileSize);
        chefView.render(g2, chefManager.getChef2(), tileSize);
        
        // Draw UI indicators
        drawUI(g2);
        
        g2.dispose();
    }
    
    private void drawUI(Graphics2D g2) {
        
        // Show active chef
        // int activeIndex = chefManager.getActiveChefIndex();
        // g2.drawString("Active: Chef " + (activeIndex + 1), 10, 30);
        
        
        // // Show held items
        // g2.setColor(Color.CYAN);
        // if (chefManager.getChef1().isHoldingItem()) {
        //     g2.drawString("Chef 1 holding: " + 
        //         chefManager.getChef1().getHeldItem().getName(), 10, 120);
        // }
        // if (chefManager.getChef2().isHoldingItem()) {
        //     g2.drawString("Chef 2 holding: " + 
        //         chefManager.getChef2().getHeldItem().getName(), 10, 150);
        // }
        
    }
}
