public class VIPCustomer extends Customer {

    int extraDiscount;


    VIPCustomer(String UserName, String password, String address, String phoneNumber) {
        super(UserName, password, address, phoneNumber);
        extraDiscount = 10;
    }

    void setExtraDiscount(int extraDiscount) {
        if (extraDiscount >= 0 && extraDiscount <= 50)
            this.extraDiscount = extraDiscount;
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

    void pay() {
        if (cart != null)
            totalPayed -= calculateExtraDiscount(cart.totalPrice - cart.calculateDiscounts());
        super.pay();
    }
}
