package utils;

import java.awt.event.KeyEvent;   
import java.awt.event.KeyListener;
import main.GamePanel;

public class KeyHandler implements KeyListener{

    public boolean upPressed, downPressed, leftPressed, rightPressed, chopThrow, pickUpDrop, swapChef, switchPressed;
    GamePanel gp;

    public KeyHandler() {
    }

    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Title state 
        if (gp != null && gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.menuState;
            }
            if (code == KeyEvent.VK_ESCAPE) {
                System.exit(0);
            }
        }

        // Menu State
        else if (gp != null && gp.gameState == gp.menuState) {
            if (code == KeyEvent.VK_ESCAPE) {
                if (gp.showHowToPlayOverlay) {
                    gp.showHowToPlayOverlay = false;
                } else if (gp.showCreditsOverlay) {
                    gp.showCreditsOverlay = false;
                } else{
                    System.exit(0);
                }
                return;
            }
            
            if (!gp.showHowToPlayOverlay && !gp.showCreditsOverlay) {
                if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                    gp.menuSelection--;
                    if (gp.menuSelection < 0) {
                        gp.menuSelection = gp.maxMenuOptions - 1;
                    }
                }
                
                if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                    gp.menuSelection++;
                    if (gp.menuSelection >= gp.maxMenuOptions) {
                        gp.menuSelection = 0;
                    }
                }
                
                if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                    switch (gp.menuSelection) {
                        case 0: // Start Game
                            gp.gameState = gp.stageSelectState;
                            break;
                        case 1: // How to Play
                            gp.showHowToPlayOverlay = true;
                            break;
                        case 2: // Credits
                            gp.showCreditsOverlay = true;
                            break;
                    }
                }
            }
        }
        
        // Stage Select State
        else if (gp != null && gp.gameState == gp.stageSelectState) {
            if (code == KeyEvent.VK_ESCAPE) {
                gp.gameState = gp.menuState;
            } else if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                gp.startGame();
            }
        }

        else if (gp != null && gp.gameState == gp.playState) {
            if(code == KeyEvent.VK_W){ upPressed = true; }
            if(code == KeyEvent.VK_S){ downPressed = true; }
            if(code == KeyEvent.VK_A){ leftPressed = true; }
            if(code == KeyEvent.VK_D){ rightPressed = true; }
            
            if(code == KeyEvent.VK_SHIFT) {switchPressed = true;}
            if(code == KeyEvent.VK_SLASH) {pickUpDrop = true;}
            if(code == KeyEvent.VK_PERIOD) {chopThrow = true;}
            
            if(code == KeyEvent.VK_ESCAPE) { 
                gp.gameState = gp.pauseState;
            }
        }
        
        // Pause state
        else if (gp != null && gp.gameState == gp.pauseState) {
            if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
                gp.pauseMenuSelection--;
                if (gp.pauseMenuSelection < 0) {
                    gp.pauseMenuSelection = gp.maxPauseMenuOptions - 1;
                }
            }
            if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
                gp.pauseMenuSelection++;
                if (gp.pauseMenuSelection >= gp.maxPauseMenuOptions) {
                    gp.pauseMenuSelection = 0;
                }
            }
            if(code == KeyEvent.VK_ESCAPE || code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                if (gp.pauseMenuSelection == 0) {
                    // Resume
                    gp.gameState = gp.playState;
                } else if (gp.pauseMenuSelection == 1) {
                    // Restart
                    gp.gameState = gp.stageSelectState;
                } else if (gp.pauseMenuSelection == 2) {
                    // Main Menu
                    gp.gameState = gp.menuState;
                }
                gp.pauseMenuSelection = 0;
            }
        }
        
        else if (gp != null && gp.gameState == gp.stageOverState) {
            if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_ENTER) {
                gp.gameState = gp.stageSelectState;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_W) {upPressed = false;}
        if(code == KeyEvent.VK_S) {downPressed = false;}
        if(code == KeyEvent.VK_A) {leftPressed = false;}
        if(code == KeyEvent.VK_D) {rightPressed = false;}
        if(code == KeyEvent.VK_SHIFT) {switchPressed = false;}
        if(code == KeyEvent.VK_SLASH) {pickUpDrop = false;}
        if(code == KeyEvent.VK_PERIOD) {chopThrow = false;}
    }
    
}