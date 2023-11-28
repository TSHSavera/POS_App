import java.util.*;
public class productStorage {
    //Create a storage for each product
    static Map<String, String> productStorage = new HashMap<String, String>();

    //Create a list of products
    static List<Map<String, String>> productList = new ArrayList<>();

    //Total number of products
    static int productCount = 0;

    //Instantiate the class
    public productStorage() {
        //Create sample products
        productStorage.put("productID", "1");
        productStorage.put("productName", "Sample Product 1");
        productStorage.put("productPrice", "100");
        productStorage.put("productQuantity", "100");
        productStorage.put("productTotalSales", "0");
        productStorage.put("productTotalProfit", "0");
        productList.add(productStorage);
        productCount++;

        productStorage.put("productID", "2");
        productStorage.put("productName", "Sample Product 2");
        productStorage.put("productPrice", "200");
        productStorage.put("productQuantity", "200");
        productStorage.put("productTotalSales", "0");
        productStorage.put("productTotalProfit", "0");
        productList.add(productStorage);
        productCount++;
    }

    //Test Values
    public boolean testProductValues(String type, String value) {
        return switch (type) {
            //Check if the value is a number
            case "productID", "productPrice", "productQuantity", "productTotalSales", "productTotalProfit" ->
                    value.matches("[0-9]+");
            //Check if the value is an alphanumeric string
            case "productName" -> value.matches("[a-zA-Z0-9]+");
            default -> false;
        };
    }

    //Add a new product
    public void addProduct(String productName, String productPrice, String productQuantity) {
        //Create a new product
        Map<String, String> newProduct = new HashMap<String, String>();
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

}
