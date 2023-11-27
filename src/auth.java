/*
    Auth Class
        The purpose of this class is to provide a secure way to log in to the system and
        allow the registration of new users. Keep in mind to set a particular method to PRIVATE
        if the class method shouldn't be accessed outside the class. Add FINAL on the variable
        if the particular variable shouldn't be accessed outside the class.

    Auth Guide
        To access certain class methods available outside the class, one must call the following
        auth.retrieve(String sid, String key) - This is the method used to retrieve a particular value from the account. This returns a String.
        Auth.signup(String sid, String un, String pass, String fn, String ln, String at) - This is the method used to sign up a new account. This returns a boolean. Must be logged in as an admin to use.
        Auth.login(String username, String password) - This is the method used to log in to the system. This returns a session ID.
        auth.loginAttemptsCheck() - This is the method used to check the number of login attempts. This returns an integer.

        To access certain class methods available only inside the class, one must call the following
        auth.checkCredentials(String username, String password) - This is the method used to check if the credentials are valid. This returns a boolean.
        auth.addNewAccount(String username, String password, String firstName, String lastName, String accountType) - This is the method used to add a new account. This returns a boolean.
        auth.deleteAccount(String username) - This is the method used to delete an account. This returns a boolean.
        auth.accountLookup(String username) - This is the method used to look up an account. This returns a Map<String, String> of the account.


*/

//package src;

import java.util.*;

public class auth {
    //Create a storage for each account credential
    static Map<String, String> account = new HashMap<>();

    //Create a list of accounts
    static List<Map<String, String>> listOfAccounts = new ArrayList<>();

    //Total number of accounts
    static int totalAccounts = 0;

    //Instantiate the class
    public auth() {
        //If the admin is not yet initialized, initialize it
        if (listOfAccounts.isEmpty()) {
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
    }

    //Auth Tests - Make sure that the data is correct
    public boolean testValues(String type, String value) {
        if (value == null) {
            System.out.println("Error: Null values are not allowed!");
            return true;
        }
        switch (type) {
            case "username":
                //Check if the username doesn't exist - if not return true
                if (accountLookup(value) == null) {
                    return false;
                } else {
                    System.out.println("Error: Username already exists!");
                }
                break;
            case "password":
                //Check if the password is longer than 8 characters
                if (value.length() >= 8) {
                    //Check if the password contains at least 1 number and 1 special character
                    if (value.matches(".*\\d.*") && value.matches(".*[!@#$%^&*].*")) {
                        return false;
                    } else {
                        System.out.println("Error: Password must contain at least 1 number and 1 special character!");
                    }
                } else {
                    System.out.println("Error: Password must be at least 8 characters long!");
                }
                break;
            case "firstName", "lastName":
                //Check if the first name is not empty
                if (!value.isEmpty()) {
                    return false;
                } else {
                    System.out.println("Error: Names cannot be empty!");
                }
                break;
            case "accountType":
                //Check if the account type is either A or C
                if (value.equalsIgnoreCase("A") || value.equalsIgnoreCase("C")) {
                    return false;
                } else {
                    System.out.println("Error: Invalid account type! Must be either A or C.");
                }
                break;
            default:
                throw new IllegalArgumentException("Error: Invalid argument for 'type' was passed - " + type);
        }
        return true;
    }

    public int addNewAccount(String username, String password, String firstName, String lastName, String accountType) {
        //Re-initialize the account storage
        account = new HashMap<>();

        //Add the new account to the list
        account.put("username", username);
        account.put("password", password);
        account.put("firstName", firstName);
        account.put("lastName", lastName);
        account.put("accountType", accountType);
        listOfAccounts.add(account);
        totalAccounts++;
        return 0;
    }

    public boolean deleteAccount(String username) {
        //Perform safety checks
        //Safety check 1: Null values are not allowed
        if (username == null) {
            System.out.println("Error: Null values are not allowed!");
            return false;
        }
        //Start lookup
        for (int i = 0; i < totalAccounts; i++) {
            //If found, remove the account
            if (listOfAccounts.get(i).get("username").equals(username)) {
                listOfAccounts.remove(i);
                System.out.println("Account deleted!");
                return true;
            }
        }
        //If not found, return an error
        System.out.println("Error: Username not found!");
        return false;
    }

    public Map<String, String> accountLookup(String username) {
        //Create a new array
        String[] tempAccount = new String[5];
        //Perform safety checks
        //Safety check 1: Null values are not allowed
        if (username == null) {
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
        return null;
    }
}

class authOperations extends auth {
    //Handlers
    private int loginAttempts = 0;
    private static String sessionID;
    //Store the logged-in user's account
    static Map<String, String> account = new HashMap<>();

    //Call current logged-in user
    String retrieve(String sid, String key) {
        if (sid.equals(sessionID)) {
            return account.get(key);
        }
        return null;
    }
    Map<String, String> retrieve(String sid) {
        if (sid.equals(sessionID)) {
            return account;
        }
        System.out.println("Error: Session ID doesn't match!");
        //TODO: Logout the user
        logout();
        return null;
    }

    //Auth retrieve all accounts for admin
    List<Map<String, String>> retrieveAllAccounts(String sid) {
        //Perform checks - if the user is logged in and is an admin
        if (retrieve(sid, "accountType").equals("A")) {
            return listOfAccounts;
        } else {
            System.out.println("Error: You are not authorized to perform this action!");
            return null;
        }
    }

    //Auth Login
    String login(String un, String pass) {
        //Perform checks
        if (loginAttempts < 3) {
            //Check if the credentials are correct
            if (checkCredentials(un, pass)) {
                //Generate a session ID
                sessionID = UUID.randomUUID().toString();
                //Return the session ID
                return sessionID;
            } else {
                //Increment login attempts
                loginAttempts++;
                //Return null
                return null;
            }
        } else {
            //Prohibit login attempts
            System.out.println("Error: Too many login attempts!");
            return null;
        }
    }

    //Auth Signup
    void signup(String sid, String un, String pass, String fn, String ln, String at) {
        //Perform checks - if the user is logged in and is an admin
        if (retrieve(sid, "accountType").equals("A")) {
            //Call addNewAccount
            int addNewAccount = addNewAccount(un, pass, fn, ln, at);
            if (addNewAccount == 0) {
                System.out.println("Total Registered Accounts: " + totalAccounts);
                System.out.println("Account created!");
                //callBackMethod.apply(addNewAccount);
            } else {
                System.out.println("Error: Account not created!");
                //callBackMethod.apply(addNewAccount);
            }
        } else {
            System.out.println("Error: You are not authorized to perform this action!");
            //callBackMethod.apply(6);
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

    //Session check
    boolean sessionCheck(String sid) {
        //Check if null
        if (sid == null) {
            return false;
        }
        return sid.equals(sessionID);
    }

    //Logout
    void logout() {
        sessionID = null;
    }


}
