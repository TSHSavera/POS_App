import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

//package src;
public class views {

    private boolean status = true;

    private String sessionID;
    BufferedReader userInp = new BufferedReader(new InputStreamReader(System.in));
    authOperations authHandler = new authOperations();
    productStorage productHandler = new productStorage();
    //Create a new transaction
    transactionStorage transactionHandler = new transactionStorage();
    //File Saving
    dataStorage dataHandler = new dataStorage();

    //Initialize a new account array
    String[] newAccount = new String[5];

    int option, option1;

    views(String sessionID) throws IOException {
        //Retrieve account data
        Map<String, String> accountData = authOperations.retrieveCurrentUser(sessionID);

        //Check the account type
        if (Objects.requireNonNull(accountData).get("accountType").equalsIgnoreCase("A")) {
            System.out.println("Welcome Admin " + accountData.get("firstName"));
            do {
                System.out.println("Please select an option: ");
                System.out.println("1. Add new account");
                System.out.println("2. View accounts");
                System.out.println("3. Delete account");
                System.out.println("4. Change account details");
                System.out.println("5. Add new products");
                System.out.println("6. Delete product");
                System.out.println("7. View product list");
                System.out.println("8. Change product details");
                System.out.println("9. Search product");
                System.out.println("10. Get the product price");
                System.out.println("11. Create new transaction");
                System.out.println("12. View transactions");
                System.out.println("13. Remove transaction");
                System.out.println("14. Save Product File");
                System.out.println("15. Save Account File");
                System.out.println("16. Save Transaction File");
                System.out.println("17. Show the quantity of all products");
                System.out.println("18. Logout");

                try {
                    System.out.print("Enter option: ");
                    option = Integer.parseInt(userInp.readLine());

                    if (option < 1 || option > 18) {
                        System.out.println("\nInvalid input range\n");
                    }
                } catch (NumberFormatException e){
                    System.out.println("\nPlease enter a valid option!\n");
                }
            } while(option < 1 || option > 18);
            
            this.sessionID = sessionID;
            adminOps(option);
        }


        if (accountData.get("accountType").equalsIgnoreCase("C")) {
            System.out.println("Welcome Cashier " + accountData.get("username"));
            do {
                System.out.println("Please select an option: ");
                System.out.println("1. Create new transaction");
                System.out.println("2. View transactions");
                System.out.println("3. Search product");
                System.out.println("4. Get the product price");
                System.out.println("5. Get product quantity");
                System.out.println("6. Show the quantity of all products");
                System.out.println("7. Logout");

                try {
                    System.out.print("Enter option: ");
                    option1 = Integer.parseInt(userInp.readLine());

                    if (option < 1 || option > 18) {
                        System.out.println("\nInvalid input range. Please enter a valid option!\n");
                    }
                } catch(NumberFormatException e){
                    System.out.println("\nPlease enter a valid option!\n");
                }
            } while (option < 1 || option > 18);

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
                viewAddNewProduct();
                break;
            case 6:
                viewDeleteProduct();
                break;
            case 7:
                viewProductList();
                break;
            case 8:
                viewChangeProductDetails();
                break;
            case 9:
                viewSearchProduct();
                break;
            case 10:
                viewGetPrice();
                break;
            case 11:
                viewCreateNewTransaction();
                break;
            case 12:
                viewViewTransaction();
                break;
            case 13:
                viewRemoveTransaction();
                break;
            case 14:
                viewSaveProduct();
                break;
            case 15:
                viewSaveAccount();
                break;
            case 16:
                viewSaveTransaction();
                break;
            case 17:
                productHandler.showProductQuantity();
                break;
            case 18:
                System.out.println("Logout");
                authOperations.logout();
                status = false;
                break;
            default:
                System.out.println("Error: Invalid argument for 'option' was passed. - " + option);
                break;
        }
    }


