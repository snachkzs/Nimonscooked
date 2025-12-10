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
    public Item itemList[] = new Item[30]; 
    public Station stationList[] = new Station[30]; //ga mood ngitung ada berapa station acc
    
    public ChefManager chefManager;
    public OrderManagement orderManager;

    private boolean switchKeyPressed = false;

    public GamePanel(){
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // better rendering
        this.addKeyListener(keyH);
        this.setFocusable(true);

        chefManager = new ChefManager(this, keyH);
        orderManager = new OrderManagement();
        assetSetter.setStation();
        
        // Generate initial orders
        orderManager.generateNewOrder();
        orderManager.generateNewOrder();
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
        
        orderManager.update();

        for (int i = 0; i < stationList.length; i++) {
            if (stationList[i] != null) {
                stationList[i].update();
            }
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.scale(renderScale, renderScale);

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
        
        g2.dispose();
    }
    
}
