package com.restaurant.model;

/**
 * Subkelas MenuItem untuk item makanan.
 */
public class Makanan extends MenuItem {

    private String jenisMakanan;

    public Makanan(String nama, double harga, String kategori, String jenisMakanan) {
        super(nama, harga, kategori);
        this.jenisMakanan = jenisMakanan;
    }

    public String getJenisMakanan() {
        return jenisMakanan;
    }

    public void setJenisMakanan(String jenisMakanan) {
        this.jenisMakanan = jenisMakanan;
    }

    @Override
    public void tampilMenu() {
        System.out.printf("  [MAKANAN] %-20s Rp %,.0f  | Jenis: %s%n",
                getNama(), getHarga(), jenisMakanan);
    }
}
