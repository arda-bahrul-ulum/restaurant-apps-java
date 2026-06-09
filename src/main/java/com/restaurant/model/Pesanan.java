package com.restaurant.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Kelas untuk mencatat pesanan pelanggan.
 */
public class Pesanan {

    public static final double BATAS_DISKON_OTOMATIS = 100_000;
    public static final double PERSEN_DISKON_OTOMATIS = 10.0;

    private final ArrayList<OrderItem> items;

    public Pesanan() {
        this.items = new ArrayList<>();
    }

    public void tambahItem(MenuItem menu, int jumlah) {
        for (OrderItem orderItem : items) {
            if (orderItem.getItem().getNama().equalsIgnoreCase(menu.getNama())) {
                orderItem.setJumlah(orderItem.getJumlah() + jumlah);
                return;
            }
        }
        items.add(new OrderItem(menu, jumlah));
    }

    public double hitungSubtotal() {
        double subtotal = 0;
        for (OrderItem orderItem : items) {
            if (!(orderItem.getItem() instanceof Diskon)) {
                subtotal += orderItem.getSubtotal();
            }
        }
        return subtotal;
    }

    public double hitungPotonganDiskonItem() {
        double subtotal = hitungSubtotal();
        double potongan = 0;
        for (OrderItem orderItem : items) {
            if (orderItem.getItem() instanceof Diskon) {
                Diskon diskon = (Diskon) orderItem.getItem();
                potongan += diskon.hitungPotongan(subtotal);
            }
        }
        return potongan;
    }

    public double hitungDiskonOtomatis() {
        double subtotal = hitungSubtotal();
        if (subtotal > BATAS_DISKON_OTOMATIS) {
            return subtotal * (PERSEN_DISKON_OTOMATIS / 100.0);
        }
        return 0;
    }

    public double hitungTotal() {
        double subtotal = hitungSubtotal();
        double potonganDiskonItem = hitungPotonganDiskonItem();
        double diskonOtomatis = hitungDiskonOtomatis();
        double total = subtotal - potonganDiskonItem - diskonOtomatis;
        return total < 0 ? 0 : total;
    }

    public boolean isKosong() {
        return items.isEmpty();
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void cetakStruk() {
        System.out.println("\n================================================");
        System.out.println("              STRUK PESANAN");
        System.out.println("================================================");

        for (OrderItem orderItem : items) {
            MenuItem item = orderItem.getItem();
            if (item instanceof Diskon) {
                Diskon diskon = (Diskon) item;
                System.out.printf("  %-20s Diskon %.0f%%%n", item.getNama(), diskon.getDiskon());
            } else {
                System.out.printf("  %-18s x%-2d @ Rp %,.0f = Rp %,.0f%n",
                        item.getNama(),
                        orderItem.getJumlah(),
                        item.getHarga(),
                        orderItem.getSubtotal());
            }
        }

        double subtotal = hitungSubtotal();
        double potonganDiskonItem = hitungPotonganDiskonItem();
        double diskonOtomatis = hitungDiskonOtomatis();
        double total = hitungTotal();

        System.out.println("------------------------------------------------");
        System.out.printf("  Subtotal:                    Rp %,.0f%n", subtotal);

        if (potonganDiskonItem > 0) {
            System.out.printf("  Potongan Diskon Item:       -Rp %,.0f%n", potonganDiskonItem);
        }

        if (diskonOtomatis > 0) {
            System.out.printf("  Diskon Otomatis (10%%):      -Rp %,.0f%n", diskonOtomatis);
        } else if (subtotal > 0 && subtotal <= BATAS_DISKON_OTOMATIS) {
            System.out.println("  Diskon Otomatis: tidak berlaku (subtotal <= Rp 100.000)");
        }

        System.out.println("------------------------------------------------");
        System.out.printf("  TOTAL BAYAR:                 Rp %,.0f%n", total);
        System.out.println("================================================");
        System.out.println("  Terima kasih atas kunjungan Anda!");
        System.out.println("================================================");
    }

    public String keStringStruk() {
        StringBuilder sb = new StringBuilder();
        sb.append("================================================\n");
        sb.append("              STRUK PESANAN\n");
        sb.append("================================================\n");

        for (OrderItem orderItem : items) {
            MenuItem item = orderItem.getItem();
            if (item instanceof Diskon) {
                Diskon diskon = (Diskon) item;
                sb.append(String.format("  %-20s Diskon %.0f%%\n", item.getNama(), diskon.getDiskon()));
            } else {
                sb.append(String.format("  %-18s x%-2d @ Rp %,.0f = Rp %,.0f%n",
                        item.getNama(),
                        orderItem.getJumlah(),
                        item.getHarga(),
                        orderItem.getSubtotal()));
            }
        }

        double subtotal = hitungSubtotal();
        double potonganDiskonItem = hitungPotonganDiskonItem();
        double diskonOtomatis = hitungDiskonOtomatis();
        double total = hitungTotal();

        sb.append("------------------------------------------------\n");
        sb.append(String.format("  Subtotal:                    Rp %,.0f%n", subtotal));

        if (potonganDiskonItem > 0) {
            sb.append(String.format("  Potongan Diskon Item:       -Rp %,.0f%n", potonganDiskonItem));
        }

        if (diskonOtomatis > 0) {
            sb.append(String.format("  Diskon Otomatis (10%%):      -Rp %,.0f%n", diskonOtomatis));
        } else if (subtotal > 0 && subtotal <= BATAS_DISKON_OTOMATIS) {
            sb.append("  Diskon Otomatis: tidak berlaku (subtotal <= Rp 100.000)\n");
        }

        sb.append("------------------------------------------------\n");
        sb.append(String.format("  TOTAL BAYAR:                 Rp %,.0f%n", total));
        sb.append("================================================\n");
        sb.append("  Terima kasih atas kunjungan Anda!\n");
        sb.append("================================================\n");

        return sb.toString();
    }
}
