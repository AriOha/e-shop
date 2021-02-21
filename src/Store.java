import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Store {

    int maxElements = 10;
    ArrayList<Product> productsList;
    ArrayList<Customer> customersList;
    ArrayList<Cart> cartList;
    private final int maxCarts, maxCustomers, maxProducts;

    Store() {
        productsList = new ArrayList<>();
        customersList = new ArrayList<>();
        cartList = new ArrayList<>();
        maxProducts = maxElements;
        maxCustomers = maxElements;
        maxCarts = maxElements;
    }

    public int getMaxCarts() {
        return maxCarts;
    }

    public int getMaxCustomers() {
        return maxCustomers;
    }

    public int getMaxProducts() {
        return maxProducts;
    }

    public int getCartsInUse() {
        return cartList.size();
    }

    public int getProductsNumInStore() {
        return productsList.size();
    }

    public int registeredCustomers() {
        return customersList.size();
    }

    boolean signUp(String userName, String password, String address, String phoneNumber) {
        return signUp(userName, password, address, phoneNumber, User.Membership.Basic);

    }

    boolean signUp(String userName, String password, String address, String phoneNumber, User.Membership membership) {
        int currentCustomers = customersList.size();
        if (customersList.size() < maxCustomers)
            if (membership != User.Membership.Basic) {
                if (customersList.add(new VIPCustomer(userName, password, address, phoneNumber, membership)))
                    System.out.println("VIP customer registered successfully.");
            } else {
                if (customersList.add(new Customer(userName, password, address, phoneNumber)))
                    System.out.println("Customer registered successfully.");
            }
        return currentCustomers > customersList.size();
    }

    public void deleteCustomer(Customer customer) throws NullPointerException {
//        perform delete to customer from the list,
//        If user = null(not exists) throws NullPointerException
        customer.releaseCart();
        customersList.remove(customer);
        System.out.println(customer.getUserName() + " removed from the list");
    }


    Customer searchForCustomer(String username) {
        for (Customer customer : customersList)
            if (customer.getUserName().equals(username)) {
                return customer;
            }
        return null;
    }

    Product searchForProductInStore(String productId) throws NumberFormatException {
        for (Product product : productsList)
            if (product.getCatalogId() == Integer.parseInt(productId)) {
                return product;
            }
        return null;
    }

    Cart searchForCart(String cartId) throws NumberFormatException {
        for (Cart cart : cartList)
            if (cart.getCartId() == Integer.parseInt(cartId)) {
                return cart;
            }
        return null;
    }


    void removeCart(String cartId) throws NullPointerException, NumberFormatException {
        Cart cart = searchForCart(cartId);
        releaseCustomer(cart);
        if (cartList.remove(cart))
            System.out.println("Cart " + cartId + " removed from the store");

    }


    void releaseCustomer(Cart cartToRemove) {
        if (cartToRemove.getOwnedByCustomer() != null) {
            cartToRemove.ownedByCustomer.releaseCart();
        }
    }


    boolean addCart() {
        if (cartList.size() < maxCarts) {
            return cartList.add(new Cart(maxProducts));
        }
        return false;
    }


    void displayOnlineCustomers() {
        StringBuilder isOnline = new StringBuilder("Online Customers:\n");
        for (Customer customer : customersList) {
            if (customer.isLoggedIn) {
                isOnline.append("Username: ").append(customer.getUserName()).append("\t |").append(customer.membership).append(" membership|\n");
            }
        }
        System.out.println(isOnline);
    }

    void takenCarts() {
        int taken = 0;
        for (Cart cart : cartList)
            taken += (cart.ownedByCustomer != null) ? 1 : 0;
        System.out.println("Taken carts: " + taken + " of " + cartList.size() + " available.(max=" + maxCarts + ")");
    }

    void displayCarts() {
        System.out.println("Carts in the store:");
        for (Cart cart : cartList) {
            System.out.println(cart);
        }
    }

    void displayProducts() {
        System.out.println("Products in the store:");
        for (Product product : productsList) {
            System.out.println(product);
        }
    }

    void displayCustomers() {
        System.out.println("Registered customers:");
        for (Customer customer : customersList) {
            System.out.println(customer.shortInfo());
        }
    }

    void earningsReport() {
        double totalSales = 0;
        int totalItems = 0;

        for (Customer customer : customersList)
            if (customer != null) {
                totalSales += customer.totalPayed;
                totalItems += customer.totalItems;
            }
        System.out.println("Total earnings: " + totalSales);
        System.out.println("Total sold items: " + totalItems);
    }

    boolean registerCustomer(User.Membership membership) {
        Scanner s = new Scanner(System.in);
        String username = "";
        String pass = "";
        String address = "";
        String phone = "";
        boolean validUsername, validPassword, validAddr, validPhone;
        validUsername = validPassword = validAddr = validPhone = false;

        do {
            if (!validUsername) {
                System.out.println("Enter username: ");
                username = s.nextLine();
            }
            if (!validPassword) {
                System.out.println("Enter password: ");
                pass = s.nextLine();
            }
            if (!validAddr) {
                System.out.println("Enter address: ");
                address = s.nextLine();
            }
            if (!validPhone) {
                System.out.println("Enter phone number: ");
                phone = s.nextLine();
            }

            validUsername = Validators.validUserName(username, this);
            validPassword = Validators.validPassword(pass);
            validAddr = Validators.validAddress(address);
            validPhone = Validators.phoneValidator(phone);
        } while (!(validUsername && validPassword && validAddr && validPhone));

        if (membership == User.Membership.Manager) {
            System.out.println("Details are valid, define as VIP? y/n");
            if (s.nextLine().startsWith("y"))
                return signUp(username, pass, address, phone, User.Membership.Bronze);
        }
        return signUp(username, pass, address, phone, User.Membership.Basic);

    }


    void saveCustomers() throws FileNotFoundException {

        File f = new File("C:\\Users\\Ariel\\Desktop\\data\\Customers.txt");
        PrintWriter pw = new PrintWriter(f);
        pw.println(customersList.size());
        for (Customer customer : customersList) {
            customer.save(pw);
        }
        pw.close();
    }

    void loadCustomers() throws FileNotFoundException {
        User.Membership membership;
        File f = new File("C:\\Users\\Ariel\\Desktop\\data\\Customers.txt");
        Scanner lc = new Scanner(f);
        int numOfCustomers = lc.nextInt();
        for (int i = 0; i < numOfCustomers; i++) {
            membership = User.Membership.valueOf(lc.next());
            if (membership != User.Membership.Basic) {
                if (customersList.add(new VIPCustomer(lc,membership)))
                    System.out.println("VIP(f) customer registered successfully.");
            } else {
                if (customersList.add(new Customer(lc)))
                    System.out.println("Customer(f) registered successfully.");
            }
        }
    }
}
