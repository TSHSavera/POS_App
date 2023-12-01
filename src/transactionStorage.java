import java.util.*;
public class transactionStorage {
    //Create a storage for each transaction
    static Map<String, Transaction> transactions = new HashMap<>();
    //Total number of transactions
    static int transactionCount = 0;

    //Instantiate the class
    public transactionStorage() {
        //Create sample transactions
        Transaction t1 = new Transaction();
        t1.addItem(new Items("1", 1));
        t1.addItem(new Items("2", 2));
        transactions.put(t1.getTransactionID(), t1);
    }

    //Add a new transaction
    void addTransaction(Transaction t) {
        transactions.put(t.getTransactionID(), t);
        transactionCount++;
    }

    //Get the transaction details
    Transaction getTransaction(String transactionID) {
        return transactions.get(transactionID);
    }

    //Get the total number of transactions
    int getTransactionCount() {
        return transactionCount;
    }

    //Get all the transactions
    Map<String, Transaction> getAllTransactions() {
        return transactions;
    }

    //

    //transactionDetails class to save the transaction details
    static class Transaction {
        String id;
        long timestamp;
        List<Items> items;

        //Create a new transaction - on call, only set up the transaction ID and timestamp
        public Transaction() {
            this.id = UUID.randomUUID().toString();
            this.timestamp = new Date().getTime();
            items = new ArrayList<>();
        }
        public void addItem(Items x) {
            items.add(x);
        }
        //Get the transaction ID
        public String getTransactionID() {
            return id;
        }
        //Get the timestamp
        public long getTimestamp() {
            return timestamp;
        }
    }

    //purchasedItems class to save the items in the transaction
    static class Items {
        //Instantiate productStorage - to get product prices
        String productID;
        int quantity;
        double totalDue;

        //Create a new purchased item
        public Items(String productID, int quantity) {
            this.productID = productID;
            this.quantity = quantity;
            this.totalDue = new productStorage().searchProduct(productID).getProductPrice() * quantity;
        }

        //Get the product ID
        public String getItemID() {
            return productID;
        }
        //Get the quantity
        public int getQuantity() {
            return quantity;
        }
        //Get the total due
        public double getTotalDue() {
            return totalDue;
        }
        //Convert the purchased item to a string
        @Override
        public String toString() {
            return "Product ID: " + productID + "\nQuantity: " + quantity + "\nTotal Due: " + totalDue;
        }

    }

}
