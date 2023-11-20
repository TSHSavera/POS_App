import java.util.*;
public class Methods {
    String[] Array;
    int arraySize;
    int arrayCount;

    Methods(){
        Array = new String[1];
        arraySize = 1;
        arrayCount = 0;
    }
    public void addProduct(String value){
        if (arraySize==arrayCount) {
            growSize();
        }
    Array[arrayCount] = value;
    arrayCount++;
    }
    public void growSize() {
        String[] temp;
        if (arraySize == arrayCount) {
            temp = new String[arraySize * 2];

            for (int i = 0; i < arraySize; i++) {
                temp[i] = Array[i];
            }
            Array = temp;
            arraySize = 2;
        }
    }
}
