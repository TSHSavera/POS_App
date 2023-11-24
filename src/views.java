import java.util.Map;
import java.io.*;

//package src;
public class views {

    private String window;
    private boolean status = true;

    private String sessionID;
    BufferedReader userInp = new BufferedReader(new InputStreamReader(System.in));
    views() {
        window = null;
    }

    views(String type, String username) {
        if (type.equals("admin-home")) {
            window = "admin-home";
            System.out.println("Welcome Admin " + username);
        }
        if (type.equals("cashier-home")) {
            window = "cashier-home";
            System.out.println("Welcome Cashier " + username);
        }
    }

    views(String sessionID) throws IOException {
        //Retrieve account data
        authOperations authHandler = new authOperations();
        Map<String, String> accountData = authHandler.retrieve(sessionID);
        //Check the account type
        if (accountData.get("accountType").equals("A")) {
            window = "admin-home";
            System.out.println("Welcome Admin " + accountData.get("firstName"));
            System.out.println("Please select an option: ");
            System.out.println("1. Add new account");
            System.out.println("2. View accounts");
            System.out.println("3. Logout");
            System.out.print("Enter option: ");
            int option = Integer.parseInt(userInp.readLine());
            this.sessionID = sessionID;
            adminOps(option);
        }
        if (accountData.get("accountType").equals("C")) {
            window = "cashier-home";
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
        authOperations authHandler = new authOperations();
        switch (option) {
            case 1:
                String[] newAccount = new String[5];
                System.out.println("Enter new account details");
                System.out.print("Username: ");
                newAccount[0] = userInp.readLine();
                System.out.print("Password: ");
                newAccount[1] = userInp.readLine();
                System.out.print("First Name: ");
                newAccount[2] = userInp.readLine();
                System.out.print("Last Name: ");
                newAccount[3] = userInp.readLine();
                do {
                    System.out.print("Account Type (A/C): ");
                    newAccount[4] = userInp.readLine();
                } while(!newAccount[4].equalsIgnoreCase("a") && !newAccount[4].equalsIgnoreCase("c"));
                authHandler.signup(sessionID, newAccount[0], newAccount[1], newAccount[2], newAccount[3], newAccount[4]);

                break;
            case 2:
                System.out.println("View accounts");
                //Retrieve all accounts
                System.out.println("Total Registered Accounts: " + authHandler.totalAccounts);
                //Loop through all accounts
                for (int i = 0; i < authHandler.totalAccounts; i++) {
                    String accountType;
                    if (authHandler.listOfAccounts.get(i).get("accountType").equalsIgnoreCase("A")) {
                        accountType = "Admin";
                    } else {
                        accountType = "Cashier";
                    }
                    System.out.println("Account " + (i + 1) + ":");
                    System.out.println("Username: " + authHandler.listOfAccounts.get(i).get("username"));
                    System.out.println("Password: " + authHandler.listOfAccounts.get(i).get("password"));
                    System.out.println("First Name: " + authHandler.listOfAccounts.get(i).get("firstName"));
                    System.out.println("Last Name: " + authHandler.listOfAccounts.get(i).get("lastName"));
                    System.out.println("Account Type: " + accountType);
                    System.out.println();
                }

                break;
            case 3:
                System.out.println("Logout");
                authHandler.logout();
                status = false;
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

    public void cashierOps(int option) {
        authOperations authHandler = new authOperations();
        switch (option) {
            case 1:
                System.out.println("Create new transaction");
                break;
            case 2:
                System.out.println("View transactions");
                break;
            case 3:
                System.out.println("Logout");
                authHandler.logout();
                status = false;
                break;
            default:
                System.out.println("Invalid option");
                break;
        }
    }

}
