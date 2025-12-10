package entity.order;

import entity.item.Item;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class OrderManagement {
    private List<Order> activeOrders;
    private List<Recipe> availableRecipes;
    private int orderCounter = 0;
    private int score = 0;

    public OrderManagement() {
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
        Order newOrder = new Order(orderCounter, r, 60, 100, 50); 
        
        activeOrders.add(newOrder);
        System.out.println("NEW ORDER: " + r.getName() + " (Time: 60s)");
    }

    public boolean processDelivery(List<Item> plateContents) {
        for (Order order : activeOrders) {
            if (order.getRecipe().validate(plateContents)) {
                System.out.println("Order " + order.getRecipe().getName() + " SELESAI! (+" + order.getReward() + " pts)");
                score += order.getReward();
                activeOrders.remove(order);
                return true;
            }
        }

        System.out.println("Order SALAH! (-50 pts)");
        score -= 50;
        return false;
    }

    public void update() {
        for (int i = 0; i < activeOrders.size(); i++) {
            Order o = activeOrders.get(i);
            o.decreaseTime();
            
            if (o.isExpired()) {
                System.out.println("Order " + o.getRecipe().getName() + " EXPIRED! (-" + o.getPenalty() + " pts)");
                score -= o.getPenalty();
                activeOrders.remove(i);
                i--;
            }
        }
    }
}