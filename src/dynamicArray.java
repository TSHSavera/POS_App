/*
    Dynamic Array -  A dynamic array is an array with a big improvement: automatic resizing.
    Increases the size of the array by 2 times when it is full and decreases the size by half when it is 30% full.
    Fully compatible with java array class
*/
public class dynamicArray {
    //Initialize
    private String[] mainArray;
    private int arraySize, arrayCount, percentOccupied;

    public dynamicArray(int length) {
        arraySize = length;
        arrayCount = 0;
        mainArray = new String[length];
    }

    //Add elements to the array
    public void addElement(String value) {
        //Grow if the array is full
        if (arraySize == arrayCount) growArray();
        mainArray[arrayCount] = value;
        arrayCount++;
        //After adding, test if we can shrink array to optimize memory
        //Compute for the percentage
        percentOccupied = (arrayCount * 100) / arraySize;
        //Shrink if the array is 30% full
        if (percentOccupied <= 30) shrinkArray();
    }

    //Delete elements from the array
    public void deleteElement(int index) {
        //Check if index is not null
        if (mainArray[index] != null) {
            //Shift all elements to the left
            for (int i = index; i < arrayCount; i++) {
                mainArray[i] = mainArray[i + 1];
            }
            //Decrement the count
            arrayCount--;
            //After removing, test if we can shrink array to optimize memory
            //Compute for the percentage
            percentOccupied = (arrayCount * 100) / arraySize;
            //Shrink if the array is 30% full
            if (percentOccupied <= 30) shrinkArray();
        }
    }

    private void growArray() {
        //If array is full, double the size
        if (arraySize == arrayCount) {
            String[] tempArray = new String[arraySize * 2];
            //Copy the elements to the new array
            if (arraySize >= 0) System.arraycopy(mainArray, 0, tempArray, 0, arraySize);
            //Resize main array
            mainArray = tempArray;
            //Update handlers
            arraySize = arraySize * 2;
        }
    }

    private void shrinkArray() {
        //Compute for the percentage
        percentOccupied = (arrayCount * 100) / arraySize;
        //Shrink if array is 30% full
        if (percentOccupied <= 30) {
            //Shrink to half
            String[] tempArray = new String[arraySize / 2];
            //Copy the elements to the new array
            if (arrayCount >= 0) System.arraycopy(mainArray, 0, tempArray, 0, arrayCount);
            //Resize main array
            mainArray = tempArray;
            //Update handlers
            arraySize = mainArray.length;
        }
    }

    public String[] getArray() {
        return mainArray;
    }
    public int getArrayCount() {
        return arrayCount;
    }

    public int getEmptyIndexes() {
        return arraySize - arrayCount;
    }

    public void printArray() {
        for (int i = 0; i < arrayCount; i++) {
            System.out.println(mainArray[i]);
        }
    }


    //Sample Usage
    public static void main(String[] args) {
        //Instantiate dynamicArray
        dynamicArray da = new dynamicArray(1);

        //Add elements
        da.addElement("a");
        da.addElement("b");
        da.addElement("c");
        da.addElement("d");
        da.addElement("e");

        da.deleteElement(2);
        da.deleteElement(2);
        da.deleteElement(2);
        da.deleteElement(0);

        //Print elements
        System.out.println("Array Size: " + da.getArray().length);
        System.out.println("Array Count: " + da.getArrayCount());
        System.out.println("Empty Indexes: " + da.getEmptyIndexes());
        System.out.println("Start Printing Elements:");
        da.printArray();
    }
}
