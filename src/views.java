/*
    Views Class

 */

public class views {

    private String window;
    views() {
        window = null;
    }
    views(String type) {
        if (type.equals("admin-home")) {
            window = "admin-home";
            System.out.println("Welcome Admin");
        }
        if (type.equals("cashier-home")) {
            window = "cashier-home";
            System.out.println("Welcome Cashier");
        }
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


}
