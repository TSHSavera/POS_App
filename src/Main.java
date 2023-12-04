//package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException{
        //Initialize Data Storage
        dataStorage dataHandler = new dataStorage();

        //Class views declaration

        //Attempt to load saved data'
        System.out.println("Attempting to load saved data (1/4)");
        //Load the transactions from the file
        dataStorage.overrideTransactionData(dataHandler.readSaveFile("Transaction"));
        System.out.println("Attempting to load saved data (2/4)");
        //Load the products from the file
        dataStorage.overrideProductData(dataHandler.readSaveFile("Product"));
        System.out.println("Attempting to load saved data (3/4)");
        //Load the users from the file
        dataStorage.overrideAuthData(dataHandler.readSaveFile("Account"));
        System.out.println("Attempting to load saved data (4/4)");
        //Load the TransactionItems from the file
        dataStorage.overrideItemsData(dataHandler.readItemsFile("TransactionItems"));


        //Initialize Variable Handlers
        String inputUsername, inputPassword, option;
        String sessionID = null;

        //Create Inputs, Initialize Classes
        BufferedReader userInp = new BufferedReader(new InputStreamReader(System.in));
        authOperations authHandler = new authOperations();

        //Perform Login
        do {
            System.out.println("\nWelcome to POS! What would you like to do next? ");
            do {
                //Ask if the user wants to log in or exit the program
                System.out.println("1. Login on Console");
                System.out.println("2. Exit");
                System.out.print("Enter option: ");
                option = userInp.readLine();
                if (option.equals("2")) {
                    System.out.println("Program exits.");
                    System.exit(0);
                }
                if (!option.equals("1") && !option.equals("2")) {
                    System.out.println("\nInvalid input! Please choose between 1 or 2 only!\n");
                }
            } while (!option.equals("1") && !option.equals("2"));


            //Ask for username and password
            System.out.println("\nWelcome to POS! Please enter your account credentials.");
            System.out.print("Username: ");
            inputUsername = userInp.readLine();
            System.out.print("Password: ");
            inputPassword = userInp.readLine();
            System.out.println();


            //Perform login and save sessionID
            sessionID = authHandler.login(inputUsername, inputPassword);
            //Call auth
            if (sessionID != null) {
                //Call views
                do {
                    views viewHandler = new views(sessionID);
                    if (!viewHandler.getStatus()) {
                        break;
                    }
                } while (true);
            } else {
                if (authHandler.loginAttemptsCheck() < 3) {
                    System.out.println("Invalid Credentials!");
                } else {
                    System.out.println("Too many login attempts! Program exits.");
                    System.exit(0);
                }

            }
        } while (authHandler.sessionCheck(sessionID));
        System.out.println("Program exits.");
    }
}
