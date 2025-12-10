package entity.item;

public interface CookingDevice {    
    // mengecek apakah bisa di dibawa-bawa atau diam
    boolean isPortable();
    
    // mengembalikan kapasitas maksimal alat masak
    int capacity();
    
    // mengecek apakah alat masak bisa menerima bahan tertentu
    boolean canAccept(Item ingredient);
    
    // menambahkan bahan ke dalam alat
    boolean addIngredient(Item ingredient);
    
    // memulai proses memasak
    void startCooking();
}