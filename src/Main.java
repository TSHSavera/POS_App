import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        //Initialize Variable Handlers
        String inputUsername, inputPassword;

        //Create Inputs, Initialize Classes
        BufferedReader userInp = new BufferedReader(new InputStreamReader(System.in));
        authOperations authHandler = new authOperations();


        //Perform Login
        do {
            System.out.println("Welcome to POS! Please enter your account credentials.");
            System.out.print("Username: ");
            inputUsername = userInp.readLine();
            System.out.print("Password: ");
            inputPassword = userInp.readLine();

            //Call auth
            if (authHandler.login(inputUsername, inputPassword) != null) {
                break;
            } else {
                if (authHandler.loginAttemptsCheck() < 3) {
                    System.out.println("Invalid Credentials!");
                } else {
                    System.out.println("Too many login attempts! Program exits.");
                    System.exit(0);
                }

            }
        } while (authHandler.loginAttemptsCheck() < 3);

        //Perform Ops
        System.out.println("Login successful!");
        //Initialize views
        if (authHandler.retrieve("accountType").equals("A")) {
            new views("admin-home", authHandler.retrieve("username"));
        } else if (authHandler.retrieve("accountType").equals("C")) {
            new views("cashier-home", authHandler.retrieve("username"));
        }
    }
}