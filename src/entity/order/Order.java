package entity.order;

public class Order {
    private int id;
    private Recipe recipe;
    private int timeLeft;
    private int reward;
    private int penalty;

    public Order(int id, Recipe recipe, int timeLeft, int reward, int penalty) {
        this.id = id;
        this.recipe = recipe;
        this.timeLeft = timeLeft;
        this.reward = reward;
        this.penalty = penalty;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public int getTimeLeft() {
        return timeLeft;
    }
    
    public int getReward() { 
        return reward; 
    }
    public int getPenalty() { 
        return penalty; 
    }

    public void decreaseTime() {
        if (timeLeft > 0) {
            timeLeft--;
        }
    }

    public boolean isExpired() {
        return timeLeft <= 0;
    }
}