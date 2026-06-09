package com.restaurant.util;

import java.util.Scanner;

/**
 * Utilitas untuk membaca input pengguna dengan validasi.
 */
public class InputHelper {

    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String bacaString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int bacaInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                int nilai = Integer.parseInt(input);
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                System.out.printf("Input harus antara %d dan %d. Silakan coba lagi.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
            }
        }
    }

    public double bacaDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            try {
                double nilai = Double.parseDouble(input);
                if (nilai >= min && nilai <= max) {
                    return nilai;
                }
                System.out.printf("Input harus antara %.0f dan %.0f. Silakan coba lagi.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Silakan coba lagi.");
            }
        }
    }

    public String bacaPilihanYaTidak(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("Ya") || input.equalsIgnoreCase("Tidak")) {
                return input;
            }
            System.out.println("Input tidak valid. Ketik 'Ya' atau 'Tidak'.");
        }
    }
}
