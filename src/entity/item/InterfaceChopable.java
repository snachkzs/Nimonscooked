package entity.item;

public interface InterfaceChopable {
    boolean isChopped();
    void setChopped(boolean chopped);
    Item getChoppedItem();
}