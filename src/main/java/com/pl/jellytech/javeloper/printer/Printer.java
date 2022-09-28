package com.pl.jellytech.javeloper.printer;

public class Printer {
    public void print(String fileName, int pageAmount){
        for(int i=0; i<pageAmount; i++){
            System.out.println("Printing file " + fileName + ". Page " + (i+1) + "/" + pageAmount);
            // Assume printing the file takes 1s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
