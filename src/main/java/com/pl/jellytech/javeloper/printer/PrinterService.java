package com.pl.jellytech.javeloper.printer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrinterService {
    public void printFiles(Map<String, Integer> files) {

//        System.out.println();
//        System.out.println("# Executing with threads");
//        printWithThreads(files);

        System.out.println("# Executing with shared printer object");
        printWithSharedPrinter(files);

        System.out.println();
        System.out.println("# Executing with synchronized printer method");
        printWithSynchronizedPrinter(files);
    }

    private void printWithThreads(Map<String, Integer> files) {

        long start = System.currentTimeMillis();
        // will keep a reference so that we can wait for all to finish to track performance
        List<Thread> threadsStarted = new ArrayList<>();

        for(String fileName : files.keySet()){
            PrintThreadRunnable printRunnable = new PrintThreadRunnable(fileName, files.get(fileName));
            Thread printThread = new Thread(printRunnable);
            System.out.println(">>> Starting thread " + printThread.getName());
            threadsStarted.add(printThread);
            printThread.start();
        }

        threadsStarted.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Print with threads finished processing all.");
        System.out.println("Time passed: " + (System.currentTimeMillis() - start) + " ms");
    }

    private void printWithSharedPrinter(Map<String, Integer> files){

        // synchronized method
        Printer printer = new SynchronizedPrinter();
        printWithPrinter(files, printer);
    }

    private void printWithSynchronizedPrinter(Map<String, Integer> files){

        // shared object
        Printer printer = new Printer();
        printWithPrinter(files, printer);
    }

    private void printWithPrinter(Map<String, Integer> files, Printer printer) {
        class PrinterRunnable implements Runnable{
            private final String fileName;
            private final int pageAmount;
            private final Printer printer;

            public PrinterRunnable(String fileName, int pageAmount, Printer printer){
                this.fileName = fileName;
                this.pageAmount = pageAmount;
                this.printer = printer;
            }

            @Override
            public void run() {
                // ### 2. Invokes shared printer method
                this.printer.print(this.fileName, this.pageAmount);
            }
        }

        long start = System.currentTimeMillis();
        // will keep a reference so that we can wait for all to finish to track performance
        List<Thread> threadsStarted = new ArrayList<>();

        for(String fileName : files.keySet()){
            PrinterRunnable printRunnable = new PrinterRunnable(fileName, files.get(fileName), printer);
            Thread printThread = new Thread(printRunnable);
            threadsStarted.add(printThread);
            printThread.start();
        }

        threadsStarted.forEach(thread -> {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Print with printer finished processing all.");
        System.out.println("Time passed: " + (System.currentTimeMillis() - start) + " ms");
    }
}
