package com.restaurant.model;

/**
 * Subkelas MenuItem untuk promo diskon.
 */
public class Diskon extends MenuItem {

    private double diskon;

    public Diskon(String nama, double harga, String kategori, double diskon) {
        super(nama, harga, kategori);
        this.diskon = diskon;
    }

    public double getDiskon() {
        return diskon;
    }

    public void setDiskon(double diskon) {
        this.diskon = diskon;
    }

    public double hitungPotongan(double subtotal) {
        return subtotal * (diskon / 100.0);
    }

    @Override
    public void tampilMenu() {
        System.out.printf("  [DISKON]  %-20s Diskon %.0f%%%n", getNama(), diskon);
    }
}
