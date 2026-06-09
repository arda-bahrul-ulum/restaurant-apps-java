package com.restaurant.model;

/**
 * Subkelas MenuItem untuk item minuman.
 */
public class Minuman extends MenuItem {

    private String jenisMinuman;

    public Minuman(String nama, double harga, String kategori, String jenisMinuman) {
        super(nama, harga, kategori);
        this.jenisMinuman = jenisMinuman;
    }

    public String getJenisMinuman() {
        return jenisMinuman;
    }

    public void setJenisMinuman(String jenisMinuman) {
        this.jenisMinuman = jenisMinuman;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("  [MINUMAN] %-20s Rp %,.0f  | Jenis: %s%n",
                getNama(), getHarga(), jenisMinuman);
    }
}
