package com.restaurant.service;

import com.restaurant.model.Pesanan;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Layanan operasi file untuk menyimpan dan memuat struk pesanan.
 */
public class StrukFileService {

    private static final String FOLDER_STRUK = "data/struk";
    private static final DateTimeFormatter FORMAT_WAKTU = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    public String simpanStruk(Pesanan pesanan) throws IOException {
        Path folder = Paths.get(FOLDER_STRUK);
        Files.createDirectories(folder);

        String namaFile = "struk_" + LocalDateTime.now().format(FORMAT_WAKTU) + ".txt";
        Path path = folder.resolve(namaFile);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(pesanan.keStringStruk());
        }

        return namaFile;
    }

    public String muatStruk(String namaFile) throws IOException {
        Path path = Paths.get(FOLDER_STRUK, namaFile);
        if (!Files.exists(path)) {
            throw new IOException("File struk tidak ditemukan: " + namaFile);
        }
        return Files.lines(path).collect(Collectors.joining(System.lineSeparator()));
    }

    public List<String> daftarFileStruk() throws IOException {
        Path folder = Paths.get(FOLDER_STRUK);
        if (!Files.exists(folder)) {
            return new ArrayList<>();
        }
        return Files.list(folder)
                .filter(Files::isRegularFile)
                .map(path -> path.getFileName().toString())
                .filter(nama -> nama.endsWith(".txt"))
                .sorted()
                .collect(Collectors.toList());
    }
}
