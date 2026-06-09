package com.restaurant;

import com.restaurant.exception.MenuItemTidakDitemukanException;
import com.restaurant.model.Diskon;
import com.restaurant.model.Makanan;
import com.restaurant.model.Menu;
import com.restaurant.model.MenuItem;
import com.restaurant.model.Minuman;
import com.restaurant.model.Pesanan;
import com.restaurant.service.MenuFileService;
import com.restaurant.service.StrukFileService;
import com.restaurant.util.InputHelper;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Kelas utama aplikasi Manajemen Restoran.
 * Mengimplementasikan menu konsol, OOP, exception handling, dan operasi file.
 */
public class Main {

    private static Menu menuRestoran;
    private static Pesanan pesananAktif;
    private static final MenuFileService menuFileService = new MenuFileService();
    private static final StrukFileService strukFileService = new StrukFileService();
    private static InputHelper input;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        input = new InputHelper(scanner);

        try {
            menuRestoran = menuFileService.muatMenu();
            System.out.println("Menu restoran berhasil dimuat dari " + menuFileService.getPathFile());
        } catch (IOException e) {
            System.out.println("Gagal memuat menu: " + e.getMessage());
            menuRestoran = new Menu();
        }

        pesananAktif = new Pesanan();
        boolean berjalan = true;

        do {
            tampilkanMenuUtama();
            int pilihan = input.bacaInt("Pilih menu: ", 0, 7);

            switch (pilihan) {
                case 1:
                    menuTambahItem();
                    break;
                case 2:
                    menuTampilkanMenu();
                    break;
                case 3:
                    menuTerimaPesanan();
                    break;
                case 4:
                    menuTampilkanStruk();
                    break;
                case 5:
                    menuSimpanMuatMenu();
                    break;
                case 6:
                    menuSimpanMuatStruk();
                    break;
                case 0:
                    System.out.println("\nTerima kasih. Program selesai.");
                    berjalan = false;
                    break;
                default:
                    System.out.println("\nPilihan tidak valid. Silakan coba lagi.");
                    break;
            }
        } while (berjalan);

