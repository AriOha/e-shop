public class Customer extends User {

    protected String address, phoneNumber;
    protected double totalPayed;
    protected int totalItems;
    Cart cart;

    Customer(String UserName, String password, String address, String phoneNumber) {
        super(UserName, password);
        setAddress(address);
        setPhoneNumber(phoneNumber);
        this.totalPayed = 0;
        this.totalItems = 0;
    }

    public Cart getCart() {
        return cart;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getUserName() {
        return super.getUserName();
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10)
            if (phoneValidator(phoneNumber))
                this.phoneNumber = phoneNumber;
    }


    private boolean phoneValidator(String phoneNumber) {
        for (int i = 0; i < phoneNumber.length(); i++)
            if (phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9')
                return false;
        return true;
    }

    void changeAddress(String newAddress) {
        if (!this.address.equals(newAddress)) {
            setAddress(newAddress);
        }
    }

    void changePhone(String newPhoneNumber) {
        if (!this.phoneNumber.equals(newPhoneNumber)) {
            setPhoneNumber(newPhoneNumber);
        }
    }


    void takeCart(Cart cart) {
        if (cart.getOwnedByCustomer() != null) {
            System.out.println("Cart " + cart.getCartId() + " already taken");
        } else if (this.cart == null) {
            this.cart = cart;
            this.cart.setCustomer(this);
        } else System.out.println("User already has a cart!");
    }


    void releaseCart() {
        if (this.cart != null) {
            this.cart.emptyCart();
            this.cart.removeCustomer(null);
            this.cart = null;
        }
    }

    void emptyCart() {
        if (this.cart != null)
            this.cart.emptyCart();
    }

    void addProductToCart(Product item) {
        this.cart.addItem(item);
        this.cart.calculateTotal();
    }

    void removeProductFromCart(int catalogNumber) {
        this.cart.removeItem(catalogNumber);
        this.cart.calculateTotal();
    }


    void displayBill() {
        double totalPrice = cart.calculateTotal();
        String checkout = cart.getBillList() +
                "========================\n" +
                "Total before discounts:\t" + totalPrice +
                "\nSaved money:\t\t\t" + cart.calculateDiscounts() +
                "\nAfter discount:\t\t\t" + (cart.totalPrice - cart.calculateDiscounts());
        if (totalPrice != 0)
            System.out.println(checkout);
        else System.out.println("No items in Cart.");
    }


    void pay() {
        if (cart != null) {
            totalPayed += cart.totalPrice - cart.calculateDiscounts();
            totalItems += cart.productsList.size();
            cart.emptyCart();
        }

    }

}