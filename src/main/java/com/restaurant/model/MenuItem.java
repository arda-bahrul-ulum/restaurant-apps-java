package com.restaurant.model;

/**
 * Kelas abstrak dasar untuk semua item menu restoran.
 * Mengimplementasikan konsep abstraksi dan encapsulation.
 */
public abstract class MenuItem {

    private String nama;
    private double harga;
    private String kategori;

    protected MenuItem(String nama, double harga, String kategori) {
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
    }

    public abstract void tampilMenu();

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public double getHarga() {
        return harga;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    @Override
    public String toString() {
        return String.format("%-20s Rp %,.0f  [%s]", nama, harga, kategori);
    }
}
