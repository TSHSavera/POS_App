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
import java.util.Random;

public class auth {

    //Initialize
    String sessionID;
    static String username;
    private String password;
    String firstName;
    String lastName;
    static String accountType;
    private int loginAttempts = 0;

    //Initialize the set of default credentials
    private final String[] usernames = {"pogi", "ganda"};
    private final String[] passwords = {"ako", "ikaw"};
    private final String[] firstNames = {"Sandren Troy", "Juan"};
    private final String[] lastNames = {"Milante", "Dela Cruz"};
    //Account Types:
    // A = Admin
    // C = Cashier
    private final String[] accountTypes = {"A", "C"};

    //Initialize array for additional credentials
    private String[] newUsernames = new String[10];
    private String[] newPasswords = new String[10];
    private String[] newFirstName = new String[10];
    private String[] newLastName = new String[10];
    private String[] newAccountTypes = new String[10];

    //Auth Login
    String login(String un, String pass) {
        //Perform checks
        if (checkCredentials(un, pass)) {
            //Generate session ID
            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
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
        //First Loop: Perform Linear Search on default credentials
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i].equals(inputUsername) && passwords[i].equals(inputPassword)) {
                //Set user credentials to the main variables
                username = usernames[i];
                password= passwords[i];
                firstName = firstNames[i];
                lastName = lastNames[i];
                accountType = accountTypes[i];
                //Callback return
                return true;
            }
        }

        //Second Loop: Perform Linear Search on newly added credentials
        //Check if the first element is null for safety
        if (newUsernames[0] != null) {
            for (int i = 0; i < newUsernames.length; i++) {
                if (newUsernames[i].equals(inputUsername) && passwords[i].equals(inputPassword)) {
                    //Set user credentials to the main variables
                    username = newUsernames[i];
                    password= newPasswords[i];
                    firstName = newFirstName[i];
                    lastName = newLastName[i];
                    accountType = newAccountTypes[i];
                    //Callback return
                    return true;
                }
            }
        } else {
            return false;
        }

        //If no matches found, return false
        return false;
    }

}
