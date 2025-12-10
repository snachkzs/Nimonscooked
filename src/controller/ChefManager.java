package controller;

import entity.Chef;
import utils.KeyHandler;
import main.GamePanel;

public class ChefManager {
    private Chef chef1;
    private Chef chef2;
    private GamePanel gp;
    private Chef activeChef;
    private int activeChefIndex = 0;

    public ChefManager(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;

        chef1 = new Chef(gp, keyH, 96, 96);
        chef2 = new Chef(gp, keyH, 528, 288);

        activeChef = chef1;
        chef1.setActive(true);
        chef2.setActive(false);
    }

    public void swapChef() {

        activeChef.setActive(false);

        if (activeChefIndex == 0) {
            activeChef = chef2;
            activeChefIndex = 1;
        } else {
            activeChef = chef1;
            activeChefIndex = 0;
        }

        activeChef.setActive(true);
    }

    public void update() {
        chef1.update();
        chef2.update();
    }

    public Chef getChef1() {
        return chef1;
    }

    public Chef getChef2() {
        return chef2;
    }

    public Chef getActiveChef() {
        return activeChef;
    }

    public int getActiveChefIndex() {
        return activeChefIndex;
    }
}