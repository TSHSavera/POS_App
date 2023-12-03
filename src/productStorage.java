import java.util.*;
public class productStorage {
    //Create a storage for each product
    static HashMap<String, String> productInstance = new HashMap<>();

    //Create a list of products
    static ArrayList<HashMap<String, String>> productList = new ArrayList<HashMap<String, String>>();

    //Total number of products
    static int productCount = 0;

    //Temp storage for search queries
    static Map<String, String> searchInstance = new HashMap<>();

    //Instantiate the class
    public productStorage() {
        if (productList.isEmpty()) {
            //Create sample products
            productInstance.put("productID", "1");
            productInstance.put("productName", "Sample Product 1");
            productInstance.put("productPrice", "100");
            productInstance.put("productQuantity", "100");
            productInstance.put("productTotalSales", "0");
            productInstance.put("productTotalProfit", "0");
            productList.add(productInstance);
            productCount++;

        }
    }

    //Test Values
    public boolean testProductValues(String type, String value) {
        if (value == null) return true;
        return !switch (type) {
            //Check if the value is a number
            case "productID", "productPrice", "productQuantity", "productTotalSales", "productTotalProfit" ->
                    value.matches("[0-9]+");
            //Check if the value is an alphanumeric string
            case "productName" -> value.matches("[a-zA-Z0-9 ]+");
            default -> false;
        };
    }

    //Add a new product
    public void addProduct(String productName, String productPrice, String productQuantity) {
        //Create a new product
        HashMap<String, String> newProduct = new HashMap<>();
        //Add the product details
        newProduct.put("productID", String.valueOf(productCount + 1));
        newProduct.put("productName", productName);
        newProduct.put("productPrice", productPrice);
        newProduct.put("productQuantity", productQuantity);
        newProduct.put("productTotalSales", "0");
        newProduct.put("productTotalProfit", "0");
        //Add the product to the list
        productList.add(newProduct);
        //Increment the product count
        productCount++;
    }

    public productStorage searchProduct(String productID) {
        //Perform binary search for the product
        int first = 0;
        int last = productCount - 1;
        int mid = (first + last) / 2;

        while (last >= first) {
            if (Objects.equals(productID, productList.get(mid).get("productID"))) {
                searchInstance = productList.get(mid);
                return this;
            } else if (Integer.parseInt(productID) > Integer.parseInt(productList.get(mid).get("productID"))) {
                first = mid + 1;
            } else {
                last = mid - 1;
            }
            mid = (first + last) / 2;
        }
        return null;
    }

    //Create a chain method for searching products
    //Get the product name
    public String getProductName() {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: Invalid argument for 'product' was passed. - " + searchInstance + " does not exist.");
        }
        //Return the product name
        return searchInstance.get("productName");
    }
    //Get the product quantity
    public int getProductQuantity() {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: Invalid argument for 'product' was passed. - " + searchInstance + " does not exist.");
        }
        //Return the product quantity
        return Integer.parseInt(searchInstance.get("productQuantity"));
    }
    //Get the product total sales
    public double getProductTotalSales() {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: Invalid argument for 'product' was passed. - " + searchInstance + " does not exist.");
        }
        //Return the product total sales
        return Double.parseDouble(searchInstance.get("productTotalSales"));
    }
    //Get the product price
    public double getProductPrice() {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: Invalid argument for 'product' was passed. - " + searchInstance + " does not exist.");
        }
        //Return the product price
        return Double.parseDouble(searchInstance.get("productPrice"));
    }

    //Print all details
    public void printProductDetails() {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: Invalid argument for 'product' was passed. - " + searchInstance + " does not exist.");
        }
        //Print the header
        System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity\tProduct Total Sales\tProduct Total Profit");
        //Print the product details
        System.out.println(searchInstance.get("productID") + "\t" + searchInstance.get("productName") + "\t" + searchInstance.get("productPrice") + "\t" + searchInstance.get("productQuantity") + "\t" + searchInstance.get("productTotalSales") + "\t" + searchInstance.get("productTotalProfit"));
    }

    //Delete a product
    public String deleteProduct() {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: There's no such product to registered.");
        }
        //Delete the product
        productList.remove(searchInstance);
        //Decrement the product count
        productCount--;
        return searchInstance.get("productID");
    }

    //Change product details
    public void changeProductDetails(String key, String value) {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: There's no such product to registered.");
        }
        if (Objects.equals(key, "productID")) {
            //This should not be changed
            throw new IllegalArgumentException("Error: Invalid argument for 'key' was passed. - " + key + " cannot be changed.");
        }
        //Change the product details
        searchInstance.put(key, value);
    }

    //Print all products
    public void printAllProducts() {
        //Print the header
        System.out.println("Product ID\tProduct Name\tProduct Price\tProduct Quantity\tProduct Total Sales\tProduct Total Profit");
        //Print all products
        for (int i = 0; i < productCount; i++) {
            System.out.println(productList.get(i).get("productID") + "\t" + productList.get(i).get("productName") + "\t" + productList.get(i).get("productPrice") + "\t" + productList.get(i).get("productQuantity") + "\t" + productList.get(i).get("productTotalSales") + "\t" + productList.get(i).get("productTotalProfit"));
        }
    }



}

class productTracker extends productStorage {
    //Adjust the product quantity
    public boolean adjustProductQuantity(String productID, String quantity) {
        //Search for the product
        productStorage product = searchProduct(productID);
        //If the product is not found, return false
        if (product == null) return false;
        //Get the current quantity
        int currentQuantity = Integer.parseInt(searchInstance.get("productQuantity"));
        //Get the new quantity
        int newQuantity = currentQuantity + Integer.parseInt(quantity);
        //Change the product quantity
        changeProductDetails("productQuantity", String.valueOf(newQuantity));
        return true;
    }

    //Show current product quantity of all products
    public void showProductQuantity() {
        //Print the header
        System.out.println("Product ID\tProduct Name\tProduct Quantity");
        //Print all products
        for (int i = 0; i < productCount; i++) {
            System.out.println(productList.get(i).get("productID") + "\t" + productList.get(i).get("productName") + "\t" + productList.get(i).get("productQuantity"));
        }
    }



}
