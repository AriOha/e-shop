import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class VIPCustomer extends Customer {

    private int extraDiscount;
    int points;

    VIPCustomer(String UserName, String password, String address, String phoneNumber, Membership membership) {
        this(UserName, password, address, phoneNumber, 0, 0, 0, membership);
    }

    VIPCustomer(String UserName, String password, String address, String phoneNumber, int points, double totalPayed, int totalItems,
                Membership membership) {
        super(UserName, password, address, phoneNumber, totalPayed, totalItems);
        this.points = points;
        this.membership = membership;
        this.extraDiscount = setExtraDiscount(membership);
    }

    VIPCustomer(Scanner s, Membership membership) throws FileNotFoundException {
        super(s);
        this.points = s.nextInt();
        this.membership = membership;
        this.extraDiscount = setExtraDiscount(membership);
    }

    int setExtraDiscount(Membership discount) {
        return switch (discount) {
            case Bronze -> 10;
            case Gold -> 20;
            case Platinum -> 30;
            default -> 0;
        };
    }

    void showDetails() {
        super.showDetails();
        if (membership != Membership.Basic)
            System.out.println("Your points are: " + points);
    }

    double calculateExtraDiscount(double price) {
        return price * extraDiscount / 100;

    }

    @Override
    public void save(PrintWriter pw) {
        super.save(pw);
        pw.println(points);
    }

    @Override
    boolean displayBill() {
        if (super.displayBill()) {
            double finalPrice = cart.totalPrice - cart.calculateDiscounts();
            System.out.println("Price after VIP saved(" + extraDiscount + "%):\t"
                    + (finalPrice - calculateExtraDiscount(finalPrice)));
            return true;
        }
        return false;
    }

    boolean pay() {

        double total = cart.totalPrice - cart.calculateDiscounts();
        total -= calculateExtraDiscount(cart.totalPrice - cart.calculateDiscounts());
        if (cart != null)
            totalPayed -= calculateExtraDiscount(cart.totalPrice - cart.calculateDiscounts());
        if (super.pay()) {
            points += (int) Math.round(total * 0.05);
            rankUpdate();
            return true;
        }
        return false;
    }

    void rankUpdate() {
        switch (membership) {
            case Bronze -> {
                if (points >= 500) {
                    this.membership = Membership.Gold;
                    System.out.println("Congrats! you are now a |Gold| member");
                    points = 0;
                }
            }
            case Gold -> {
                if (points >= 1000) {
                    this.membership = Membership.Platinum;
                    System.out.println("Congrats! you are now a |Platinum| member");
                    points = 0;
                }
            }

        }
        System.out.println("Your Points total is: " + points);

    }
}

