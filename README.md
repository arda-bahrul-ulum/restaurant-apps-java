# Restaurant Apps Java

Aplikasi konsol Java untuk manajemen restoran — Tugas Praktik 3 Pemrograman Berbasis Objek.

Program ini mengelola menu restoran (makanan, minuman, diskon), menerima pesanan pelanggan, menghitung total biaya dengan diskon, menampilkan struk, serta menyimpan dan memuat data melalui operasi file.

## Daftar Isi

- [Fitur](#fitur)
- [Konsep Pemrograman yang Diimplementasikan](#konsep-pemrograman-yang-diimplementasikan)
- [Struktur Proyek](#struktur-proyek)
- [Arsitektur Kelas](#arsitektur-kelas)
- [Persyaratan Sistem](#persyaratan-sistem)
- [Cara Menjalankan](#cara-menjalankan)
- [Menu Utama Aplikasi](#menu-utama-aplikasi)
- [Format File Data](#format-file-data)
- [Logika Perhitungan Diskon](#logika-perhitungan-diskon)
- [Exception Handling](#exception-handling)
- [Contoh Skenario Penggunaan](#contoh-skenario-penggunaan)
- [Panduan Demo Video](#panduan-demo-video)

## Fitur

- Menambahkan item menu baru (Makanan, Minuman, atau Diskon)
- Menampilkan daftar menu restoran dikelompokkan per kategori
- Menerima pesanan pelanggan dengan validasi nama menu
- Menghitung total biaya dengan diskon item dan diskon otomatis
- Menampilkan struk pesanan di layar
- Menyimpan dan memuat daftar menu dari file teks
- Menyimpan dan memuat struk pesanan ke/dari file teks
- Penanganan exception untuk input tidak valid dan menu tidak ditemukan

## Konsep Pemrograman yang Diimplementasikan

| Konsep | Implementasi |
|--------|--------------|
| **Abstraksi** | Kelas abstrak `MenuItem` dengan metode abstrak `tampilMenu()` |
| **Inheritance** | `Makanan`, `Minuman`, `Diskon` sebagai subkelas `MenuItem` |
| **Encapsulation** | Semua atribut kelas bersifat `private` dengan getter/setter |
| **Polymorphism** | Override `tampilMenu()` di setiap subkelas; koleksi `ArrayList<MenuItem>` |
| **Exception** | `MenuItemTidakDitemukanException`, `IOException`, `NumberFormatException` |
| **I/O & File** | `MenuFileService`, `StrukFileService`, `Scanner` |
| **Struktur keputusan** | `switch-case`, `if-else`, `if-else if` |
| **Struktur pengulangan** | `do-while`, `while`, `for-each` |
| **Array & String** | `ArrayList`, `String.split()`, `String.format()`, parsing file |

## Struktur Proyek

```
restaurant-apps-java/
├── pom.xml
├── run.sh / run.bat          # Skrip jalankan tanpa Maven
├── README.md
├── data/
│   ├── menu.txt              # Data menu restoran
│   └── struk/                # Folder struk tersimpan
└── src/main/java/com/restaurant/
    ├── Main.java
    ├── model/
    │   ├── MenuItem.java     # Kelas abstrak dasar
    │   ├── Makanan.java
    │   ├── Minuman.java
    │   ├── Diskon.java
    │   ├── Menu.java
    │   ├── OrderItem.java
    │   └── Pesanan.java
    ├── exception/
    │   └── MenuItemTidakDitemukanException.java
    ├── service/
    │   ├── MenuFileService.java
    │   └── StrukFileService.java
    └── util/
        └── InputHelper.java
```

## Arsitektur Kelas

```
MenuItem (abstract)
├── Makanan     → atribut: jenisMakanan
├── Minuman     → atribut: jenisMinuman
└── Diskon      → atribut: diskon (persentase)

Menu            → ArrayList<MenuItem> daftarItem
Pesanan         → ArrayList<OrderItem> items
OrderItem       → MenuItem item + int jumlah
```

### Kelas `MenuItem` (Abstract)

Kelas dasar untuk semua item menu dengan atribut:

- `nama` (String)
- `harga` (double)
- `kategori` (String)

Metode abstrak `tampilMenu()` diimplementasikan oleh setiap subkelas.

### Kelas `Makanan` dan `Minuman`

Subkelas `MenuItem` dengan atribut tambahan:

- `Makanan` → `jenisMakanan` (contoh: Goreng, Bakar)
- `Minuman` → `jenisMinuman` (contoh: Dingin, Panas)

### Kelas `Diskon`

Subkelas `MenuItem` untuk promo diskon dengan atribut `diskon` (persentase 0–100). Metode `hitungPotongan(subtotal)` menghitung nilai potongan dari subtotal pesanan.

### Kelas `Menu`

Mengelola semua item menu menggunakan `ArrayList<MenuItem>`. Menyediakan operasi tambah item, cari item by nama, dan tampilkan menu dengan polymorphism.

### Kelas `Pesanan`

Mencatat pesanan pelanggan menggunakan `ArrayList<OrderItem>`. Menghitung subtotal, potongan diskon, dan total akhir.

## Persyaratan Sistem

- Java 11 atau lebih baru (JDK)
- Maven 3.x (opsional, untuk build dengan Maven)
- Terminal / Command Prompt / Git Bash

## Cara Menjalankan

### Opsi 1: Skrip (tanpa Maven)

**Git Bash / Linux / macOS:**

```bash
./run.sh
```

**Windows CMD / PowerShell:**

```bat
run.bat
```

### Opsi 2: Manual (tanpa Maven)

```bash
mkdir -p target/classes
javac -d target/classes -encoding UTF-8 $(find src/main/java -name "*.java")
java -cp target/classes com.restaurant.Main
```

### Opsi 3: Maven

```bash
mvn compile exec:java
```

> Jika muncul error `mvn: command not found`, gunakan Opsi 1 atau 2. Maven belum terpasang di sistem.

## Menu Utama Aplikasi

```
========================================
     MANAJEMEN RESTORAN - TUGAS 3
========================================
1. Tambah Item ke Menu
2. Tampilkan Menu Restoran
3. Terima Pesanan Pelanggan
4. Tampilkan Struk Pesanan
5. Simpan / Muat Menu dari File
6. Simpan / Muat Struk ke File
0. Keluar
========================================
```

### 1. Tambah Item ke Menu

Pilih jenis item:

| Pilihan | Jenis | Input yang diperlukan |
|---------|-------|----------------------|
| 1 | Makanan | Nama, harga, jenis makanan |
| 2 | Minuman | Nama, harga, jenis minuman |
| 3 | Diskon | Nama promo, persentase diskon (0–100) |

Menu otomatis disimpan ke `data/menu.txt` setelah penambahan.

### 2. Tampilkan Menu Restoran

Menampilkan semua item dikelompokkan: MAKANAN, MINUMAN, DISKON. Setiap item memanggil `tampilMenu()` sesuai tipenya (polymorphism).

### 3. Terima Pesanan Pelanggan

1. Menu ditampilkan
2. Masukkan nama item yang dipesan
3. Masukkan jumlah
4. Ulangi hingga mengetik `selesai`
5. Jika nama item tidak ditemukan, muncul pesan error tanpa menghentikan program

### 4. Tampilkan Struk Pesanan

Menampilkan rincian item, subtotal, potongan diskon, dan total bayar.

### 5. Simpan / Muat Menu dari File

- **Simpan** — menulis daftar menu ke `data/menu.txt`
- **Muat** — membaca menu dari `data/menu.txt` (membuat data default jika file belum ada)

### 6. Simpan / Muat Struk ke File

- **Simpan** — menyimpan struk pesanan aktif ke `data/struk/struk_YYYYMMDD_HHmmss.txt`
- **Muat** — menampilkan struk dari file yang sudah tersimpan

## Format File Data

### `data/menu.txt`

Format pipe-delimited per baris:

```
TIPE|nama|harga|kategori|atribut_tambahan
```

Contoh:

```
MAKANAN|Nasi Goreng|25000|makanan|Goreng
MINUMAN|Es Teh|5000|minuman|Dingin
DISKON|Diskon Member|0|diskon|15
```

| Tipe | Atribut tambahan |
|------|------------------|
| `MAKANAN` | jenis makanan |
| `MINUMAN` | jenis minuman |
| `DISKON` | persentase diskon |

### `data/struk/struk_*.txt`

File struk berisi rincian pesanan dalam format teks yang sama dengan tampilan di layar, termasuk subtotal, potongan, dan total bayar.

## Logika Perhitungan Diskon

Perhitungan total mengikuti urutan berikut:

```
1. Subtotal = jumlah harga semua item makanan & minuman
2. Potongan diskon item = dari item Diskon yang dipilih pelanggan
3. Diskon otomatis 10% = jika subtotal > Rp 100.000
4. Total = Subtotal - Potongan diskon item - Diskon otomatis
```

### Contoh Perhitungan

Pesanan: Nasi Goreng x2 + Ayam Bakar x3 + Diskon Member (15%)

| Komponen | Nilai |
|----------|-------|
| Subtotal | Rp 155.000 |
| Potongan Diskon Member (15%) | -Rp 23.250 |
| Diskon Otomatis (10%) | -Rp 15.500 |
| **Total Bayar** | **Rp 116.250** |

## Exception Handling

| Skenario | Penanganan |
|----------|------------|
| Menu tidak ditemukan saat pesanan | `MenuItemTidakDitemukanException` ditangkap, pesan error ditampilkan |
| Input angka tidak valid | `NumberFormatException` ditangkap di `InputHelper`, minta input ulang |
| File menu tidak ada | Buat menu default dan simpan otomatis |
| Gagal baca/tulis file | `IOException` ditangkap, pesan error ditampilkan tanpa crash |

## Contoh Skenario Penggunaan

### Skenario 1: Lihat menu dan buat pesanan

```
Pilih menu: 2          → Tampilkan menu
Pilih menu: 3          → Terima pesanan
Nama item: Nasi Goreng
Jumlah: 2
Nama item: Ayam Bakar
Jumlah: 3
Nama item: selesai
Pilih menu: 4          → Tampilkan struk
```

### Skenario 2: Tambah menu baru

```
Pilih menu: 1          → Tambah item
Pilih jenis: 1         → Makanan
Nama item: Rendang
Harga: 40000
Jenis makanan: Kuah
```

### Skenario 3: Uji exception

```
Pilih menu: 3
Nama item: Menu Tidak Ada
→ Error: Menu 'Menu Tidak Ada' tidak ditemukan dalam daftar menu restoran.
```

### Skenario 4: Simpan struk

```
Pilih menu: 6
Pilih: 1               → Simpan struk
→ Struk berhasil disimpan: data/struk/struk_YYYYMMDD_HHmmss.txt
```

## Panduan Demo Video

Susunan demo untuk rekaman penjelasan (maks. 15 menit):

| Segmen | Durasi | Isi |
|--------|--------|-----|
| Pengenalan & struktur kelas | ~2 menit | Jelaskan inheritance `MenuItem` → 3 subkelas |
| Encapsulation & polymorphism | ~2 menit | Atribut private, demo `tampilMenu()` per tipe |
| Tambah menu + tampilkan | ~2 menit | Tambah makanan baru, tampilkan menu |
| Proses pesanan + diskon | ~4 menit | Pesan item, tambah diskon, tunjukkan diskon otomatis |
| Struk & file I/O | ~3 menit | Cetak struk, simpan/muat menu dan struk |
| Exception handling | ~2 menit | Input menu salah → exception tertangkap |

## Data Awal (Default Menu)

Saat pertama kali dijalankan, aplikasi memuat `data/menu.txt` yang berisi:

**Makanan:** Nasi Goreng, Ayam Bakar, Mie Goreng, Sate Ayam

**Minuman:** Es Teh, Es Jeruk, Kopi Susu, Air Mineral

**Diskon:** Diskon Member 15%

Jika file tidak ada, data default dibuat otomatis.

## Lisensi

Proyek ini dibuat untuk keperluan tugas praktik mata kuliah Pemrograman Berbasis Desktop.
