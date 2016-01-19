import java.util.Scanner;

public class Program {


    public static void main(String[] args) {

        Scanner a = new Scanner(System.in);

        System.out.println("Enter Item name");
        String name = a.nextLine();

        System.out.println("Enter Item price");
        Double price = a.nextDouble();

        System.out.println("Enter discount amount");
        Double discountAmount = a.nextDouble();

        Item item = new Item(name, price);
        item.reducePrice(discountAmount);

        System.out.println(item.toString());
        System.out.println("Price: " + item.getPrice());

        if (item.promotion != null) {
            System.out.println(item.promotion.isPromotion);
        }
    }




}


