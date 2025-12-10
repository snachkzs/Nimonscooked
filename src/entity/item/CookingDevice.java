package entity.item;

public interface CookingDevice {    
    // mengecek apakah bisa di dibawa-bawa atau diam
    boolean isPortable();
    
    // mengembalikan kapasitas maksimal alat masak
    int capacity();
    
    // mengecek apakah alat masak bisa menerima bahan tertentu
    boolean canAccept(Preparable ingredient);
    
    // menambahkan bahan ke dalam alat
    void addIngredient(Preparable ingredient);
    
    // memulai proses memasak
    void startCooking();
}