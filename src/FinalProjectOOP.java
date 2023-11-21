package FinalProject;

import java.util.*;
import java.io.*;

class User {
    
    private final String username;
    private final String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String Username() {
        return username;
    }

    public boolean isValidCredentials(String enteredUsername, String enteredPassword) {
        return username.equals(enteredUsername) && password.equals(enteredPassword);
    }
}

class Admin extends User {
    public Admin(String username, String password) {
        super(username, password);
    }

    public void performAdminActions() {
        System.out.println("Admin actions performed.");
        // Add admin-specific actions here
    }
}

class Cashier extends User {
    public Cashier(String username, String password) {
        super(username, password);
    }

    public void performCashierActions() {
        System.out.println("Cashier actions performed.");
        // Add cashier-specific actions here
    }
}

public class FinalProjectOOP{
    public static void main(String[] args) throws IOException {
        try (Scanner scanner = new Scanner(System.in)) {
            // Create admin account
            System.out.println("Creating admin account ");
            System.out.println("-------------------------------------------");
            System.out.print("Enter admin username: ");
            String adminUsername = scanner.nextLine();
            System.out.print("Enter admin password: ");
            String adminPassword = scanner.nextLine();
            Admin admin = new Admin(adminUsername, adminPassword);
            
            // Create cashier account
            System.out.println("\nCreating cashier account ");
            System.out.println("-------------------------------------------");
            System.out.print("Enter cashier username: ");
            String cashierUsername = scanner.nextLine();
            System.out.print("Enter cashier password: ");
            String cashierPassword = scanner.nextLine();
            Cashier cashier = new Cashier(cashierUsername, cashierPassword);
            
            // Authentication
            System.out.println("\nAuthentication ");
            System.out.println("-------------------------------------------");
            System.out.print("Enter username: ");
            String enteredUsername = scanner.nextLine();
            System.out.print("Enter password: ");
            String enteredPassword = scanner.nextLine();
            System.out.println("");
            
            // Check admin credentials
            if (admin.isValidCredentials(enteredUsername, enteredPassword)) {
                System.out.println("Admin Login successfully.");
                admin.performAdminActions();
            } else if (cashier.isValidCredentials(enteredUsername, enteredPassword)) {
                System.out.println("Cashier Login successfully.");
                cashier.performCashierActions();
            } else {
                System.out.println("Login failed. Invalid username or password. Please try again!");
            }
        }
    }
}