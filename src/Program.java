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

        if (name != null && price != null) {
            Item item = new Item(name, price);
            item.attemptPriceReduction(discountAmount);

            System.out.println(item.toString());
            System.out.println("Price: " + item.getPrice());

            if (item.promotion != null) {
                System.out.println(item.promotion.isPromotion);
            }
        } else {
            System.out.println("provide a name and price");
        }


    }




}


