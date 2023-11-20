import java.util.*;

public class etits {
    public static void main(String[] args){
        Methods meth = new Methods();

        int choices=0;

        Scanner input = new Scanner (System.in);
        Scanner inputString = new Scanner(System.in);

        do{
            System.out.println("\n\n1. Add new Product\n2. Print all Product/s\n3. Exit");
            System.out.println("\nEnter a number: ");
            choices = input.nextInt();

            if (choices==1){
                System.out.println("Enter a Product you want to add in the list: ");
                meth.addProduct(inputString.nextLine());
            }
            else if(choices==2){
                System.out.println("\n");
                for(int i=0; i< meth.arrayCount; i++){
                    System.out.println("\nProduct Name: "+meth.Array[i]+"\nProduct ID: ");
                }
            }
        }
        while (choices!=3);
    }
}