package main;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import input.KeyHandler;
import entity.Chef;

public class GamePanel extends JPanel implements Runnable{

    //screen settings
    final int OriginalTileSize = 16; // 16 x 16 
    final int scale = 3;

    public final int tileSize = OriginalTileSize * scale; // 48 x 48
    final int maxScreenCol = 14;
    final int maxScreenRow = 10;
    final int screenWidth = tileSize * maxScreenCol; //672 pixel
    final int screenHeight = tileSize * maxScreenRow; // 480 pixel

    int FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    Chef chef = new Chef(this, keyH);


    //set player default position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel(){
        
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true); // better rendering
        this.addKeyListener(keyH);
        this.setFocusable(true);
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
        chef.update();

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        chef.draw(g2);

        g2.dispose();
    }
}
