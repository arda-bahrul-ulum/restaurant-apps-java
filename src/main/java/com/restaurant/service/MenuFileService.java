package com.restaurant.service;

import com.restaurant.model.Diskon;
import com.restaurant.model.Makanan;
import com.restaurant.model.Menu;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Minuman;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Layanan operasi file untuk menyimpan dan memuat daftar menu.
 */
public class MenuFileService {

    private static final String FILE_MENU = "data/menu.txt";

    public void simpanMenu(Menu menu) throws IOException {
        Path path = Paths.get(FILE_MENU);
        Files.createDirectories(path.getParent());

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (MenuItem item : menu.getSemuaItem()) {
                writer.write(keBarisFile(item));
                writer.newLine();
            }
        }
    }

    public Menu muatMenu() throws IOException {
        Path path = Paths.get(FILE_MENU);
        Menu menu = new Menu();

        if (!Files.exists(path)) {
            buatMenuDefault(menu);
            simpanMenu(menu);
            return menu;
        }

        List<String> baris = Files.readAllLines(path);
        for (String line : baris) {
            if (line.trim().isEmpty()) {
                continue;
            }
            MenuItem item = parseBaris(line);
            menu.tambahItem(item);
        }

        return menu;
    }

    private MenuItem parseBaris(String line) throws IOException {
        String[] bagian = line.split("\\|", -1);
        if (bagian.length < 5) {
            throw new IOException("Format baris tidak valid: " + line);
        }

        String tipe = bagian[0].trim().toUpperCase();
        String nama = bagian[1].trim();
        double harga = Double.parseDouble(bagian[2].trim());
        String kategori = bagian[3].trim();
        String tambahan = bagian[4].trim();

        switch (tipe) {
            case "MAKANAN":
                return new Makanan(nama, harga, kategori, tambahan);
            case "MINUMAN":
                return new Minuman(nama, harga, kategori, tambahan);
            case "DISKON":
                return new Diskon(nama, harga, kategori, Double.parseDouble(tambahan));
            default:
                throw new IOException("Tipe menu tidak dikenal: " + tipe);
        }
    }

    private String keBarisFile(MenuItem item) {
        if (item instanceof Makanan) {
            Makanan makanan = (Makanan) item;
            return String.format("MAKANAN|%s|%.0f|%s|%s",
                    makanan.getNama(), makanan.getHarga(), makanan.getKategori(), makanan.getJenisMakanan());
        }
        if (item instanceof Minuman) {
            Minuman minuman = (Minuman) item;
            return String.format("MINUMAN|%s|%.0f|%s|%s",
                    minuman.getNama(), minuman.getHarga(), minuman.getKategori(), minuman.getJenisMinuman());
        }
        if (item instanceof Diskon) {
            Diskon diskon = (Diskon) item;
            return String.format("DISKON|%s|%.0f|%s|%.0f",
                    diskon.getNama(), diskon.getHarga(), diskon.getKategori(), diskon.getDiskon());
        }
        throw new IllegalArgumentException("Tipe item tidak didukung: " + item.getClass().getSimpleName());
    }

    private void buatMenuDefault(Menu menu) {
        menu.tambahItem(new Makanan("Nasi Goreng", 25000, "makanan", "Goreng"));
        menu.tambahItem(new Makanan("Ayam Bakar", 35000, "makanan", "Bakar"));
        menu.tambahItem(new Makanan("Mie Goreng", 20000, "makanan", "Goreng"));
        menu.tambahItem(new Makanan("Sate Ayam", 30000, "makanan", "Bakar"));
        menu.tambahItem(new Minuman("Es Teh", 5000, "minuman", "Dingin"));
        menu.tambahItem(new Minuman("Es Jeruk", 8000, "minuman", "Dingin"));
        menu.tambahItem(new Minuman("Kopi Susu", 12000, "minuman", "Panas"));
        menu.tambahItem(new Minuman("Air Mineral", 4000, "minuman", "Dingin"));
        menu.tambahItem(new Diskon("Diskon Member", 0, "diskon", 15));
    }

    public String getPathFile() {
        return FILE_MENU;
    }
}
