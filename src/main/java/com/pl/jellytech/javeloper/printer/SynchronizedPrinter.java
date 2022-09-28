package com.pl.jellytech.javeloper.printer;

public class SynchronizedPrinter extends Printer{
    @Override
    public void print(String fileName, int pageAmount){
        synchronizedPrint(fileName, pageAmount);
    }

    private synchronized void synchronizedPrint(String fileName, int pageAmount){
        super.print(fileName, pageAmount);
    }
}
