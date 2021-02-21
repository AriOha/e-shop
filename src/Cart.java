import java.util.ArrayList;

public class Cart {

    static int startID = 1000;
    ArrayList<Product> productsList;
    private final int cartId, maxCapacity;
    Customer ownedByCustomer;
    double totalPrice;


    Cart() {
        this(100);
    }

    Cart(int maxCapacity) {
        this.productsList = new ArrayList<>();
        ownedByCustomer = null;
        this.cartId = startID++;
        this.totalPrice = 0;
        this.maxCapacity = maxCapacity;
    }

    public int getCartId() {
        return cartId;
    }

    public Customer getOwnedByCustomer() {
        return ownedByCustomer;
    }


    void addItem(Product p) throws NullPointerException {
        try {
            if (productsList.size() < maxCapacity) {
                productsList.add(p);
                System.out.println(p.getProductName() + " added to the cart");
            } else System.out.println("No product added");
        } catch (NullPointerException nu) {
            throw new NullPointerException();
        }
    }

    boolean removeItem(Product product) {
        return productsList.remove(product);
    }

    Product searchForProduct(String productId)  throws NumberFormatException {
        for (Product product : productsList)
            if (product.getCatalogId() == Integer.parseInt(productId)) {
                return product;
            }
        return null;
    }

    void emptyCart() {
        try {
            productsList.clear();
            calculateTotal();
        } catch (NullPointerException e) {
            System.out.println("No cart to remove");
        }
    }


    public void setCustomer(Customer customer) {
        if (ownedByCustomer == null)
            ownedByCustomer = customer;
    }

    void releaseCustomerFromCart() {
        if (ownedByCustomer != null)
            ownedByCustomer = null;
    }

    void displayCart() {
        System.out.println(getBillList());
    }

    StringBuilder getBillList() {
        StringBuilder itemsList = new StringBuilder();
        for (Product item : productsList)
            itemsList.append(item.getProductName()).append("\t").append(item.getPrice()).append("\n");
        if (itemsList.length() == 0) {
            itemsList.append("=No Items in the cart=");
        }

        return itemsList;
    }

    double calculateTotal() {
        //Calculates the total price of the cart
//        Updates the new totalPrice & returns the total price
        double newTotalPrice = 0;
        for (Product item : productsList)
            newTotalPrice += item.getPrice();
        totalPrice = newTotalPrice;
        return totalPrice;
    }

    double calculateDiscounts() {
        double totalDiscount = 0;
        for (Product item : productsList)
            if (item != null) {
                totalDiscount += item.getPrice() * item.getDiscount() / 100;
            }
        return totalDiscount;
    }


    private String toStringArrayNonNulls(Product[] a) {
        StringBuilder sb = new StringBuilder("" + a[0]);
        for (int i = 1; i < a.length; i++) {
            if (a[i] != null)
                sb.append(", ").append(a[i]);
        }
        return sb.toString();
    }


    @Override
    public String toString() {
        return "Cart no. " + cartId + "\t" + "Status: " + (ownedByCustomer != null ? "Belongs to " + ownedByCustomer.getUserName() + "\t" : "Available") +
                "\nShopping list:\n" + getBillList() +
                "\nTotal products: " + productsList.size() + "\n==========";
    }
}
