package com.restaurant.model;

import com.restaurant.exception.MenuItemTidakDitemukanException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Kelas untuk mengelola semua item menu restoran.
 */
public class Menu {

    private final ArrayList<MenuItem> daftarItem;

    public Menu() {
        this.daftarItem = new ArrayList<>();
    }

    public void tambahItem(MenuItem item) {
        daftarItem.add(item);
    }

    public MenuItem cariItemByNama(String nama) throws MenuItemTidakDitemukanException {
        for (MenuItem item : daftarItem) {
            if (item.getNama().equalsIgnoreCase(nama.trim())) {
                return item;
            }
        }
        throw new MenuItemTidakDitemukanException(nama);
    }

    public void tampilkanSemuaMenu() {
        if (daftarItem.isEmpty()) {
            System.out.println("  (Menu kosong)");
            return;
        }

        System.out.println("\n--- MAKANAN ---");
        for (MenuItem item : daftarItem) {
            if (item instanceof Makanan) {
                item.tampilMenu();
            }
        }

        System.out.println("\n--- MINUMAN ---");
        for (MenuItem item : daftarItem) {
            if (item instanceof Minuman) {
                item.tampilMenu();
            }
        }

        System.out.println("\n--- DISKON ---");
        boolean adaDiskon = false;
        for (MenuItem item : daftarItem) {
            if (item instanceof Diskon) {
                item.tampilMenu();
                adaDiskon = true;
            }
        }
        if (!adaDiskon) {
            System.out.println("  (Tidak ada promo diskon)");
        }
    }

    public List<MenuItem> getSemuaItem() {
        return Collections.unmodifiableList(daftarItem);
    }

    public boolean isKosong() {
        return daftarItem.isEmpty();
    }

    public void kosongkan() {
        daftarItem.clear();
    }
}
