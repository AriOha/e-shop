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

    void signUp(String userName, String password, String address, String phoneNumber, User.Membership membership) {
        if (customersList.size() < maxCustomers)
            if (membership != User.Membership.Regular)
                customersList.add(new VIPCustomer(userName, password, address, phoneNumber, membership));
            else
                customersList.add(new Customer(userName, password, address, phoneNumber));
    }

    void removeCustomer(String username) {
        for (Customer customer : customersList)
            if (customer.getUserName().equals(username)) {
                customer.releaseCart();
                customersList.remove(customer);
                System.out.println(username + " removed from the list");
                break;
            }
    }


    void addCart() {
        if (cartList.size() < maxCarts) {
            cartList.add(new Cart(maxElements));
        }
    }

    void removeCart(int cartId) {
        for (Cart cart : cartList)
            if (cart.getCartId() == cartId) {
                releaseCustomer(cart);
                cartList.remove(cart);
                break;
            }
    }

    void releaseCustomer(Cart cartToRemove) {
        //ask Are u sure? if cart assigned to a user
        if (cartToRemove.getOwnedByCustomer() != null) {
            cartToRemove.ownedByCustomer.releaseCart();
        }
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
        System.out.println("Taken carts: " + cartList.size() + " of " + maxCarts + " total.");
    }

    void displayProducts() {
        System.out.println("Products in the store:");
        for (Product product : productsList) {
            System.out.println(product);
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
