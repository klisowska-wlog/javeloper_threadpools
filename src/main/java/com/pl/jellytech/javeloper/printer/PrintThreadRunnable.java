package com.pl.jellytech.javeloper.printer;

public class PrintThreadRunnable implements Runnable{
    private final String fileName;
    private final int pageAmount;

    public PrintThreadRunnable(String fileName, int pageAmount){
        this.fileName = fileName;
        this.pageAmount = pageAmount;
    }

    @Override
    public void run() {
        // ### 1. Print directly in thread
        for(int i=0; i<this.pageAmount; i++){
            System.out.println("Printing file " + this.fileName + ". Page " + (i+1) + "/" + this.pageAmount);
            // Assume printing the file takes 1s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
