import java.io.*;
import java.util.*;

public class dataStorage {
    //Store all data in a file
    //JSON format
    String json = "[";
    //JSON to ArrayList
    String line;
    ArrayList<HashMap<String,String>> convertedJson = new ArrayList<>();
    //Create a file - store the data in the file. A chain method of toJson() and storeToFile()
    //will be used to store the data in the file.
    void storeToFile(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        try {
            fileWriter.write(json);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to save file");
        }
        System.out.println("File saved successfully");

    }

    //Convert the data to JSON
    public dataStorage toJson(ArrayList<HashMap<String, String>> data) {
        StringBuilder json = new StringBuilder("[");
        for (HashMap<String, String> x : data) {
            json.append("{");
            for (String key : x.keySet()) {
                json.append("\"").append(key).append("\":\"").append(x.get(key)).append("\",");
            }
            json = new StringBuilder(json.substring(0, json.length() - 1));
            json.append("},");
        }
        json = new StringBuilder(json.substring(0, json.length() - 1));
        json.append("]");

        return this;
    }

    //Read from the file
    public dataStorage readJson(String jsonFile) throws IOException {
        try {
            //Store the data in a string
            BufferedReader br = new BufferedReader(new FileReader(jsonFile));
            line = br.readLine(); //remove [
            //Traverse the file
            while ((line = br.readLine()) != null) {
                if (line.contains("}")) {
                    HashMap<String, String> singleData = new HashMap<>();
                    convertedJson.add(singleData);
                } else {
                    //Process JSON, remove unnecessary characters
                    String[] keyValue = line.split("\",\"");

                    String key = keyValue[0].replace("\"", "");
                    String value = keyValue[1].replace("\"", "");
                    value = value.replaceAll(",", "");

                    //Get the latest HashMap in the ArrayList - Then put
                    HashMap<String, String> currentMap = convertedJson.get(convertedJson.size() - 1);
                    currentMap.put(key, value);
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read file.");
        }
        return this;
    }
    //Override the data in the specific class
    public void overrideAuthData() {
        authOperations.listOfAccounts = convertedJson;
    }

    public void overrideProductData() {
        productStorage.productList = convertedJson;
    }

    void storeToFile2(String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        try {
            fileWriter.write(String.valueOf(productStorage.productList));
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to save file");
        }
        System.out.println("File saved successfully");

    }
    ArrayList<HashMap<String,String>> stringToMaps(String str){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();

        //Converter to remove brackets and braces
        String var = str.replaceAll("/\\[/g", "");
        var = var.replaceAll("/]/g", "");
        var = var.replaceAll("/\\{/g", "");
        //var = var.replace("}", "");

        HashMap<String, String> a = new HashMap<>();
        String[] maps = var.split("},");

        for (String map: maps) {
            String[] keyValues = var.split(",");

            for (String kv : keyValues) {

                String[] split = kv.split("=");
                String key = split[0].trim();
                String value = split[1];

                a.put(key, value);
            }
            list.add(a);
        }
        return list;
    }

    public void readJson2(String jsonFile) throws IOException {
        try {
            //Store the data in a string
            BufferedReader br = new BufferedReader(new FileReader(jsonFile));
            line = br.readLine(); //remove [
            System.out.println(line);

            ArrayList<HashMap<String, String>> pList = new ArrayList<HashMap<String, String>>();
            pList = stringToMaps(line);
            System.out.println(pList);
            productStorage.productList = pList;

           // System.out.println(pList.get(0).get("productID"));

        } catch (IOException e) {
            System.out.println("Failed to read file.");
        }
    }


}
