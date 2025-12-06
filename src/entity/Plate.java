package entity;

public class Plate extends KitchenUtensil {    
    private boolean isClean;

    public Plate() {
        // memanggil konstruktor KitchenUtensil
        super();
        // defaultnya piring bersih saat diambil
        this.isClean = true; 
    }

    // getter untuk cek kebersihan
    public boolean isClean() {
        return isClean;
    }

    // method untuk mencuci piring (dipanggil di Washing Station)
    public void wash() {
        this.isClean = true;
        System.out.println("Plate is now clean.");
    }

    // method untuk membuat piring kotor (dipanggil setelah makan atau serving)
    public void dirty() {
        this.isClean = false;
        // saat kotor, isinya harus dibuang/kosong
        this.contents.clear(); 
        System.out.println("Plate is now dirty.");
    }

    @Override
    public String getAssetPath() {
        if (this.isClean) {
            // kalo piring bersih, ke file gambar piring bersih
            return "/assets/kitchen-utensil/plate_clean.png";
        } else {
            // kalo piring kotor, ke file gambar piring kotor
            return "/assets/kitchen-utensil/plate_dirty.png";
        }
    }
}