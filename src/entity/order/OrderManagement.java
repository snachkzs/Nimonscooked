package entity.order;

import entity.item.Item;
import main.GamePanel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderManagement {
    private List<Order> activeOrders;
    private List<Recipe> availableRecipes;
    private int orderCounter = 0;
    private int frameCounter = 0;
    private static final int FPS = 60;
    private GamePanel gp;

    public OrderManagement(GamePanel gp) {
        this.gp = gp;
        this.activeOrders = new ArrayList<>();
        this.availableRecipes = new ArrayList<>();
        initializeRecipes();
    }

    private void initializeRecipes() {
        availableRecipes.add(new Recipe("Classic Burger", 
            Arrays.asList("Roti", "Patty Matang")));

        availableRecipes.add(new Recipe("Cheeseburger", 
            Arrays.asList("Roti", "Patty Matang", "Keju Iris")));

        availableRecipes.add(new Recipe("BLT Burger", 
            Arrays.asList("Roti", "Lettuce potong", "Tomat potong", "Patty Matang")));

        availableRecipes.add(new Recipe("Deluxe Burger", 
            Arrays.asList("Roti", "Lettuce potong", "Patty Matang", "Keju Iris")));
    }

    public void generateNewOrder() {
        if (activeOrders.size() >= 3) return;

        Random rand = new Random();
        Recipe r = availableRecipes.get(rand.nextInt(availableRecipes.size()));
        
        orderCounter++;
        Order newOrder = new Order(orderCounter, r, 40, 100, 50); // 40 seconds per order
        
        activeOrders.add(newOrder);
        System.out.println("NEW ORDER: " + r.getName() + " (Time: 40s)");
    }

    public boolean processDelivery(List<Item> plateContents) {
        for (Order order : activeOrders) {
            if (order.getRecipe().validate(plateContents)) {
                System.out.println("Order " + order.getRecipe().getName() + " SELESAI! (+" + gp.SCORE_PER_ORDER + " pts)");
                gp.currentScore += gp.SCORE_PER_ORDER;
                gp.consecutiveFailedOrders = 0; // Reset failed counter on success
                activeOrders.remove(order);
                
                generateNewOrder();
                return true;
            }
        }

        System.out.println("Order SALAH! (-10 pts)");
        gp.currentScore -= 10;
        gp.consecutiveFailedOrders++;
        return false;
    }

    public void update() {
        frameCounter++;
        
        if (frameCounter >= FPS) {
            frameCounter = 0;
            
            for (int i = 0; i < activeOrders.size(); i++) {
                Order o = activeOrders.get(i);
                o.decreaseTime();
                
                if (o.isExpired()) {
                    System.out.println("Order " + o.getRecipe().getName() + " EXPIRED! (-" + o.getPenalty() + " pts)");
                    gp.currentScore -= o.getPenalty();
                    gp.consecutiveFailedOrders++; // Count as failed order
                    activeOrders.remove(i);
                    i--;
                    
                    generateNewOrder();
                }
            }
        }
    }

    public List<Order> getActiveOrders() {
        return activeOrders;
    }
}