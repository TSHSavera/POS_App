import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        //Initialize Variable Handlers
        String inputUsername, inputPassword;

        //Create Inputs, Initialize Classes
        BufferedReader userInp = new BufferedReader(new InputStreamReader(System.in));
        auth authHandler = new auth();


        //Perform Login
        do {
            System.out.println("Welcome to POS! Please enter your account credentials.");
            System.out.print("Username: ");
            inputUsername = userInp.readLine();
            System.out.print("Password: ");
            inputPassword = userInp.readLine();

            //Call auth
            if (authHandler.login(inputUsername, inputPassword) != null) {
                System.out.println("Login successful!");
                //Initialize views
                if (auth.accountType.equals("A")) {
                    new views("admin-home", auth.username);
                } else if (auth.accountType.equals("C")) {
                    new views("cashier-home", auth.username);
                }

            } else {
                System.out.println("Invalid Credentials!");

            }
        } while (authHandler.loginAttemptsCheck() > 3);



    }
}