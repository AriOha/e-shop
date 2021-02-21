import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class VIPCustomer extends Customer {

	int extraDiscount;
	int points = 0;

	VIPCustomer(String UserName, String password, String address, String phoneNumber, Membership membership) {
		this(UserName, password, address, phoneNumber, 0, 0, membership);
	}

	VIPCustomer(String UserName, String password, String address, String phoneNumber, double totalPayed, int totalItems,
			Membership membership) {
		super(UserName, password, address, phoneNumber, totalPayed, totalItems);
		this.extraDiscount = setExtraDiscount(membership);
		this.membership = membership;

	}

	VIPCustomer(Scanner s, Membership membership) throws FileNotFoundException {
		super(s);
		this.membership = membership;
	}

	int setExtraDiscount(Membership discount) {
		return switch (discount) {
		case Bronze -> 10;
		case Gold -> 20;
		case Platinum -> 30;
		default -> 0;
		};
	}

	double calculateExtraDiscount(double price) {
		return price * extraDiscount / 100;

	}

	@Override
	public void save(PrintWriter pw) {
		super.save(pw);
	}

	@Override
	boolean displayBill() {
		if (super.displayBill()) {
			cart.calculateTotal();
			double finalPrice = cart.totalPrice - cart.calculateDiscounts();
			System.out.println("Price after VIP saved(" + extraDiscount + "%):\t"
					+ (finalPrice - calculateExtraDiscount(finalPrice)));
			return true;
		}
		return false;
	}

	boolean pay() {
		double total = cart.totalPrice - cart.calculateDiscounts();
		total = total - calculateExtraDiscount(cart.totalPrice - cart.calculateDiscounts());

		if (cart != null)
			totalPayed -= calculateExtraDiscount(cart.totalPrice - cart.calculateDiscounts());
		if (super.pay()) {
			points += (int) Math.round(total * 0.05);
			if (points > 0) {
				System.out.println("Your Points total is: " + points);
			}
			return true;
		}
		return false;
	}
}


