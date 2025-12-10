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
    Chef chef = new Chef(this, keyH);
    ChefView chefView = new ChefView();
    public Item itemList[] = new Item[20]; //ganti sesuai banyak item
    
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

    public void setUpGame(){

        assetSetter.setItem();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        // Draw tiles
        tileM.draw(g2);
        
        // Draw items
        for (int i = 0; i < itemList.length; i++) {
            if (itemList[i] != null) {
                itemList[i].draw(g2, this);
            }
        }

        // Draw both chefs
        chefView.render(g2, chefManager.getChef1(), tileSize);
        chefView.render(g2, chefManager.getChef2(), tileSize);
        
        
        g2.dispose();
    }
    
}
