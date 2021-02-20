public class VIPCustomer extends Customer {

    int extraDiscount;


    VIPCustomer(String UserName, String password, String address, String phoneNumber,Membership membership) {
        super(UserName, password, address, phoneNumber);
        extraDiscount = setExtraDiscount(membership);
        this.membership= membership;
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
    boolean displayBill() {
        if (super.displayBill()) {
            double totalPrice = cart.calculateTotal();
            double finalPrice = cart.totalPrice - cart.calculateDiscounts();
//        String vipCheckout = cart.getBillList() +
//                "========================\n" +
//                "Total before discounts:\t" + totalPrice +
//                "\nSaved money:\t\t\t" + cart.calculateDiscounts() +
//                "\nAfter discount:\t\t\t" + finalPrice +
            System.out.println("Price after VIP saved(" + extraDiscount + "%):\t" + (finalPrice - calculateExtraDiscount(finalPrice)));
            return true;
        }
//        if (totalPrice != 0)
//            System.out.println(vipCheckout);
//        else System.out.println("No items in Cart");
        return false;
    }

    boolean pay() {
        if (cart != null)
            totalPayed -= calculateExtraDiscount(cart.totalPrice - cart.calculateDiscounts());
        if (super.pay()){
            return true;
        }
        return false;
    }
}
