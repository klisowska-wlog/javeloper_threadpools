package com.pl.jellytech.javeloper.threads;

public class TtlRunnable implements Runnable {

    private final String threadName;
    private final int ttl;

    public TtlRunnable(String threadName, int ttl){
        this.threadName = threadName;
        this.ttl = ttl;
    }

    @Override
    public void run() {
        for(int i=0; i<ttl; i++){
            System.out.println("Runnable " + this.threadName + " says hello on " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public String getThreadName() {
        return threadName;
    }

    public int getTtl() {
        return ttl;
    }
}
