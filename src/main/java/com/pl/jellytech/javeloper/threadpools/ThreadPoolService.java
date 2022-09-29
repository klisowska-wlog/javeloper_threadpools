package com.pl.jellytech.javeloper.threadpools;

import com.pl.jellytech.javeloper.printer.PrintThreadRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolService {

    public void printWithExecutorService(List<PrintThreadRunnable> printTasks){
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        ExecutorService singleThread = Executors.newSingleThreadExecutor();
        ExecutorService cachedPool = Executors.newCachedThreadPool();
        ExecutorService scheduledPool = Executors.newScheduledThreadPool(5, Executors.defaultThreadFactory());


        long start = System.currentTimeMillis();
        List<Future<?>> printTaskFutures = new ArrayList<>();
        for(PrintThreadRunnable printTask : printTasks){
            Future<?> printFuture = fixedPool.submit(printTask);
            printTaskFutures.add(printFuture);
        }

        printTaskFutures.forEach(pt -> {
            try {
                Object result = pt.get();
                System.out.println("Print task finished");
            } catch (InterruptedException e) {
                System.out.println("Print task finished");
                e.printStackTrace();
            } catch (ExecutionException e) {
                System.out.println("Print task threw an exception");
                e.printStackTrace();
            }
        });

        System.out.println("Total printing with pool took: " + (System.currentTimeMillis() - start) + " ms");
    }

    public void printWithForkJoinPool(){
        class CustomForkJoinTask extends RecursiveTask<Integer>{
            private static final int threshold = 3;
            private final int[] workload;

            public CustomForkJoinTask(int[] workload){
                this.workload = workload;
            }

            @Override
            protected Integer compute() {
                if(workload.length >= threshold){
                    System.out.println("Splitting task - size " + workload.length +  " exceeds threshold " + threshold);
                    return ForkJoinTask.invokeAll(createSubTasks()).stream().mapToInt(ForkJoinTask::join).sum();
                }else{
                    System.out.println("Calculating sum - size " + workload.length +  " does not exceed threshold " + threshold);
                    return getSum(workload);
                }
            }

            private List<CustomForkJoinTask> createSubTasks(){
                // divide the data
                CustomForkJoinTask partOne = new CustomForkJoinTask(Arrays.copyOfRange(workload, 0, workload.length / 2));
                CustomForkJoinTask partTwo = new CustomForkJoinTask(Arrays.copyOfRange(workload, workload.length / 2, workload.length));

                return Arrays.asList(partOne, partTwo);
            }
            private int getSum(int[] values){
                int sum = 0;
                for (int value : values) {
                    sum += value;
                }
                return sum;
            }
        }

        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        ForkJoinPool commonForkJoinPool = ForkJoinPool.commonPool();

        long start = System.currentTimeMillis();

        CustomForkJoinTask customForkJoinTask = new CustomForkJoinTask(new int[] {1,2,3,4,5,6,7,8,9,10,11,12});
        commonForkJoinPool.execute(customForkJoinTask);

        int result = customForkJoinTask.join();

        System.out.println("Result calculated: " + result);
        System.out.println("Total printing with pool took: " + (System.currentTimeMillis() - start) + " ms");
    }
}
