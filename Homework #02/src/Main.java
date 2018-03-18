import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Car[] cars = new Car[1];
        cars[0] = new FordFocus();

        System.out.println("List of models:");
        for (int i = 0; i < cars.length; i++) {
            System.out.println((i + 1) + ". " + cars[i].model);
        }
        System.out.println("Enter number of car to see its characteristics or '0' to exit");
        Scanner scan = new Scanner(System.in);
        int ind;

        while ((ind = scan.nextInt()) != 0) {
            if (ind <= cars.length && ind >= 0) {
                cars[ind - 1].info();
            }
            else {
                System.out.println("Wrong number.");
            }
        }
    }
}