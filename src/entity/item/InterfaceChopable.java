package entity.item;

public interface InterfaceChopable {
    public void canBeChopped();
    boolean isChopped();
    Item getChoppedItem();
}