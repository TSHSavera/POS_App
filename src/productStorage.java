import java.util.*;
public class productStorage {
    //Create a storage for each product
    static HashMap<String, String> productInstance = new HashMap<>();

    //Create a list of products
    static ArrayList<HashMap<String, String>> productList = new ArrayList<>();

    //Temp storage for search queries
    static Map<String, String> searchInstance = new HashMap<>();

    //Instantiate the class
    public productStorage() {
        if (productList == null || productList.isEmpty()) {
            //Re-instantiate the class
            productList = new ArrayList<>();
            //Create sample products
            productInstance.put("productID", "1");
            productInstance.put("productName", "Sample Product 1");
            productInstance.put("productPrice", "100");
            productInstance.put("productQuantity", "100");
            productInstance.put("productTotalSales", "0");
            productList.add(productInstance);
        }
    }

    //Test Values
    public boolean testProductValues(String type, String value) {
        if (value == null) return true;
        return !switch (type) {
            //Check if the value is a number
            case "productID", "productQuantity" ->
                    value.matches("[0-9]+");
            //
            case "productPrice", "productTotalSales" ->
                value.matches("[0-9.]+");
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
        //Loop through the product list
        newProduct.put("productID", String.valueOf((int) (Math.random() * 999999)));
        newProduct.put("productName", productName);
        newProduct.put("productPrice", productPrice);
        newProduct.put("productQuantity", productQuantity);
        newProduct.put("productTotalSales", "0");
        //Add the product to the list
        productList.add(newProduct);
    }

    public productStorage searchProduct(String productID) {
        //Remove the brackets from the productID - if it has any
        String trimmedPID = productID.replaceAll("\\[", "");
        trimmedPID = trimmedPID.replaceAll("]", "");
        //Perform Linear Search
        for (HashMap<String, String> stringStringHashMap : productList) {
            if (Objects.equals(stringStringHashMap.get("productID"), trimmedPID)) {
                searchInstance = stringStringHashMap;
                return this;
            }
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
        System.out.println(searchInstance.get("productID") + "\t" + searchInstance.get("productName") + "\t" + searchInstance.get("productPrice") + "\t" + searchInstance.get("productQuantity") + "\t" + searchInstance.get("productTotalSales"));
    }

    //Delete a product
    public String deleteProduct() {
        if (searchInstance == null) {
            throw new IllegalArgumentException("Error: There's no such product to registered.");
        }
        //Delete the product
        productList.remove(searchInstance);
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
        for (HashMap<String, String> stringStringHashMap : productList) {
            System.out.println(stringStringHashMap.get("productID") + "\t" + stringStringHashMap.get("productName") + "\t" + stringStringHashMap.get("productPrice") + "\t" + stringStringHashMap.get("productQuantity") + "\t" + stringStringHashMap.get("productTotalSales"));
        }
    }
    public void showProductQuantity() {
        //Print the header
        System.out.println("Product ID\tProduct Name\tProduct Quantity");
        //Print all products
        for (HashMap<String, String> stringStringHashMap : productList) {
            System.out.println(stringStringHashMap.get("productID") + "\t" + stringStringHashMap.get("productName") + "\t" + stringStringHashMap.get("productQuantity"));
        }
    }

}