        scanner.close();
    }

    private static void tampilkanMenuUtama() {
        System.out.println("\n========================================");
        System.out.println("     MANAJEMEN RESTORAN - TUGAS 3");
        System.out.println("========================================");
        System.out.println("1. Tambah Item ke Menu");
        System.out.println("2. Tampilkan Menu Restoran");
        System.out.println("3. Terima Pesanan Pelanggan");
        System.out.println("4. Tampilkan Struk Pesanan");
        System.out.println("5. Simpan / Muat Menu dari File");
        System.out.println("6. Simpan / Muat Struk ke File");
        System.out.println("0. Keluar");
        System.out.println("========================================");
    }

    private static void menuTambahItem() {
        System.out.println("\n--- TAMBAH ITEM KE MENU ---");
        System.out.println("1. Makanan");
        System.out.println("2. Minuman");
        System.out.println("3. Diskon");
        System.out.println("0. Kembali");

        int tipe = input.bacaInt("Pilih jenis item: ", 0, 3);
        if (tipe == 0) {
            return;
        }

        String nama = input.bacaString("Nama item: ");
        if (nama.isEmpty()) {
            System.out.println("Nama tidak boleh kosong.");
            return;
        }

        try {
            menuRestoran.cariItemByNama(nama);
            System.out.println("Item dengan nama tersebut sudah ada.");
            return;
        } catch (MenuItemTidakDitemukanException ignored) {
            // nama belum ada, lanjutkan
        }

        MenuItem itemBaru = null;

        switch (tipe) {
            case 1:
                double hargaMakanan = input.bacaDouble("Harga (Rp): ", 1, Double.MAX_VALUE);
                String jenisMakanan = input.bacaString("Jenis makanan (mis. Goreng, Bakar): ");
                itemBaru = new Makanan(nama, hargaMakanan, "makanan", jenisMakanan);
                break;
            case 2:
                double hargaMinuman = input.bacaDouble("Harga (Rp): ", 1, Double.MAX_VALUE);
                String jenisMinuman = input.bacaString("Jenis minuman (mis. Dingin, Panas): ");
                itemBaru = new Minuman(nama, hargaMinuman, "minuman", jenisMinuman);
                break;
            case 3:
                double persenDiskon = input.bacaDouble("Persentase diskon (0-100): ", 0, 100);
                itemBaru = new Diskon(nama, 0, "diskon", persenDiskon);
                break;
            default:
                break;
        }

        if (itemBaru != null) {
            menuRestoran.tambahItem(itemBaru);
            System.out.println("Item '" + nama + "' berhasil ditambahkan.");

            try {
                menuFileService.simpanMenu(menuRestoran);
                System.out.println("Menu otomatis disimpan ke " + menuFileService.getPathFile());
            } catch (IOException e) {
                System.out.println("Gagal menyimpan menu: " + e.getMessage());
            }
        }
    }

    private static void menuTampilkanMenu() {
        System.out.println("\n========== DAFTAR MENU RESTORAN ==========");
        menuRestoran.tampilkanSemuaMenu();
        System.out.println("==========================================");
    }

    private static void menuTerimaPesanan() {
        if (menuRestoran.isKosong()) {
            System.out.println("\nDaftar menu kosong. Tambahkan item terlebih dahulu.");
            return;
        }

        pesananAktif = new Pesanan();

        System.out.println("\n--- TERIMA PESANAN PELANGGAN ---");
        menuRestoran.tampilkanSemuaMenu();
        System.out.println("\nMasukkan nama item yang ingin dipesan.");
        System.out.println("Ketik 'selesai' jika sudah selesai memesan.\n");

        while (true) {
            String namaInput = input.bacaString("Nama item: ");
            if (namaInput.equalsIgnoreCase("selesai")) {
                break;
            }

            try {
                MenuItem item = menuRestoran.cariItemByNama(namaInput);
                int jumlah = input.bacaInt("Jumlah: ", 1, Integer.MAX_VALUE);
                pesananAktif.tambahItem(item, jumlah);
                System.out.println("Item berhasil ditambahkan ke pesanan.");
            } catch (MenuItemTidakDitemukanException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }

        if (pesananAktif.isKosong()) {
            System.out.println("\nTidak ada pesanan yang dicatat.");
        } else {
            System.out.printf("\nPesanan tercatat. Total sementara: Rp %,.0f%n", pesananAktif.hitungTotal());
        }
    }

    private static void menuTampilkanStruk() {
        if (pesananAktif == null || pesananAktif.isKosong()) {
            System.out.println("\nBelum ada pesanan aktif. Silakan terima pesanan terlebih dahulu.");
            return;
        }
        pesananAktif.cetakStruk();
    }

    private static void menuSimpanMuatMenu() {
        System.out.println("\n--- SIMPAN / MUAT MENU ---");
        System.out.println("1. Simpan menu ke file");
        System.out.println("2. Muat menu dari file");
        System.out.println("0. Kembali");

        int pilihan = input.bacaInt("Pilih: ", 0, 2);

        switch (pilihan) {
            case 1:
                try {
                    menuFileService.simpanMenu(menuRestoran);
                    System.out.println("Menu berhasil disimpan ke " + menuFileService.getPathFile());
                } catch (IOException e) {
                    System.out.println("Gagal menyimpan menu: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    menuRestoran = menuFileService.muatMenu();
                    System.out.println("Menu berhasil dimuat dari " + menuFileService.getPathFile());
                    menuTampilkanMenu();
                } catch (IOException e) {
                    System.out.println("Gagal memuat menu: " + e.getMessage());
                }
                break;
            default:
                break;
        }
    }

    private static void menuSimpanMuatStruk() {
        System.out.println("\n--- SIMPAN / MUAT STRUK ---");
        System.out.println("1. Simpan struk pesanan aktif");
        System.out.println("2. Muat dan tampilkan struk dari file");
        System.out.println("0. Kembali");

        int pilihan = input.bacaInt("Pilih: ", 0, 2);

        switch (pilihan) {
            case 1:
                if (pesananAktif == null || pesananAktif.isKosong()) {
                    System.out.println("Belum ada pesanan aktif untuk disimpan.");
                    return;
                }
                try {
                    String namaFile = strukFileService.simpanStruk(pesananAktif);
                    System.out.println("Struk berhasil disimpan: data/struk/" + namaFile);
                } catch (IOException e) {
                    System.out.println("Gagal menyimpan struk: " + e.getMessage());
                }
                break;
            case 2:
                try {
                    List<String> daftar = strukFileService.daftarFileStruk();
                    if (daftar.isEmpty()) {
                        System.out.println("Belum ada file struk tersimpan.");
                        return;
                    }

                    System.out.println("\nDaftar file struk:");
                    for (int i = 0; i < daftar.size(); i++) {
                        System.out.printf("%2d. %s%n", i + 1, daftar.get(i));
                    }

                    int nomor = input.bacaInt("Pilih nomor file: ", 1, daftar.size());
                    String isi = strukFileService.muatStruk(daftar.get(nomor - 1));
                    System.out.println("\n" + isi);
                } catch (IOException e) {
                    System.out.println("Gagal memuat struk: " + e.getMessage());
                }
                break;
            default:
                break;
        }
    }
}
