/*
    Auth Class
        The purpose of this class is to provide a secure way to log in to the system and
        allow the registration of new users. Keep in mind to set a particular method to PRIVATE
        if the class method shouldn't be accessed outside the class. Add FINAL on the variable
        if the particular variable shouldn't be accessed outside the class.

    Auth Guide
        To access certain class methods available outside the class, one must call the following
        auth.sessionID - This is the session ID of the user. This is used to check if the user is logged in.
        auth.username - This is the username of the user. This is used to get the username of the logged-in user.
        auth.accountType - This is the account type of the user. This is used to get the account type of the logged-in user.
        auth.firstName - This is the first name of the user. This is used to get the first name of the logged-in user.
        auth.lastName - This is the last name of the user. This is used to get the last name of the logged-in user.
        auth.login(String username, String password) - This is the method used to log in to the system. This returns a session ID.

        To access certain class methods available only inside the class, one must call the following
        auth.checkCredentials(String username, String password) - This is the method used to check if the credentials are valid. This returns a boolean.

*/

import java.nio.charset.StandardCharsets;
import java.util.*;

public class auth {
    //Create a storage for each account credential
    Map<String, String> account = new HashMap<>();

    //Create a list of accounts
    List<Map<String, String>> listOfAccounts = new ArrayList<>();

    //Total number of accounts
    int totalAccounts = 0;

    //Instantiate the class
    public auth() {
        //Initialize the admin accounts
        account.put("username", "admin");
        account.put("password", "admin");
        account.put("firstName", "Sandren Troy");
        account.put("lastName", "Milante");
        account.put("accountType", "A");
        listOfAccounts.add(account);
        totalAccounts++;

        account = new HashMap<>();
        account.put("username", "cashier");
        account.put("password", "cashier");
        account.put("firstName", "Juan");
        account.put("lastName", "Dela Cruz");
        account.put("accountType", "C");
        listOfAccounts.add(account);
        totalAccounts++;
    }

    public void addNewAccount(String username, String password, String firstName, String lastName, String accountType) {
        //Re-initialize the account storage
        account = new HashMap<>();

        //Perform safety checks
        //Safety check 1: Null values are not allowed
        if (username == null || password == null || firstName == null || lastName == null || accountType == null) {
            System.out.println("Error: Null values are not allowed!");
            return;
        }
        //Safety check 2: Username must be unique
        for (int i = 0; i < totalAccounts; i++) {
            if (listOfAccounts.get(i).get("username").equals(username)) {
                System.out.println("Error: Username already exists!");
                return;
            }
        }
        //Safety check 3: Password must be at least 8 characters long
        if (password.length() < 8) {
            System.out.println("Error: Password must be at least 8 characters long!");
            return;
        } else {
            //must contain at least 1 number and 1 special character
            if (!password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*].*")) {
                System.out.println("Error: Password must contain at least 1 number and 1 special character!");
                return;
            }
        }
        //Safety check 4: An account type must be either A or C
        if (!(accountType.equals("A") || accountType.equals("C"))) {
            System.out.println("Error: Account type must be either A or C!");
            return;
        }
        //Safety check 5: First name and last name must not be empty
        if (firstName.isEmpty() || lastName.isEmpty()) {
            System.out.println("Error: First name and last name must not be empty!");
            return;
        }

        //Add the new account to the list
        account.put("username", username);
        account.put("password", password);
        account.put("firstName", firstName);
        account.put("lastName", lastName);
        account.put("accountType", accountType);
        listOfAccounts.add(account);
        totalAccounts++;
        System.out.println("Account added!");
    }

    public void deleteAccount(String username) {
        //Perform safety checks
        //Safety check 1: Null values are not allowed
        if (username == null) {
            System.out.println("Error: Null values are not allowed!");
            return;
        }
        //Start lookup
        for (int i = 0; i < totalAccounts; i++) {
            //If found, remove the account
            if (listOfAccounts.get(i).get("username").equals(username)) {
                listOfAccounts.remove(i);
                System.out.println("Account deleted!");
                return;
            }
        }
        //If not found, return an error
        System.out.println("Error: Username not found!");
    }

    public Map<String, String> accountLookup(String username) {
        //Create a new array
        String[] tempAccount = new String[5];
        //Perform safety checks
        //Safety check 1: Null values are not allowed
        if (username == null) {
            System.out.println("Error: Null values are not allowed!");
            return null;
        }
        //Start lookup
        for (int i = 0; i < totalAccounts; i++) {
            //If found, save the account
            if (listOfAccounts.get(i).get("username").equals(username)) {
                return listOfAccounts.get(i);
            }
        }
        //If not found, return an error
        System.out.println("Error: Username not found!");
        return null;
    }
}

class authOperations extends auth {
    //Handlers
    private int loginAttempts = 0;
    String sessionID = null;
    Map<String, String> account = new HashMap<>();

    //Call current logged-in user
    String retrieve(String key) {
        return account.get(key);
    }
    //Auth Login
    String login(String un, String pass) {
        //Perform checks
        if (checkCredentials(un, pass)) {
            //Generate session ID
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            //Set SessionID
            sessionID = new String(array, StandardCharsets.UTF_8);
            //Return session ID
            return sessionID;
        } else {
            //Increment login attempts
            loginAttempts++;
            //Return null
            return null;
        }
    }



    //Auth Check Credentials
    private boolean checkCredentials(String inputUsername, String inputPassword) {
        //Call lookup then store
        account = accountLookup(inputUsername);
        //Check if the account is null
        if (account == null) return false;
        //Check if the password is correct
        return account.get("password").equals(inputPassword);
    }

    //Login Attempts
    //Goal - To prevent accessing the login attempts directly. Set to read-only.
    int loginAttemptsCheck() {
        return loginAttempts;
    }


}
