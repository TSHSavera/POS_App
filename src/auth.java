import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class auth {

    //Initialize
    String sessionID;
    String username;
    String password;
    String firstName;
    String lastName;
    String accountType;

    //Initialize the set of default credentials
    String[] usernames = {"pogi", "ganda"};
    String[] passwords = {"ako", "ikaw"};

    //Initialize array for additional credentials
    String[] newUsernames = new String[10];
    String[] newPasswords = new String[10];

    //Auth Login
    String login(String un, String pass) {
        //Perform checks
        if (checkCredentials(un, pass)) {
            //Set a random generated sid
            byte[] array = new byte[16]; // length is bounded by 7
            new Random().nextBytes(array);
            sessionID = new String(array, StandardCharsets.UTF_8);
            //Return
            return sessionID;
        }
        return null;
    }

    private boolean checkCredentials(String inputUsername, String inputPassword) {
        //First Loop: Perform Linear Search on default credentials
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i].equals(inputUsername) && passwords[i].equals(inputPassword)) {
                return true;
            }
        }

        //Second Loop: Perform Linear Search on newly added credentials
        for (int i = 0; i < newUsernames.length; i++) {
            if (newUsernames[i].equals(inputUsername) && passwords[i].equals(inputPassword)) {
                return true;
            }
        }

        //If no matches found, return false
        return false;
    }
}
