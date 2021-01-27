public class Customer extends User {

    protected String address, phoneNumber;
    Cart cart;

    Customer(String UserName, String password,String address,String phoneNumber) {
        super(UserName, password);
        setAddress(address);
        setPhoneNumber(phoneNumber);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10)
            if (phoneValidator(phoneNumber))
                this.phoneNumber = phoneNumber;
    }

    public void setCart(Cart currentCart) {
        this.cart = currentCart;
    }

    private boolean phoneValidator(String phoneNumber) {
        for (int i = 0; i < phoneNumber.length(); i++)
            if (phoneNumber.charAt(i) < '0' || phoneNumber.charAt(i) > '9')
                return false;
        return true;
    }
}

