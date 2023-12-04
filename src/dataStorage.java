import java.io.*;
import java.util.*;

public class dataStorage {
    //JSON to ArrayList
    String line;
    //Create a file - store the data in the file.
    // A chain method of toJson() and storeToFile()
    //will be used to store the data in the file.
    void storeToFile (String fileName, ArrayList<HashMap<String, String>> listToSave) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        StringBuilder listValues = new StringBuilder();
        for (HashMap<String, String> pl: listToSave) {
            for (String key: pl.keySet()) {
                listValues.append(key).append("=").append(pl.get(key)).append(",");
            }
            listValues.append("#");
        }
        try {
            fileWriter.write(listValues.toString());
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("Failed to save file");
        }
        System.out.println("File saved successfully");

    }

    //Convert the data to my own format
    ArrayList<HashMap<String,String>> stringToMaps(String str){
        ArrayList<HashMap<String,String>> list = new ArrayList<>();
        //Get values from the string
        String[] listValues = str.split(",#");
        //Loop through the values
        for (String lv: listValues) {
            //Create a new HashMap
            HashMap<String,String> map = new HashMap<>();
            //Get the key-value pairs
            String[] keyValues = lv.split(",");
            //Loop through the key-value pairs
            for (String kv: keyValues) {
                //Split the key-value pairs
                String[] split = kv.split("=");
                //Get the key and value
                String key = split[0].trim();
                String value = split[1];
                //Put the key-value pair in the HashMap
                map.put(key,value);
            }
            //Add the HashMap to the ArrayList
            list.add(map);
        }
        return list;
    }

    //Read the file
    public ArrayList<HashMap<String, String>> readSaveFile(String fiLeName) {
        try {
            //Store the data in a string
            BufferedReader br = new BufferedReader(new FileReader(fiLeName));
            line = br.readLine();

            ArrayList<HashMap<String, String>> vList;
            vList = stringToMaps(line);
            return vList;

        } catch (IOException e) {
            System.out.println("Failed to read file.");
            return null;
        }
    }

    //Override the data in the specific class
    public static void overrideAuthData(ArrayList<HashMap<String, String>> aList) {
        try {
            authOperations.listOfAccounts = aList;
        } catch (NullPointerException e) {
            System.out.println("Overriding Failed, loading default values.");
        }
    }

    public static void overrideProductData(ArrayList<HashMap<String, String>> pList){
        try {
            productStorage.productList = pList;
        } catch (NullPointerException e) {
            System.out.println("Overriding Failed, loading default values.");
        }
    }





}
