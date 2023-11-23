import java.util.*;

public class etits {
    public static void main(String[] args){
        Methods meth = new Methods();
        ProductID prod = new ProductID();

        int choices;

        Scanner input = new Scanner (System.in);
        Scanner inputString = new Scanner(System.in);

        do{
            System.out.println("\n\n1. Add new Product\n2. Print all Product/s\n3. Exit");
            System.out.print("\nEnter your choice: ");
            choices = input.nextInt();

            if (choices==1){
                System.out.print("Enter a Product that you want to add in the list: ");
                meth.addProduct(inputString.nextLine());
                prod.generateID();
                System.out.println("Product Registered Successfully!");
            }
            else if(choices==2){
                System.out.println();
                for(int i=0; i< meth.arrayCount; i++){
                    System.out.println("\nProduct Name: "+meth.Array[i]+"\nProduct ID: " + prod.arrayID[i]);
                }
            }
        }
        while (choices!=3);
    }
}