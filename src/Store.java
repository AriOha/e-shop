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

    void signUp(String userName, String password, String address, String phoneNumber, boolean isVip) {
        if (customersList.size() < maxCustomers)
            if (isVip)
                customersList.add(new VIPCustomer(userName, password, address, phoneNumber));
            else
                customersList.add(new Customer(userName, password, address, phoneNumber));
    }
//
//    Customer[] reorderCustomers(Customer[] unorderedList) {
//        Customer[] products = new Customer[unorderedList.length];
//        int numOfCustomers = 0;
//        for (int i = 0; i < products.length; i++) {
//            if (unorderedList[i] != null)
//                products[numOfCustomers++] = unorderedList[i];
//        }
//        return products;
//    }

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


}
