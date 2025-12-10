package entity.item;

public interface InterfaceCookable {
    void setCookingTime(int time);
    int getCookingTime();
    boolean isCooked();
    void setCooked(boolean cooked);
    boolean isBurned();
    void setBurned(boolean burned);
    void cook();
}
