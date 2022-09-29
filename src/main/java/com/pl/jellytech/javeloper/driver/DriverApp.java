package com.pl.jellytech.javeloper.driver;

import java.util.ArrayList;
import java.util.List;

public class DriverApp
{
    static class Driver {
        private final String name;
        private final List<Integer> pointsGathered;
        private Long accountBalance;

        public Driver(String name, Long accountBalance){
            this.name = name;
            this.accountBalance = accountBalance;
            this.pointsGathered = new ArrayList<>();
        }

        public void getParkingTickets(int amount) {
            for (int i = 0; i < amount; i++) {
                earnPoints();
                loseMoney();
            }
        }

        public Long getAccountBalance() { return this.accountBalance; }
        public int getAmountOfTicketsGathered() { return this.pointsGathered.size(); }

        private void earnPoints(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Adding points to list...");
            this.pointsGathered.add(1);
        }

        private void loseMoney(){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Removing cash from account to pay fine...");
            this.accountBalance -= 1000L;
        }
    }

    static class TrafficViolation implements Runnable{

        private final Driver driver;
        private final int amountOfTickets;

        public TrafficViolation(Driver driver, int amountOfTickets){
            this.driver = driver;
            this.amountOfTickets = amountOfTickets;
        }

        @Override
        public void run() {
            driver.getParkingTickets(this.amountOfTickets);
        }
    }

    public static void main(String[] args) {
        System.out.println( "Myth 2: Parallel is ALWAYS faster!" );

        final Driver driverAdam = new Driver("Adam", 10000L);
        final Driver driverMonica = new Driver("Monica", 8000L);

        Thread parkingTicketsAdam = new Thread(new TrafficViolation(driverAdam, 2));
        Thread parkingTicketsMonica = new Thread(new TrafficViolation(driverMonica, 3));

        System.out.println("Starting threads ...");
        long start = System.currentTimeMillis();
        parkingTicketsAdam.start();


        try {
            parkingTicketsAdam.join();
            parkingTicketsMonica.start();
            parkingTicketsMonica.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println("Parking tickets distributed. Time passed: " + (System.currentTimeMillis() - start));
        System.out.println("Bank account statement Adam: " + driverAdam.getAccountBalance());
        System.out.println("Amount of tickets received: " + driverAdam.getAmountOfTicketsGathered());

        System.out.println();
        System.out.println("Bank account statement Monica: " + driverMonica.getAccountBalance());
        System.out.println("Amount of tickets received: " + driverMonica.getAmountOfTicketsGathered());


        // Synchronous
        start = System.currentTimeMillis();
        Driver driverPhoebe = new Driver("Phoebe", 8000L);
        Driver driverRoss = new Driver("Ross", 13000L);

        driverPhoebe.getParkingTickets(8);
        driverRoss.getParkingTickets(1);

        System.out.println();
        System.out.println("Parking tickets distributed. Time passed: " + (System.currentTimeMillis() - start));
        System.out.println("Bank account statement Phoebe: " + driverPhoebe.getAccountBalance());
        System.out.println("Amount of tickets received: " + driverPhoebe.getAmountOfTicketsGathered());

        System.out.println();
        System.out.println("Bank account statement Ross: " + driverRoss.getAccountBalance());
        System.out.println("Amount of tickets received: " + driverRoss.getAmountOfTicketsGathered());
    }
}
