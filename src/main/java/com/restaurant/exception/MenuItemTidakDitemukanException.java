package com.restaurant.exception;

/**
 * Exception ketika item menu tidak ditemukan dalam daftar menu.
 */
public class MenuItemTidakDitemukanException extends Exception {

    public MenuItemTidakDitemukanException(String namaMenu) {
        super("Menu '" + namaMenu + "' tidak ditemukan dalam daftar menu restoran.");
    }
}
