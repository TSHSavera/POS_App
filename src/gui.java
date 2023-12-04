import javax.swing.*;
public class gui {

    JFrame frame;
    JPanel panel;

    JTextField usernameField;
    JPasswordField passwordField;

    JButton loginButton;
    JButton clearButton;

    JTextArea textArea;

    public gui() {
        //Create frame
        frame = new JFrame("POS");
        //Create panel
        panel = new JPanel();

        //Create fields
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);

        //Create buttons
        JButton loginButton = new JButton("Login");
        JButton clearButton = new JButton("Clear");

        //Add action listeners
        loginButton.addActionListener(e -> {
            //Get username and password
            String username = usernameField.getText();
            String password = String.valueOf(passwordField.getPassword());

            //Perform login
            authOperations authHandler = new authOperations();
            guiOperations guiHandler = new guiOperations();
            String sessionID = null;

            do {
                sessionID = authHandler.login(username, password);
                //Call views
                if (sessionID != null) {
                    do {
                        //Do gui changes
                        if (!guiHandler.getStatus()) {
                            break;
                        }
                    } while (true);
                } else {
                    if (authHandler.loginAttemptsCheck() < 3) {
                        //Create a error dialog
                        JOptionPane.showMessageDialog(frame, "Invalid username or password. Please try again.");
                    } else {
                        //Create a error dialog
                        JOptionPane.showMessageDialog(frame, "You have exceeded the maximum number of login attempts. Please try again later.");
                        System.exit(0);
                    }
                }
            } while (!authHandler.sessionCheck(sessionID));
        });

        //Add components to panel
        panel.add(usernameField);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(clearButton);

        //Add panel to frame
        frame.add(panel);


        //Frame Properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setVisible(true);
        frame.pack();
    }
}

class guiOperations {
    private boolean status = true;
    public void changeView() {
        //Change vieww
    }
    public boolean getStatus() {
        return status;
    }
}
