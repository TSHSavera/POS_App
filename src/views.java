import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.Objects;

//package src;
public class views {

    private boolean status = true;

    private String sessionID;
    BufferedReader userInp = new BufferedReader(new InputStreamReader(System.in));
    authOperations authHandler = new authOperations();

    //Initialize new account array
    String[] newAccount = new String[5];

    views(String sessionID) throws IOException {
        //Retrieve account data
        Map<String, String> accountData = authOperations.retrieveCurrentUser(sessionID);
        //Check the account type
        if (Objects.requireNonNull(accountData).get("accountType").equals("A")) {
            System.out.println("Welcome Admin " + accountData.get("firstName"));
            System.out.println("Please select an option: ");
            System.out.println("1. Add new account");
            System.out.println("2. View accounts");
            System.out.println("3. Delete account");
            System.out.println("4. Change account details");
            System.out.println("5. Logout");
            System.out.print("Enter option: ");
            int option = Integer.parseInt(userInp.readLine());
            this.sessionID = sessionID;
            adminOps(option);
        }
        if (accountData.get("accountType").equals("C")) {
            System.out.println("Welcome Cashier " + accountData.get("username"));
            System.out.println("Please select an option: ");
            System.out.println("1. Create new transaction");
            System.out.println("2. View transactions");
            System.out.println("3. Logout");
            System.out.print("Enter option: ");
            int option = Integer.parseInt(userInp.readLine());
            this.sessionID = sessionID;
            cashierOps(option);
        }
    }

    public boolean getStatus() {
        return status;
    }

    public void adminOps(int option) throws IOException {
        switch (option) {
            case 1:
                viewAddNewAccount();
                break;
            case 2:
                viewViewAccounts();
                break;
            case 3:
                viewDeleteAccount();
                break;
            case 4:
                viewChangeAccountDetails();
                break;
            case 5:
                System.out.println("Logout");
                authOperations.logout();
                status = false;
                break;
            default:
                throw new IllegalArgumentException("Error: Invalid argument for 'option' was passed. - " + option);
        }
    }

    public void cashierOps(int option) {
        switch (option) {
            case 1:
                System.out.println("Create new transaction");
                break;
            case 2:
                System.out.println("View transactions");
                break;
            case 3:
                System.out.println("Logout");
                authOperations.logout();
                status = false;
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }


    //This function is called when the admin wants to add a new account
    void viewAddNewAccount() throws IOException{
        //Ask for values then test instantly
        //Ask for new account details
        //Loop until the signup is successful
        System.out.println("Enter new account details");
        //Ask for username
        do {
            System.out.print("Username: ");
            newAccount[0] = userInp.readLine();
        } while (authHandler.testValues("username", newAccount[0]));
        //Ask for password
        do {
            System.out.print("Password: ");
            newAccount[1] = userInp.readLine();
        } while (authHandler.testValues("password", newAccount[1]));
        //Ask for first name and last name
        do {
            System.out.print("First Name: ");
            newAccount[2] = userInp.readLine();
        } while (authHandler.testValues("firstName", newAccount[2]));
        do {
            System.out.print("Last Name: ");
            newAccount[3] = userInp.readLine();
        } while (authHandler.testValues("lastName", newAccount[3]));
        //Ask for the account type
        do {
            System.out.print("Account Type (A/C): ");
            newAccount[4] = userInp.readLine();
        } while (authHandler.testValues("accountType", newAccount[4]));

        //Once all loops broke, perform signup
        authHandler.signup(sessionID, newAccount[0], newAccount[1], newAccount[2], newAccount[3], newAccount[4]);
    }

    //This function is called when the admin wants to view all accounts
    void viewViewAccounts() {
        //Retrieve all accounts
        List<Map<String, String>> allAccounts = authHandler.retrieveAllAccounts(sessionID);

        //Print all accounts
        System.out.println("Total Registered Accounts: " + allAccounts.size());
        for (int i = 0; i < allAccounts.size(); i++) {
            System.out.println("Account " + (i + 1) + ":");
            System.out.println("Username: " + allAccounts.get(i).get("username"));
            System.out.println("Password: " + allAccounts.get(i).get("password"));
            System.out.println("First Name: " + allAccounts.get(i).get("firstName"));
            System.out.println("Last Name: " + allAccounts.get(i).get("lastName"));
            String accountType = allAccounts.get(i).get("accountType");
            if (accountType.equalsIgnoreCase("A")) {
                System.out.println("Account Type: Admin");
            } else if (accountType.equalsIgnoreCase("C")) {
                System.out.println("Account Type: Cashier");
            } else {
                System.out.println("Account Type: Unknown");
            }
            System.out.println();
        }

    }

    //This function is called when the admin wants to delete an account
    void viewDeleteAccount() throws IOException {
        //Retrieve all accounts
        viewViewAccounts();
        //Store all accounts
        List<Map<String, String>> allAccounts = authHandler.retrieveAllAccounts(sessionID);

        //Ask for the account to delete
        System.out.print("Enter the account number to delete: ");
        int accountNumber = Integer.parseInt(userInp.readLine());
        //Delete the account
        authHandler.deleteAccount(sessionID, allAccounts.get(accountNumber - 1).get("username"));
    }

    void viewChangeAccountDetails() throws IOException {
        //Retrieve all accounts
        viewViewAccounts();
        //Store all accounts
        List<Map<String, String>> allAccounts = authHandler.retrieveAllAccounts(sessionID);

        //Ask for the account to change
        System.out.print("Enter the account number to change: ");
        int accountNumber = Integer.parseInt(userInp.readLine());

        //Ask what to change
        System.out.println("What do you want to change?");
        System.out.println("1. Username");
        System.out.println("2. Password");
        System.out.println("3. First Name");
        System.out.println("4. Last Name");
        System.out.println("5. Account Type");
        System.out.print("Enter option: ");
        int option = Integer.parseInt(userInp.readLine());

        //Change the option to key
        String key;
        switch (option) {
            case 1:
                key = "username";
                break;
            case 2:
                key = "password";
                break;
            case 3:
                key = "firstName";
                break;
            case 4:
                key = "lastName";
                break;
            case 5:
                key = "accountType";
                break;
            default:
                throw new IllegalArgumentException("Error: Invalid argument for 'option' was passed. - " + option);
        }
        //Ask for the new value
        String newValue;
        do {
            System.out.print("Enter the new value: ");
            newValue = userInp.readLine();
        } while (authHandler.testValues(key, newValue));


        //Change the account credentials
        authHandler.changeCredentials(sessionID, allAccounts.get(accountNumber - 1).get("username"), key, newValue);
    }
}
