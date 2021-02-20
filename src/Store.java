import java.util.ArrayList;

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

    boolean signUp(String userName, String password, String address, String phoneNumber, User.Membership membership) {
        int currentCustomers = customersList.size();
        if (customersList.size() < maxCustomers)
            if (membership != User.Membership.Basic)
                customersList.add(new VIPCustomer(userName, password, address, phoneNumber, membership));
            else
                customersList.add(new Customer(userName, password, address, phoneNumber));

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

    Product searchForProductInStore(String productId)  throws NumberFormatException{
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


    void removeCart(String cartId) throws NullPointerException,NumberFormatException {
        Cart cart = searchForCart(cartId);
        releaseCustomer(cart);
        if (cartList.remove(cart))
            System.out.println("Cart "+cartId+" removed from the store");

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

}