    public void cashierOps(int option) throws IOException {
        switch (option) {
            case 1:
                viewCreateNewTransaction();
                break;
            case 2:
                viewViewTransaction();
                break;
            case 3:
                viewSearchProduct();
                break;
            case 4:
                viewGetPrice();
                break;
            case 5:
                productHandler.getProductQuantity();
                break;
            case 6:
                productHandler.showProductQuantity();
                break;
            case 7:
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
    void viewAddNewAccount() throws IOException {
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
        ArrayList<HashMap<String, String>> allAccounts = authHandler.retrieveAllAccounts(sessionID);

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
        ArrayList<HashMap<String, String>> allAccounts = authHandler.retrieveAllAccounts(sessionID);

        //Ask for the account to delete
        System.out.print("Enter the account number to delete: ");
        int accountNumber = Integer.parseInt(userInp.readLine());
        //Delete the account
        String deleteAccount = authHandler.deleteAccount(sessionID, allAccounts.get(accountNumber - 1).get("username"));
        if (deleteAccount != null) {
            System.out.println("Account with username of '" + deleteAccount + "' was deleted successfully!");
        } else {
            System.out.println("Account deletion failed!");
        }
    }

    void viewChangeAccountDetails() throws IOException {
        //Retrieve all accounts
        viewViewAccounts();
        //Store all accounts
        ArrayList<HashMap<String, String>> allAccounts = authHandler.retrieveAllAccounts(sessionID);

        //Ask for the account to change
        int accountNumber;
        do {
            System.out.print("Enter the account number to change: ");
            accountNumber = Integer.parseInt(userInp.readLine());
            if (accountNumber > allAccounts.size()) {
                System.out.println("Out of bounds! Please enter a valid account number.");
            }
        } while (accountNumber > allAccounts.size());


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
        String key = switch (option) {
            case 1 -> "username";
            case 2 -> "password";
            case 3 -> "firstName";
            case 4 -> "lastName";
            case 5 -> "accountType";
            default -> throw new IllegalArgumentException("Error: Invalid argument for 'option' was passed. - " + option);
        };
        //Ask for the new value
        String newValue;
        do {
            System.out.print("Enter the new value: ");
            newValue = userInp.readLine();
        } while (authHandler.testValues(key, newValue));

        //Change the account credentials
        authHandler.changeCredentials(sessionID, allAccounts.get(accountNumber - 1).get("username"), key, newValue);
    }

    //Add new products
    void viewAddNewProduct() throws IOException {
        String choice, productPrice, productQuantity, productName;
        //Ask for Products
        do {
            do {
                System.out.print("Enter product name: ");
                productName = userInp.readLine();
                //Check if the input is alphanumeric
                if (productHandler.testProductValues("productName", productName))
                    System.out.println("Product name only accepts alphanumeric characters!");
            } while (productHandler.testProductValues("productName", productName));

            do {
                System.out.print("Enter product price: ");
                productPrice = userInp.readLine();
                //Check if the input is numeric
                if (productHandler.testProductValues("productPrice", productPrice))
                    System.out.println("Product price only accepts numeric characters!");
            } while (productHandler.testProductValues("productPrice", productPrice));

            do {
                System.out.print("Current Stock: ");
                productQuantity = userInp.readLine();
                //Check if the input is numeric
                if (productHandler.testProductValues("productQuantity", productQuantity))
                    System.out.println("Current Stock only accepts numeric characters!");

            } while (productHandler.testProductValues("productQuantity", productQuantity));

            productHandler.addProduct(productName, productPrice, productQuantity);
            System.out.println("Product added successfully!");

            System.out.print("Do you want to add another product? y/n: ");
            choice = userInp.readLine();

        } while (choice.equalsIgnoreCase("y"));
    }

    //Delete product
    void viewDeleteProduct() throws IOException {
        //Ask for the product ID
        System.out.print("Enter the product ID to delete: ");
        String productID = userInp.readLine();
        //Delete the product
        String deleteProduct;
        try {
            deleteProduct = productHandler.searchProduct(productID).deleteProduct();
        } catch (NullPointerException e) {
            System.out.println("Product not found!");
            return;
        }
        if (deleteProduct != null) {
            System.out.println("Product with ID of '" + deleteProduct + "' was deleted successfully!");
        }
    }

    //View the product list
    void viewProductList() {
        System.out.println(productStorage.productList);
        productHandler.printAllProducts();
    }

    //Change product details
    void viewChangeProductDetails() throws IOException {
        //Ask for the product ID
        System.out.print("Enter the product ID to change: ");
        String productID = userInp.readLine();
        try {
            productHandler.searchProduct(productID).getProductName();
        } catch (NullPointerException e) {
            System.out.println("Product not found!");
            return;
        }
        //Ask what to change
        System.out.println("What do you want to change?");
        System.out.println("1. Product Name");
        System.out.println("2. Product Price");
        System.out.println("3. Product Quantity");
        System.out.print("Enter option: ");
        int option = Integer.parseInt(userInp.readLine());
        //Change the option to key
        String key = switch (option) {
            case 1 -> "productName";
            case 2 -> "productPrice";
            case 3 -> "productQuantity";
            default -> throw new IllegalArgumentException("Error: Invalid argument for 'option' was passed. - " + option);
        };
        //Ask for the new value
        String newValue;
        do {
            System.out.print("Enter the new value: ");
            newValue = userInp.readLine();
        } while (productHandler.testProductValues(key, newValue));
        //Change the product details
        productHandler.searchProduct(productID).changeProductDetails(key, newValue);
    }

    //Search product
    void viewSearchProduct() throws IOException {
        //Ask for the product ID
        System.out.print("Enter the product ID to search: ");
        String productID = userInp.readLine();
        String searchProduct;
        //Search the product
        try {
            searchProduct = productHandler.searchProduct(productID).getProductName();
        } catch (NullPointerException e) {
            System.out.println("Product not found!");
            return;
        }
        if (searchProduct != null) {
            System.out.println("Product found!");
            productHandler.searchProduct(productID).printProductDetails();
        }
    }

    //Create new transaction
    void viewCreateNewTransaction() throws IOException {
        //Initialize variables
        String productID, productQuantity;
        String choice;
        //Create ArrayList of product IDs and quantities
        ArrayList<String> productIDs = new ArrayList<>();
        ArrayList<String> productQuantities = new ArrayList<>();

        //Ask for Products
        do {
            do {
                System.out.print("Enter product ID: ");
                productID = userInp.readLine();
                //Check if the input is numeric
                if (productHandler.testProductValues("productID", productID)) {
                    System.out.println("Product ID only accepts numeric characters!");
                } else {
                    //Check if the product exists
                    if (productHandler.searchProduct(productID) == null) {
                        System.out.println("Product not found!");
                    }
                }
            } while (productHandler.testProductValues("productID", productID) && productHandler.searchProduct(productID) != null);

            do {
                System.out.print("Enter product quantity: ");
                productQuantity = userInp.readLine();
                //Check if the input is numeric
                if (productHandler.testProductValues("productQuantity", productQuantity))
                    System.out.println("Product quantity only accepts numeric characters!");
            } while (productHandler.testProductValues("productQuantity", productQuantity));


            productIDs.add(productID);
            productQuantities.add(productQuantity);

            System.out.print("Do you want to add another product? y/n: ");
            choice = userInp.readLine();

        } while (choice.equalsIgnoreCase("y"));
        //Add the products to the transaction
        transactionHandler.addNewItems(productIDs, productQuantities);
        //Create a new transaction
        transactionHandler.addNewTransaction();
        //Print the transaction
        transactionHandler.printTransactionDetails();
        //Ask if the transaction is final
        System.out.print("Would you like to save this transaction? y/n: ");
        choice = userInp.readLine();
        if (choice.equalsIgnoreCase("y")) {
            System.out.println("Transaction created successfully!");
        } else {
            System.out.println("Transaction cancelled!");
            transactionHandler.removeTransaction(String.valueOf(transactionStorage.transactionStorage.size()));
        }
    }

    //View transactions
    void viewViewTransaction() throws IOException {
        transactionHandler.printALlTransactions();
    }

    //Remove transaction
    void viewRemoveTransaction() throws IOException {
        //Ask for the transaction ID - loop until the transaction is found
        String transactionID;
        do {
            System.out.print("Enter the transaction ID to remove: ");
            transactionID = userInp.readLine();
            if (transactionHandler.searchTransaction(transactionID) == null) {
                System.out.println("Transaction not found!");
            }
        } while (transactionHandler.searchTransaction(transactionID) == null);
        //Remove the transaction
        try {
            transactionHandler.removeTransaction(transactionID);
            System.out.println("Transaction removed successfully!");
        } catch (NullPointerException e) {
            System.out.println("Transaction not found!");
        }
    }

    void viewSaveProduct() throws IOException {
        String fileName = "Product";
        System.out.println();

        //Converts Product list data to some format - then save
        dataHandler.storeToFile(fileName, productStorage.productList);

        System.out.println();
    }

    void viewSaveAccount() throws IOException {
        String fileName = "Account";
        System.out.println();

        //Converts Account list data to json
        dataHandler.storeToFile(fileName, auth.listOfAccounts);

        System.out.println();

    }

    void viewSaveTransaction() throws IOException {
        String fileName = "Transaction";
        System.out.println();

        //Converts Transaction list data to json
        dataHandler.storeToFile(fileName, transactionStorage.transactionStorage);
        //Save Items too
        dataHandler.storeToItemsFile("TransactionItems", transactionStorage.itemsList);

        System.out.println();
    }

    void viewGetPrice()throws IOException{
        String productID;

        do {
            System.out.print("Enter product ID: ");
            productID = userInp.readLine();
            //Check if the input is numeric
            if (productHandler.testProductValues("productID", productID))
                System.out.println("Product ID only accepts numeric characters!");
        } while (productHandler.testProductValues("productID", productID));

        double pPrice;
        try {
            pPrice = (productHandler.searchProduct(productID).getProductPrice());
            System.out.println("\nPrice of an item: "+new DecimalFormat("#.##").format(pPrice)+"\n");
        } catch (NullPointerException e) {
            System.out.println("Product not found!");
        }
    }

    void clearScreen() throws IOException, InterruptedException {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    }
}
