package com.pl.jellytech.javeloper.printer;

import java.util.HashMap;
import java.util.Map;

public class PrinterApp {
    private static final Map<String, Integer> filesToPrint = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("######## Printer application started! ######## Main thread ID: " + Thread.currentThread().getId());
        filesToPrint.put("Javeloper.pdf", 10);
        filesToPrint.put("JellyTech.pdf", 10);

        long start = System.currentTimeMillis();

        PrinterService printerService = new PrinterService();
        printerService.printFiles(filesToPrint);

        System.out.println( "######## Printer application finished! ######## Main thread ID: " + Thread.currentThread().getId());
        System.out.println("Time passed: " + (System.currentTimeMillis() - start));
    }
}
