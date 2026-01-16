package Concurrency;

import java.util.concurrent.Semaphore;

public class LoginService {
    // Only 3 connections allowed at once!
    private final Semaphore dbConnections = new Semaphore(3);

    public void loginPremium(String user) {
        System.out.println("PREMIUM user " + user + " is waiting for access...");
        try {
            // TODO 1: Wait here until a permit is available (Blocking)
            dbConnections.acquire();

            System.out.println("✅ PREMIUM user " + user + " got a connection!");
            performDatabaseTask();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // TODO 2: Release the connection
            dbConnections.release(1);
            System.out.println("👋 PREMIUM user " + user + " logged out.");
        }
    }

    public void loginGuest(String user) {
        System.out.println("GUEST user " + user + " is trying to access...");

        // TODO 3: Check if a permit is available immediately (Non-Blocking)
        boolean accessGranted = false; // Change this line
        accessGranted = dbConnections.tryAcquire();

        if (accessGranted) {
            try {
                System.out.println("✅ GUEST user " + user + " got a connection!");
                performDatabaseTask();
            } finally {
                // TODO 4: Release the connection
                dbConnections.release(1);
                System.out.println("👋 GUEST user " + user + " logged out.");
            }
        } else {
            System.out.println("⛔ GUEST user " + user + " rejected. Server busy.");
        }
    }

    // Simulates work taking 2 seconds
    private void performDatabaseTask() {
        try { Thread.sleep(2000); } catch (InterruptedException ignored) {}
    }

    public static void main(String[] args) {
        LoginService service = new LoginService();

        // 1. Fill the slots with 3 Premium users
        for (int i = 1; i <= 3; i++) {
            final int id = i;
            new Thread(() -> service.loginPremium("P-" + id)).start();
        }

        // 2. Try to add a 4th Premium user (Should wait)
        new Thread(() -> service.loginPremium("P-WAITING")).start();

        // 3. Try to add a Guest user (Should be rejected immediately)
        new Thread(() -> service.loginGuest("G-REJECT")).start();
    }
}