import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
public class transactionStorage {
    //Create a storage for each transaction
    dataStorage dataHandler = new dataStorage();
    static ArrayList<HashMap<String, String>> transactionStorage = new ArrayList<>();
    //Create a list of items in transaction
    static ArrayList<HashMap<String, ArrayList<String>>> itemsList = new ArrayList<>();
    //Total number of transactions
    static int transactionCount = 0;
    //Total price temp handler
    double totalPrice = 0;
    //Storage for search results
    static HashMap<String, String> searchResults = new HashMap<>();

    //Instantiate the class
    public transactionStorage() {
        if (transactionStorage == null || transactionStorage.isEmpty()) {
            //Re-instantiate the class
            transactionStorage = new ArrayList<>();
            itemsList = new ArrayList<>();
            double totalPrice = 0;
            //Add items
            HashMap<String, ArrayList<String>> itemsInstance = new HashMap<>();
            ArrayList<String> itemsIDs = new ArrayList<>();
            itemsIDs.add("1");
            ArrayList<String> itemQuantity = new ArrayList<>();
            itemQuantity.add("1");
            ArrayList<String> itemsTotal = new ArrayList<>();
            //Get the product price from productStorage and multiply it by the quantity
            for (int i = 0; i < productStorage.productList.size(); i++) {
                if (productStorage.productList.get(i).get("productID").equals("1")) {
                    double x = Double.parseDouble(productStorage.productList.get(i).get("productPrice")) * Double.parseDouble(itemQuantity.get(i));
                    totalPrice += x;
                    itemsTotal.add(String.valueOf(x));
                }
            }
            //Create sample transactions
            HashMap<String, String> transactionInstance = new HashMap<>();
            transactionInstance.put("ID", "1");
            transactionInstance.put("Timestamp", String.valueOf(new Timestamp(System.currentTimeMillis())));
            transactionInstance.put("Total", String.valueOf(totalPrice));
            transactionInstance.put("itemsIndex", String.valueOf(transactionCount));
            transactionStorage.add(transactionInstance);


            itemsInstance.put("itemID", itemsIDs);
            itemsInstance.put("itemQuantity", itemQuantity);
            itemsInstance.put("itemTotal", itemsTotal);
            itemsList.add(itemsInstance);
            transactionCount++;
        }
    }

    //Create a new items instance
    public void addNewItems(ArrayList<String> productIDs, ArrayList<String> productQuantities) {
        //Add items
        HashMap<String, ArrayList<String>> itemsInstance = new HashMap<>();
        ArrayList<String> itemsTotal = new ArrayList<>();
        //Get the product price from productStorage and multiply it by the quantity
        for (int j = 0; j < productIDs.size(); j++) {
            for (int i = 0; i < productStorage.productList.size(); i++) {
                if (productStorage.productList.get(i).get("productID").equals(productIDs.get(j))) {
                    double x = Double.parseDouble(productStorage.productList.get(i).get("productPrice")) * Double.parseDouble(productQuantities.get(j));
                    totalPrice += x;
                    itemsTotal.add(String.valueOf(x));
                    productStorage.productList.get(i).put("productTotalSales", String.valueOf(Double.parseDouble(productStorage.productList.get(i).get("productTotalSales"))+x));

                    int y = Integer.parseInt(productQuantities.get(j));
                    productStorage.productList.get(i).put("productQuantity", String.valueOf(Integer.parseInt(productStorage.productList.get(i).get("productQuantity"))-y));
                }
            }
        }
        itemsInstance.put("itemID", productIDs);
        itemsInstance.put("itemQuantity", productQuantities);
        itemsInstance.put("itemTotal", itemsTotal);
        itemsList.add(itemsInstance);
    }

    //Create a new transaction
    public void addNewTransaction() {
        //Create sample transactions
        HashMap<String, String> transactionInstance = new HashMap<>();
        transactionInstance.put("ID", String.valueOf(transactionCount + 1));
        transactionInstance.put("Timestamp", String.valueOf(new Timestamp(System.currentTimeMillis())));
        transactionInstance.put("Total", String.valueOf(totalPrice));
        transactionInstance.put("itemsIndex", String.valueOf(transactionCount));
        transactionStorage.add(transactionInstance);
        transactionCount++;
        totalPrice = 0;
    }

    //Print all transactions
    public void printALlTransactions() throws IOException {
        productStorage productHandler = new productStorage();
        for (int i = 0; i < transactionStorage.size(); i++) {
            System.out.println("Transaction ID: " + transactionStorage.get(i).get("ID"));
            System.out.println("Timestamp: " + transactionStorage.get(i).get("Timestamp"));
            System.out.println("Total: " + transactionStorage.get(i).get("Total"));
            System.out.println("Items: ");
            for (int j = 0; j < itemsList.get(i).get("itemID").size(); j++) {
                System.out.println("Item ID: " + itemsList.get(i).get("itemID").get(j));
                System.out.println("Item Name: " + productHandler.searchProduct(String.valueOf(itemsList.get(i).get("itemID").get(j))).getProductName());
                System.out.println("Item Quantity: " + itemsList.get(i).get("itemQuantity").get(j));
                System.out.println("Item Total: " + itemsList.get(i).get("itemTotal").get(j));
            }
            System.out.println();
        }
    }

    //Print the transaction details
    public void printTransactionDetails() {
        System.out.println("Transaction ID: " + transactionStorage.get(transactionCount-1).get("ID"));
        System.out.println("Timestamp: " + transactionStorage.get(transactionCount-1).get("Timestamp"));
        System.out.println("Total: " + transactionStorage.get(transactionCount-1).get("Total"));
        System.out.println("Items: ");
        for (int j = 0; j < itemsList.get(transactionCount-1).get("itemID").size(); j++) {
            System.out.println("Item ID: " + itemsList.get(transactionCount-1).get("itemID").get(j));
            System.out.println("Item Quantity: " + itemsList.get(transactionCount-1).get("itemQuantity").get(j));
            System.out.println("Item Total: " + itemsList.get(transactionCount-1).get("itemTotal").get(j));
        }
        System.out.println();
    }

    //Remove the transaction
    public void removeTransaction() {
            itemsList.remove(searchResults.get("itemsIndex"));
            transactionStorage.remove(searchResults);
            transactionCount--;
    }

    //Remove the transaction
    public void removeTransaction(String transactionID) {
        for (HashMap<String, String> stringStringHashMap : transactionStorage) {
            if (stringStringHashMap.get("ID").equals(transactionID)) {
                itemsList.remove(stringStringHashMap.get("itemsIndex"));
                transactionStorage.remove(stringStringHashMap);

                transactionCount--;
                return;
            }
        }
        System.out.println("Transaction not found!");
    }

    //Search the transaction
    public transactionStorage searchTransaction(String transactionID) {
        for (HashMap<String, String> stringStringHashMap : transactionStorage) {
            if (stringStringHashMap.get("ID").equals(transactionID)) {
                searchResults = stringStringHashMap;
                return this;
            }
        }
        System.out.println("Transaction not found!");
        return null;
    }

}
