package com.pl.jellytech.javeloper.threadpools;

import com.pl.jellytech.javeloper.printer.PrintThreadRunnable;

import java.util.Arrays;

public class ThreadPoolMainApp {

    public static void main (String[] args){
        PrintThreadRunnable javeloperFile = new PrintThreadRunnable("Javeloper.pdf", 10);
        PrintThreadRunnable jellyTechFile = new PrintThreadRunnable("JellyTech.pdf", 10);

        ThreadPoolService threadPoolService = new ThreadPoolService();

        // ### 1. ExecutorService
//        threadPoolService.printWithExecutorService(Arrays.asList(javeloperFile, jellyTechFile));

        // ### 2. ForkJoinPool
        threadPoolService.printWithForkJoinPool();


        System.out.println("## All tasks submitted! ##");
        System.out.println("##### Application completed! #####");
    }


}
