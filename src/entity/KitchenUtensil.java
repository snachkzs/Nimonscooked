package entity;

import java.util.List;
import java.util.ArrayList;

public abstract class KitchenUtensil {
    // daftar bahan yang ada di dalam alat masak
    protected List<Preparable> contents;

    // constructor
    public KitchenUtensil() {
        // inisialisasi
        this.contents = new ArrayList<>();
    }

    // mengambil isi list
    public List<Preparable> getContents() {
        return this.contents;
    }
}