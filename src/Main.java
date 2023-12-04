//package src;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {


        //Initialize Variable Handlers
        String inputUsername, inputPassword;
        String sessionID = null;

        //Create Inputs, Initialize Classes
        BufferedReader userInp = new BufferedReader(new InputStreamReader(System.in));
        authOperations authHandler = new authOperations();

        //Perform Login
        do {
            //Ask if the user wants to log in or exit the program
            System.out.println("Welcome to POS! What would you like to do next?: ");
            System.out.println("1. Login on Console");
            System.out.println("2. Login on GUI");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            int option = Integer.parseInt(userInp.readLine());
            if (option == 2) {
                gui guiHandler = new gui();
                System.out.println("Press enter to terminate the application.");
                if (System.in.read() != 0) {
                    System.exit(0);
                }
            } else if (option == 3) {
                System.out.println("Program exits.");
                System.exit(0);
            } else if (option != 1) {
                System.out.println("Invalid option!");
                continue;
            }

            //Ask for username and password
            System.out.println("Welcome to POS! Please enter your account credentials.");
            System.out.print("Username: ");
            inputUsername = userInp.readLine();
            System.out.print("Password: ");
            inputPassword = userInp.readLine();

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
        } while (!authHandler.sessionCheck(sessionID));
        System.out.println("Program exits.");
    }
}
