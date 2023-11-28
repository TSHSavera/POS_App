import java.util.*;
public class productStorage {
    //Create a storage for each product
    static Map<String, String> productInstance = new HashMap<>();

    //Create a list of products
    static List<Map<String, String>> productList = new ArrayList<>();

    //Total number of products
    static int productCount = 0;

    //Instantiate the class
    public productStorage() {
        //Create sample products
        productInstance.put("productID", "1");
        productInstance.put("productName", "Sample Product 1");
        productInstance.put("productPrice", "100");
        productInstance.put("productQuantity", "100");
        productInstance.put("productTotalSales", "0");
        productInstance.put("productTotalProfit", "0");
        productList.add(productInstance);
        productCount++;

        productInstance.put("productID", "2");
        productInstance.put("productName", "Sample Product 2");
        productInstance.put("productPrice", "200");
        productInstance.put("productQuantity", "200");
        productInstance.put("productTotalSales", "0");
        productInstance.put("productTotalProfit", "0");
        productList.add(productInstance);
        productCount++;
    }

    //Test Values
    public boolean testProductValues(String type, String value) {
        if (value == null) return false;
        return switch (type) {
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
        Map<String, String> newProduct = new HashMap<>();
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

    public Map<String, String> searchProduct(String productID) {
        //Perform binary search for the product
        int first = 0;
        int last = productCount - 1;
        int mid = (first + last) / 2;

        while (last >= first) {
            if (Objects.equals(productID, productList.get(mid).get("productID"))) {
                return productList.get(mid);
            } else if (Integer.parseInt(productID) > Integer.parseInt(productList.get(mid).get("productID"))) {
                first = mid + 1;
            } else {
                last = mid - 1;
            }
            mid = (first + last) / 2;
        }
        return null;
    }

    //Delete a product
    public void deleteProduct(String productID) {
        //Search for the product
        Map<String, String> product = searchProduct(productID);
        //Delete the product
        productList.remove(product);
        //Decrement the product count
        productCount--;
    }

    //Change product details
    public void changeProductDetails(String productID, String key, String value) {
        //Search for the product
        Map<String, String> product = searchProduct(productID);
        if (key == "productID") {
            //This should not be changed
            throw new IllegalArgumentException("Error: Invalid argument for 'key' was passed. - " + key + " cannot be changed.");
        }
        //Change the product details
        product.put(key, value);
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
