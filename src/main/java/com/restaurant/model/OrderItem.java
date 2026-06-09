package com.restaurant.model;

/**
 * Merepresentasikan satu baris pesanan (menu + jumlah).
 */
public class OrderItem {

    private MenuItem item;
    private int jumlah;

    public OrderItem(MenuItem item, int jumlah) {
        this.item = item;
        this.jumlah = jumlah;
    }

    public MenuItem getItem() {
        return item;
    }

    public void setItem(MenuItem item) {
        this.item = item;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public double getSubtotal() {
        if (item instanceof Diskon) {
            return 0;
        }
        return item.getHarga() * jumlah;
    }
}
