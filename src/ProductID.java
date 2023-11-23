public class ProductID {
    int[] arrayID;
    int sizeArray;
    int countArray;

    ProductID(){
        arrayID = new int [1];
        sizeArray=1;
        countArray=0;
    }
    void generateID() {
        if (sizeArray==countArray){
            increaseArray();
        }
        arrayID[countArray] = (int)(10000*Math.random()+1000000);
        countArray++;
    }
    void increaseArray(){
        int[] temp;
        if (sizeArray == countArray) {
            temp = new int[sizeArray * 2];

            for (int i = 0; i < sizeArray; i++) {
                temp[i] = arrayID[i];
            }
            arrayID = temp;
            sizeArray = 2;
        }
    }
}