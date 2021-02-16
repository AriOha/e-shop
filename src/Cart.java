import java.util.Arrays;

public class Cart {

    static int startID = 1000;
    Product[] productsList;
    private final int cartId;
    private int productsCounter;
    Customer ownedByCustomer;
    double totalPrice;


    Cart() {
        this(100);
    }

    Cart(int maxProducts) {
        this.productsList = new Product[maxProducts];
        ownedByCustomer = null;
        this.cartId = startID++;
        this.productsCounter = 0;
        this.totalPrice = 0;
    }

    public int getCartId() {
        return cartId;
    }

    public Customer getOwnedByCustomer() {
        return ownedByCustomer;
    }

    public int getProductsCounter() {
        return productsCounter;
    }

    void addItem(Product p) {
        //TODO:
        if (productsCounter < productsList.length) {
            productsList[productsCounter++] = p;
            System.out.println(p.getProductName() + " added to the cart");
        } else System.out.println("No product added");
    }

    void removeItem(int catalogId) {
        boolean notDeleted = true;
        for (int i = 0; i < productsCounter && notDeleted; i++) {
            if (productsList[i] != null)
                if (productsList[i].getCatalogId() == catalogId) {
                    productsList[i] = null;
                    productsList = reorderProducts(productsList);
                    notDeleted = false;
                    productsCounter--;
                }
        }
    }

    void emptyCart() {
        Arrays.fill(productsList, null);
        productsCounter = 0;
    }

    Product[] reorderProducts(Product[] unorderedList) {

        Product[] products = new Product[unorderedList.length];
        int numOfProducts = 0;
        for (int i = 0; i < products.length; i++) {
            if (unorderedList[i] != null)
                products[numOfProducts++] = unorderedList[i];
        }
        return products;

    }


    public void setCustomer(Customer customer) {
        if (ownedByCustomer == null)
            ownedByCustomer = customer;
    }

    void removeCustomer(Customer customer) {
        if (ownedByCustomer != null)
            ownedByCustomer = null;
    }

    void displayChart() {
        System.out.println(getBillList());
    }

    StringBuilder getBillList() {
        StringBuilder itemsList = new StringBuilder();
        for (Product item : productsList)
            if (item != null) {
                itemsList.append(item.getProductName()).append("\t").append(item.getPrice()).append("\n");
            }
        return itemsList;
    }

    double calculateTotal() {
        //Calculates the total price of the cart
//        Updates the new totalPrice & returns the total price
        double newTotalPrice = 0;
        for (Product item : productsList)
            if (item != null) {
                newTotalPrice += item.getPrice();
            }
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


    @Override
    public String toString() {
        return "Cart{" +
                "productsList=" + Arrays.toString(productsList) +
                ", cartId=" + cartId +
                ", productsCounter=" + productsCounter +
                ", ownedByCustomer=" + (ownedByCustomer != null ? ownedByCustomer.getUserName() : ownedByCustomer) +
                '}';
    }
}
